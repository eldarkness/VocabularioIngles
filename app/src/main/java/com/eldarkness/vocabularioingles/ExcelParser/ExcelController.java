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

    private static Cell cellstatico;
    private static Sheet sheet;
    private static String nombreExcel = "Excel Vocabulario Ingles.xls";
    Workbook workbook;
    private static String EXCEL_SHEET_NAME = "Palabras Diccionario";

    // En teoria le deberian de llegar aqui las palabras desde la clase activity excel en un arraylist
    public Boolean crearExcel(ArrayList<PalabraDiccionario> palabrasDiccionario){
        if(palabrasDiccionario.size() < 1){
            return false;
        }

        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet(EXCEL_SHEET_NAME);
        Row row = sheet.createRow(0);
        Cell cell = null;
        cell = row.createCell(0);
        cell.setCellValue("Palabra Español");
        cell = row.createCell(1);
        cell.setCellValue("Palabra Ingles");
        cell = row.createCell(2);
        cell.setCellValue("Categoria");

        for (int i = 0; i< palabrasDiccionario.size();i++){
            row = sheet.createRow(i+1);
            cell = row.createCell(0);
            cell.setCellValue(palabrasDiccionario.get(i).getPalabraEsp());
            cell = row.createCell(1);
            cell.setCellValue(palabrasDiccionario.get(i).getPalabraEng());
            cell = row.createCell(2);
            cell.setCellValue(palabrasDiccionario.get(i).getCategoria());
        }

        if(exportarExcel(workbook)){
            return true;
        }else{
            return false;
        }

    }

    private Boolean exportarExcel(Workbook workbook){
        Boolean exito;

        if(workbook == null){
            return false;
        }

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), nombreExcel);
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            System.out.println("He grabado el excel");
            exito = true;

        } catch (IOException e) {
            System.out.println("Error writing Exception: " + e);
            exito = false;

        } catch (Exception e) {
            System.out.println("Failed to save file due to Exception " + e);
            exito = false;

        // es solo para cerrar el fileoutputstream
        } finally {
            try {
                if (null != fileOutputStream) {
                    fileOutputStream.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return exito;
    }

    /**
     * @param excelACargar
     * @return
     *
     * Este metodo recibe un objeto workbook (excel) y extrae todas las palabras del diccionario, incluida la categoria
     * En el caso de que la celda categoria este vacia se le asignara a esa palabra la categoria Defecto.
     * Finalmente devuelve un arraylist con palabras diccionario
     */
    public ArrayList<PalabraDiccionario> cargarPalabrasExcel(Workbook excelACargar){
        System.out.println("Metodo cargarPalabrasExcel");
        ArrayList<PalabraDiccionario> palabrasExcelLeidas= new ArrayList<PalabraDiccionario>();

        Sheet sheet = excelACargar.getSheetAt(0);
        Row row = null;
        Iterator<Row> rowIterator = sheet.iterator();

        // Va recorriendo las filas del excel y en cada una de ellas saca una palabra y la añade a la lista
        while(rowIterator.hasNext()){
            row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            Cell celda = null;
            String palabraEsp = "";
            String palabraIng = "";
            String categoria = "Defecto";


            if(cellIterator.hasNext()){
                celda = cellIterator.next();
                if(celda.getStringCellValue().equalsIgnoreCase("Palabra Español")){
                    continue;
                }
                palabraEsp = celda.getStringCellValue();

            }
            if(cellIterator.hasNext()){
                celda = cellIterator.next();
                palabraIng = celda.getStringCellValue();

            }

            if(cellIterator.hasNext()){
                celda = cellIterator.next();
                if(celda.getStringCellValue() != ""){
                    categoria = celda.getStringCellValue();
                }

            }

            if(palabraEsp != null && palabraEsp != "" && palabraIng != null && palabraIng != ""){
                palabrasExcelLeidas.add(new PalabraDiccionario(palabraEsp, palabraIng, categoria));
            }


        }

        //leerArrayList(palabrasExcelLeidas);

        return palabrasExcelLeidas;

    }

    // Es un metodo solo para depurar el programa, sirve para ver que palabras han sido extraidas del excel
    private void leerArrayList(ArrayList<PalabraDiccionario> listaALeer){
        for (PalabraDiccionario p:listaALeer) {
            System.out.println("Palabra en español: " + p.getPalabraEsp() + "\n" +
                    "Palabra en Ingles: " + p.getPalabraEng() + "\n");
        }
    }

    // A partir de aqui son solo pruebas no se usan, asi que borrar mas adelante
    public void createExcel(Context context, String name){
        workbook = new HSSFWorkbook();

        cellstatico = null;

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

        sheet = null;
        sheet = workbook.createSheet(EXCEL_SHEET_NAME);

        Row row = sheet.createRow(0);

        cellstatico = row.createCell(0);
        cellstatico.setCellValue("First Name");
        cellstatico.setCellStyle(cellStyle);

        cellstatico = row.createCell(1);
        cellstatico.setCellValue("Last Name");
        cellstatico.setCellStyle(cellStyle);

        cellstatico = row.createCell(2);
        cellstatico.setCellValue("Phone Number");
        cellstatico.setCellStyle(cellStyle);

        cellstatico = row.createCell(3);
        cellstatico.setCellValue("Mail ID");
        cellstatico.setCellStyle(cellStyle);

        Sheet sheet2 = workbook.getSheetAt(0);

        exportarExcelPrueba(context, name);



    }

    // metodo privado para apoyo del anterior, tambien de prueba borrar mas adelante
    private void exportarExcelPrueba(Context context, String name){

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
