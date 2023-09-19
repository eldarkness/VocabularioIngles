package com.eldarkness.vocabularioingles.bbdd;

public class Estructura_BBDD {


    // Constructor vacio
    private Estructura_BBDD() {}

    /* Inner class that defines the table contents */
    /*public static class FeedEntry implements BaseColumns {*/
    public static final String TABLE_NAME = "Diccionario";
    public static final String NOMBRE_COLUMNA1 = "ID";
    public static final String NOMBRE_COLUMNA2 = "Español";
    public static final String NOMBRE_COLUMNA3 = "Ingles";
    public static final String NOMBRE_COLUMNA4 = "Categoria";
    public static final String TABLE2_NAME = "TablaCategorias";
    public static final String COLUMNA1_CATEGORIAS = "ID";
    public static final String COLUMNA2_CATEGORIAS = "Categorias";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = " ,";

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Estructura_BBDD.TABLE_NAME + " (" +
                    Estructura_BBDD.NOMBRE_COLUMNA1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Estructura_BBDD.NOMBRE_COLUMNA2 + TEXT_TYPE + " UNIQUE " + COMMA_SEP +
                    Estructura_BBDD.NOMBRE_COLUMNA3 + TEXT_TYPE + COMMA_SEP +
                    Estructura_BBDD.NOMBRE_COLUMNA4 + TEXT_TYPE + " )";

    protected static final String SQL_CREATE_ENTRIES2 =
            "CREATE TABLE " + Estructura_BBDD.TABLE2_NAME + " (" +
                    Estructura_BBDD.COLUMNA1_CATEGORIAS + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Estructura_BBDD.COLUMNA2_CATEGORIAS + TEXT_TYPE + " )";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Estructura_BBDD.TABLE_NAME;

    protected static final String SQL_DELETE_ENTRIES2 =
            "DROP TABLE IF EXISTS " + Estructura_BBDD.TABLE2_NAME;
}
