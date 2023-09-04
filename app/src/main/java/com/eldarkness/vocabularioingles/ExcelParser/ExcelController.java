package com.eldarkness.vocabularioingles.ExcelParser;

import android.content.Context;
import android.os.Environment;

import com.eldarkness.vocabularioingles.PalabraDiccionario;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelController {

    private static Cell cell;
    private static Sheet sheet;

    Workbook workbook;

    private static String EXCEL_SHEET_NAME = "Sheet 1";

    public void createExcel(Context context, String name){
        workbook = new HSSFWorkbook();

        cell = null;

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

        sheet = null;
        sheet = workbook.createSheet(EXCEL_SHEET_NAME);

        Row row = sheet.createRow(0);

        cell = row.createCell(0);
        cell.setCellValue("First Name");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("Last Name");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("Phone Number");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("Mail ID");
        cell.setCellStyle(cellStyle);

        Sheet sheet2 = workbook.getSheetAt(0);


        exportarExcel(context, name);



    }

    private void exportarExcel(Context context, String name){

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name);
        FileOutputStream fileOutputStream = null;
        System.out.println();
        System.out.println(file);

        try {
            fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            System.out.println("He grabado el excel");


        } catch (IOException e) {
            System.out.println("Error writing Exception: " + e);

        } catch (Exception e) {
            System.out.println("Failed to save file due to Exception " + e);

        } finally {
            try {
                if (null != fileOutputStream) {
                    fileOutputStream.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public ArrayList<PalabraDiccionario> cargarPalabrasExcel(Workbook excelACargar){
        System.out.println("Metodo cargarPalabrasExcel");
        ArrayList<PalabraDiccionario> palabrasExcelLeidas= new ArrayList<PalabraDiccionario>();

        // sacamos la pagina, que solo tendra una en este caso
        Sheet sheet = excelACargar.getSheetAt(0);
        Row row = null;
        Iterator<Row> rowIterator = sheet.iterator();

        // Comprueba filas
        while(rowIterator.hasNext()){
            row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            Cell celda = null;
            String palabraEsp = "";
            String palabraIng = "";

            if(cellIterator.hasNext()){
                celda = cellIterator.next();
                palabraEsp = celda.getStringCellValue();
            }
            if(cellIterator.hasNext()){
                celda = cellIterator.next();
                palabraIng = celda.getStringCellValue();
            }

            palabrasExcelLeidas.add(new PalabraDiccionario(palabraEsp, palabraIng));

        }

        leerArrayList(palabrasExcelLeidas);

        return palabrasExcelLeidas;

    }

    private void leerArrayList(ArrayList<PalabraDiccionario> listaALeer){
        for (PalabraDiccionario p:listaALeer) {
            System.out.println("Palabra en español: " + p.getPalabraEsp() + "\n" +
                                  "Palabra en Ingles: " + p.getPalabraEng() + "\n");
        }
    }

    // prueba borrar mas adelante
    public void leerExcel() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "excelprueba.xls");

        FileInputStream fileInputStream = null;
        Workbook workbookCargado;


        try {
            fileInputStream = new FileInputStream(file);

            workbookCargado = new HSSFWorkbook(fileInputStream);
            System.out.println(workbookCargado.getSheetAt(0).getRow(0).getCell(0));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    }
