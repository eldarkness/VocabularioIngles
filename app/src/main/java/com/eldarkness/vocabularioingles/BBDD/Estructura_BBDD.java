package com.eldarkness.vocabularioingles.BBDD;

public class Estructura_BBDD {



    // Constructor vacio
    private Estructura_BBDD() {}

    /* Inner class that defines the table contents */
    /*public static class FeedEntry implements BaseColumns {*/
    public static final String TABLE_NAME = "Palabras";
    public static final String NOMBRE_COLUMNA1 = "ID";
    public static final String NOMBRE_COLUMNA2 = "Ingles";
    public static final String NOMBRE_COLUMNA3 = "Espanol";
    public static final String TABLE_NAME2 = "Diccionario";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = " ,";
    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Estructura_BBDD.TABLE_NAME + " (" +
                    Estructura_BBDD.NOMBRE_COLUMNA1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Estructura_BBDD.NOMBRE_COLUMNA2 + TEXT_TYPE + " UNIQUE " + COMMA_SEP +
                    Estructura_BBDD.NOMBRE_COLUMNA3 + TEXT_TYPE + " )";

    protected static final String SQL_CREATE_ENTRIES2 =
            "CREATE TABLE " + Estructura_BBDD.TABLE_NAME2 + " (" +
                    Estructura_BBDD.NOMBRE_COLUMNA1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Estructura_BBDD.NOMBRE_COLUMNA3 + TEXT_TYPE + " UNIQUE " + COMMA_SEP +
                    Estructura_BBDD.NOMBRE_COLUMNA2 + TEXT_TYPE + " )";



    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Estructura_BBDD.TABLE_NAME;

}
