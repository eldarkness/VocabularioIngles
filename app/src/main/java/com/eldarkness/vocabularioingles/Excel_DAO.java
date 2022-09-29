package com.eldarkness.vocabularioingles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;

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
import java.util.Iterator;

public class Excel_DAO extends AppCompatActivity {

    TextView primerTextView;
    HSSFWorkbook workbook;
    InputStream inputStream;
    FileInputStream fileInputStream;
    String rutaAlmacenamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_dao);

        primerTextView = (TextView) findViewById(R.id.MostrarNombreExcel);

        AssetManager am = this.getAssets();
        try {
            inputStream = am.open("PruebaVocabularioIngles.xls");
            workbook = new HSSFWorkbook(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // muestra la hoja en un textview en el layout
        primerTextView.setText(workbook.getSheetName(0));

        HSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        Row row;
        // Se recorren las filas
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            Cell celda;
            // Se recorren las columnas (o celdas)
            while (cellIterator.hasNext()){
                celda = cellIterator.next();
                System.out.println(celda.getStringCellValue());
                /*switch (celda.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        if (HSSFDateUtil.isCellDateFormatted(celda)) {
                            fecha2= celda.getDateCellValue();
                            SimpleDateFormat fmt = new SimpleDateFormat("dd-yyyy");
                            fechaSTR = fmt.format(fecha2);

                        } else if (cont == 5) {
                            precioVenta= (int)celda.getNumericCellValue();
                        }
                        break;

                    case Cell.CELL_TYPE_STRING:
                        if (cont == 1) {
                            nombre=celda.getStringCellValue();
                        }else if(cont == 3) {
                            tipoOperacion=celda.getStringCellValue();
                        }else {
                            tipoInmueble=celda.getStringCellValue();
                        }
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(celda.getBooleanCellValue()+" ");
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        System.out.printf("La formula es: %s", celda.getCellFormula()+"a "+celda.getNumericCellValue());
                        break;
                }
                cont++;*/

            }

            System.out.println("*************************************");
        }// fin segunda pagina */

        // cerramos el libro excel*/
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

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