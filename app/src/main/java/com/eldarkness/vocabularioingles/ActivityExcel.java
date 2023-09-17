package com.eldarkness.vocabularioingles;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.eldarkness.vocabularioingles.BBDD.BBDD_Controller;
import com.eldarkness.vocabularioingles.ExcelParser.ExcelController;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ActivityExcel extends AppCompatActivity {

    Button botonImportar;
    Button botonExportar;

    BBDD_Controller bbdd_controller;
    ExcelController excelController;

    String CategoriaExcel = "";

    private final int REQUEST_CODE_EXCEL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel);
        botonExportar = findViewById(R.id.BotonExportar);
        botonImportar = findViewById(R.id.BotonImportar);
        bbdd_controller = new BBDD_Controller(this);
        excelController = new ExcelController();
    }

    public void importarExcel(View view){
        openFileChooser();
    }

    public void openFileChooser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/vnd.ms-excel");
        startActivityForResult(intent, REQUEST_CODE_EXCEL);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        // Comprobacion a la llamada de intent para seleccionar un libro excel en el dispositivo
        if(requestCode == REQUEST_CODE_EXCEL && resultCode == Activity.RESULT_OK){
            if(data == null){
                return;
            }
            Uri uri = data.getData();
            Workbook workbook;

            try {
                // El objeto uri conseguido de los datos del intent no se puede convertir a file directamente
                // asi que he tenido que optar por usar ese metodo para conseguir un inputStream y ya pasarlo a workbook
                InputStream inputStream = getContentResolver().openInputStream(uri);
                //System.out.println(inputStream.toString());

                workbook = new HSSFWorkbook(inputStream);
                EventoCategoriaDialog(workbook);
                //System.out.println("Este es el excel del inputstream" + workbook.getSheetAt(0).getRow(0).getCell(0));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }



        }

    }

    /***
     *
     * @param palabrasExcelCargado
     * Se le pasa una lista de palabras cargadas del excel con la categoria seleccionada en el Cuadro de dialogo
     * y se meten en la base de datos sino estan ya en ella.
     */
    private void introducirPalabrasExcelEnBBDD(ArrayList<PalabraDiccionario> palabrasExcelCargado){

        System.out.println("La lista tiene " + palabrasExcelCargado.size() + " palabras");
        // preguntar aqui al usuario si quiere asignar una categoria a las palabras cargadas a las que se le haya asignado
        // la categoria defecto sino se introduce ninguna entonces se le dejara esa por defecto

        if(!bbdd_controller.categoriaRepetida("Defecto")){
            bbdd_controller.anadirCategoria("Defecto");

        }

        int cont = 0;
        for (PalabraDiccionario p: palabrasExcelCargado) {
            // Sino esta la palabra en español entonces hay que añadirla a la BBDD en caso contrario no hacemos nada
            if(!bbdd_controller.buscarPalabra(p.getPalabraEsp())){
                // Si la categoria viene vacia o nula se cogera la categoria elegida en el dialog, ya sea una real o por defecto
                // sino se introducen con la categoria que tenian en el excel del que se importaron
                if(p.getCategoria() == null || p.getCategoria().equalsIgnoreCase("")) {
                    bbdd_controller.IntroducirPalabrasDiccionario(p.getPalabraEsp(),p.getPalabraEng(),CategoriaExcel);
                }else{
                    bbdd_controller.IntroducirPalabrasDiccionario(p.getPalabraEsp(),p.getPalabraEng(),p.getCategoria());
                }
                cont++;
            }

        }

        System.out.println("********** Se insertaron " + cont + " palabras desde el excel cargado");

    }

    public void EventoCategoriaDialog(Workbook workbook){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        ArrayList<String> ListaCategorias =  bbdd_controller.cargarCategorias();
        CharSequence[] cs = ListaCategorias.toArray(new String[0]);
        final int[] checkedItem = new int[]{-1};
        builder.setSingleChoiceItems(cs, checkedItem[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // al seleccionar se podria quizas meter en una variable el string que se desee y luego mandarlo con la interfaz
                // En el which esta el indice del item que se ha seleccionado asi que debo trabajar aqui con el arraylist original
                CategoriaExcel = ListaCategorias.get(which);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            CategoriaExcel = "Defecto";
        });

        builder.setPositiveButton("Ok", (dialog, which) -> {
            ArrayList<PalabraDiccionario> palabrasExcelCargado = excelController.cargarPalabrasExcel(workbook);
            if(palabrasExcelCargado.size()>0){
                introducirPalabrasExcelEnBBDD(palabrasExcelCargado);
            }
        });

        builder.show();
    }

    /***
     *
     * @param view
     *
     * Este metodo deberia de mandar las palabras de la base de datos al metodo crear excel de la clase excelcontroller
     */
    public void exportarExcel(View view){

        // debemos recibir primero las palabras de la base de datos en un arraylist
        // luego se lo mandamos a la clase excelcontroller y seguimos desde alli
        ArrayList<PalabraDiccionario> listaPalabras;
        listaPalabras = bbdd_controller.extraerPalabrasBBDD();
        excelController.crearExcel(listaPalabras);


    }



}