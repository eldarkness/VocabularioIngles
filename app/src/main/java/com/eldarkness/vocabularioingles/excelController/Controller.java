package com.eldarkness.vocabularioingles.excelController;



import android.content.Context;
import android.os.Build;
import android.os.Environment;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Controller {

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

        exportarExcel(context, name);



    }

    private void exportarExcel(Context context, String name){

        if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())){
            System.out.println("el sistema esta solo de modo lectura");
        }
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            System.out.println("El almacenamiento externo no esta disponible");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if(Environment.isExternalStorageManager()){
                System.out.println("Se tiene acceso al almacenamiento externo");
            }else{
                System.out.println("no se tiene acceso al almacenamiento externo");
            }
        }
        ;

        File file = new File(context.getExternalFilesDir(null), name);
        FileOutputStream fileOutputStream = null;
        System.out.println(context.getExternalFilesDir(null));
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



}
