package com.eldarkness.vocabularioingles;

import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.Iterator;

public class Pruebas {

/*
    HSSFSheet sheet2 = workbook.getSheetAt(1);
    Iterator<Row> rowIterator2 = sheet2.iterator();

            System.out.println("La hoja EXCEL se llama:  \""+ sheet2.getSheetName() + "\"");

    Row row2;
    int linea2=0;
    // Con este bucle while recorremos cada una de las filas del excel y metemos esa "fila" dentro de la variable row en cada pasada
            while (rowIterator2.hasNext()) {
        row2 = rowIterator2.next();
        Iterator<Cell> cellIterator2 = row2.cellIterator();

        // Entrara solo una vez y creara la tabla y los campos mediante un metodo
        if (linea2 == 0) {

            linea2++;
            continue;
        }

        String nombre = "";
        String fechaSTR= "";
        String tipoOperacion = "";
        String tipoInmueble= "";
        int precioVenta = 0;
        Date fecha2 = new Date();
        System.out.println("------Linea n�" + linea + "------");
        Cell celda;
        int cont = 1;
        // Una vez dentro de la fila metemos esa fila (variable row) dentro de otro iterator para recorrer sus celdas
        while (cellIterator2.hasNext()){
            celda = cellIterator2.next();
            // 6 Y finalmente dentro de cada CELDA tenemos que comprobar de que tipo es para poder sacarlo por pantalla adecuadamente
            // para ello usamos un switch-case y dependiendo del valor usamos el metodo para poder mostrarlo

            switch (celda.getCellType()) {
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
            cont++;

        }

        System.out.println("*************************************");
    }// fin segunda pagina

    // cerramos el libro excel
            workbook.close();*/


}
