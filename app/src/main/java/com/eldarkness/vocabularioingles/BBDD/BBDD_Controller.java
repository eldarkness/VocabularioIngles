package com.eldarkness.vocabularioingles.BBDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.eldarkness.vocabularioingles.PalabraDiccionario;

import java.util.ArrayList;
import java.util.Locale;

public class BBDD_Controller extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PalabrasDiccionario.db";

    public BBDD_Controller(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Estructura_BBDD.SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(Estructura_BBDD.SQL_CREATE_ENTRIES2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(Estructura_BBDD.SQL_DELETE_ENTRIES);
        sqLiteDatabase.execSQL(Estructura_BBDD.SQL_DELETE_ENTRIES2);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public Boolean buscarPalabra(String palabraEsp){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] projection = {
                Estructura_BBDD.NOMBRE_COLUMNA2
        };
        String selection = Estructura_BBDD.NOMBRE_COLUMNA2 + " = ?";
        String[] selectionArgs = { capitalizar(palabraEsp) };

        Cursor c = sqLiteDatabase.query(
                Estructura_BBDD.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null

        );
        int cantidad = c.getCount();
        c.close();
        // parece que no se puede cerrar el objeto sqlitedatabase ANTES de llamar al metodo del cursor getCount()

        if (cantidad>0){
            return true;
        }else{
            return false;
        }

    }

    public Boolean IntroducirPalabrasDiccionario(String palabraEsp, String palabraIng, String categoria) {
        // tiene que añadir una palabra a la base de datos sino esta repetida, usar columna llamada Diccionario
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        palabraEsp = capitalizar(palabraEsp);
        palabraIng = capitalizar(palabraIng);

        ContentValues values = new ContentValues();
        values.put(Estructura_BBDD.NOMBRE_COLUMNA2, palabraEsp);
        values.put(Estructura_BBDD.NOMBRE_COLUMNA3, palabraIng);
        values.put(Estructura_BBDD.NOMBRE_COLUMNA4, categoria);
        long newRowId = sqLiteDatabase.insert(Estructura_BBDD.TABLE_NAME, null, values);
        sqLiteDatabase.close();
        if (newRowId > 0) {
            return true;
        }

        return false;
    }

    /**
     * @return
     * Este metodo extrae todas las palabras de la tabla diccionario, que son las palabras con su traduccion
     * tanto en español como en ingles y su categoria, y devuelve estas en un arraylist de palabras diccionario
     */
    public ArrayList<PalabraDiccionario> extraerPalabrasBBDD() {
        ArrayList<PalabraDiccionario> listaPalabras = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor c = sqLiteDatabase.rawQuery("select * from " + Estructura_BBDD.TABLE_NAME, null);

        if(c.getCount() > 0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                listaPalabras.add(new PalabraDiccionario(c.getString(1),c.getString(2),c.getString(3)));
                c.moveToNext();
            }
        }

        // depuracion
        int cantidad = c.getCount();
        System.out.println("Hay " + cantidad + " palabras en la base de datos");

        c.close();

        return listaPalabras;
    }

    public ArrayList<String> cargarCategorias(){
        ArrayList<String> listaCategorias = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + Estructura_BBDD.TABLE2_NAME, null);

        if(c.getCount()>0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                listaCategorias.add(c.getString(1));
                c.moveToNext();
            }
        }else{
            listaCategorias.add("Crea una Categoria");
        }
        c.close();
        return listaCategorias;

    }


    public void anadirCategoria(String categoria) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        categoria = capitalizar(categoria);
        ContentValues values = new ContentValues();

        values.put(Estructura_BBDD.COLUMNA2_CATEGORIAS, categoria);
        sqLiteDatabase.insert(Estructura_BBDD.TABLE2_NAME, null, values);

    }


    public Boolean categoriaRepetida(String categoria) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] projection = {
                Estructura_BBDD.COLUMNA2_CATEGORIAS
        };
        String selection = Estructura_BBDD.COLUMNA2_CATEGORIAS + " = ?";
        String[] selectionArgs = {categoria};

        Cursor c = sqLiteDatabase.query(
                Estructura_BBDD.TABLE2_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null

        );
        int cantidad = c.getCount();
        c.close();

        if (cantidad > 0) {
            return true;
        } else {
            return false;
        }

    }

    private String capitalizar(String palabra){
        String str = (palabra.substring(0, 1)).toUpperCase(Locale.ROOT) + (palabra.substring(1)).toLowerCase(Locale.ROOT);

        return str;
    }



}
