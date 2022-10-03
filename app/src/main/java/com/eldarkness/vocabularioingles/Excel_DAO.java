package com.eldarkness.vocabularioingles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.eldarkness.vocabularioingles.BBDD.BBDD_Controller;
import com.eldarkness.vocabularioingles.BBDD.Estructura_BBDD;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class Excel_DAO extends AppCompatActivity {

    TextView MostrarPalabras;
    HSSFWorkbook workbook;
    InputStream inputStream;
    BBDD_Controller bbdd_controller;
    private ArrayList<String> listaPalabrasEsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_dao);
        bbdd_controller = new BBDD_Controller(this);
        SQLiteDatabase sqLiteDatabase = bbdd_controller.getWritableDatabase();
        listaPalabrasEsp = new ArrayList<>();
        MostrarPalabras = (TextView) findViewById(R.id.MostrarPalabras);

        AssetManager am = this.getAssets();
        try {
            inputStream = am.open("PruebaVocabularioIngles.xls");
            workbook = new HSSFWorkbook(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Golaaa");
        try {
            InputStream prueba2 = new FileInputStream("/storage/emulated/0/Download/PruebaVocabularioIngles.xls");


            prueba2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // muestra la hoja en un textview en el layout
        // primerTextView = (TextView) findViewById(R.id.MostrarNombreExcel);
        // primerTextView.setText(workbook.getSheetName(0));

        HSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        Row row;

        // Se recorren las filas
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            Cell celda;

            // Se recorren las columnas (o celdas)
            celda = cellIterator.next();
            if(comprobarPalabra(celda.getStringCellValue(),bbdd_controller)){
                continue;
            }
            ContentValues values = new ContentValues();
            values.put(Estructura_BBDD.NOMBRE_COLUMNA3, celda.getStringCellValue());
            System.out.println(celda.getStringCellValue());
            listaPalabrasEsp.add(celda.getStringCellValue());
            celda = cellIterator.next();
            values.put(Estructura_BBDD.NOMBRE_COLUMNA2, celda.getStringCellValue());
            long newRowId = sqLiteDatabase.insert(Estructura_BBDD.TABLE_NAME, null, values );

            /*
            while(cellIterator.hasNext()){
                if(celda.getColumnIndex() == 0){
                    System.out.println(celda.getStringCellValue());

                }else if(celda.getColumnIndex() == 1){
                    System.out.println(celda.getStringCellValue());
                    values.put(Estructura_BBDD.NOMBRE_COLUMNA3, celda.getStringCellValue());
                }
            }
            System.out.println("*************************************");*/
        }

        try {
            workbook.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        MostrarPalabras.setText(listaPalabrasEsp.toString());


    }

    private Boolean comprobarPalabra(String palabraEsp, BBDD_Controller bbdd_controller){
        SQLiteDatabase sqLiteDatabase = bbdd_controller.getReadableDatabase();
        String[] projection = {
                Estructura_BBDD.NOMBRE_COLUMNA3
        };

        String selection = Estructura_BBDD.NOMBRE_COLUMNA3 + " = ?";
        String[] selectionArgs = { palabraEsp };

        Cursor c = sqLiteDatabase.query(
                Estructura_BBDD.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (c.getCount()>0){
            return true;
        }else{
            return false;
        }



    }


    // no se usa
    private void copiaExcel(Context contexto){

        try{
            InputStream datosEntrada = contexto.getAssets().open("PruebaVocabularioIngles.xls");
            OutputStream datosSalida = new FileOutputStream("");
            byte[] bufferBBDD = new byte[1024];
            int longitud;
            while((longitud = datosEntrada.read(bufferBBDD)) > 0){
                datosSalida.flush();
                datosSalida.close();
                datosEntrada.close();

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}