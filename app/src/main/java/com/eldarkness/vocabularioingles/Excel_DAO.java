package com.eldarkness.vocabularioingles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

public class Excel_DAO extends AppCompatActivity {

    TextView primerTextView;
    HSSFWorkbook workbook;
    String rutaAlmacenamiento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_dao);

        primerTextView = (TextView) findViewById(R.id.MostrarNombreExcel);

        rutaAlmacenamiento = this.getFilesDir().getParentFile().getPath() + "PruebaVocabularioIngles.xls";
        copiaExcel(this);
        System.out.println(rutaAlmacenamiento);

        AssetManager am = this.getAssets();
        try {
            InputStream is = am.open("PruebaVocabularioIngles.xls");

        } catch (IOException e) {
            e.printStackTrace();
        }

        FileInputStream file;
        try {
            file = new FileInputStream(rutaAlmacenamiento);
            workbook = new HSSFWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        primerTextView.setText(workbook.getSheetName(0));

        HSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        System.out.println("La hoja EXCEL se llama:  \""+ sheet.getSheetName() + "\"");

        Row row;
        Row row1;
        int linea=0;
        // Con este bucle while recorremos cada una de las filas del excel y metemos esa "fila" dentro de la variable row en cada pasada


    }

    private void copiaExcel(Context contexto){

        try{
            InputStream datosEntrada = contexto.getAssets().open("PruebaVocabularioIngles.xls");
            OutputStream datosSalida = new FileOutputStream(rutaAlmacenamiento);
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