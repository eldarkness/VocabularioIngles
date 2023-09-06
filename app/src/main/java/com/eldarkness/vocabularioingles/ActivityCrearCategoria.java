package com.eldarkness.vocabularioingles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.eldarkness.vocabularioingles.BBDD.BBDD_Controller;
import com.eldarkness.vocabularioingles.BBDD.Estructura_BBDD;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityCrearCategoria extends AppCompatActivity {

    private BBDD_Controller bbdd;

    private EditText textoCategoria;
    private ArrayList<String> categorias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bbdd = new BBDD_Controller(this);
        setContentView(R.layout.activity_crear_categoria);
        textoCategoria = (EditText) findViewById(R.id.editTextCategoria);
        categorias = new ArrayList<>();
        categoriaPorDefecto();
    }

    public void anadirCategoria(View view){

        String categoria = capitalizar(textoCategoria.getText().toString());
        if(categoria != ""){
            return;
        }

        if(!bbdd.categoriaRepetida(categoria)){
            bbdd.anadirCategoria(categoria);
        }
        textoCategoria.setText("");
        textoCategoria.requestFocus();

    }




    /*public void anadirCategoria(View view){
        SQLiteDatabase sqLiteDatabase = bbdd.getWritableDatabase();
        ContentValues values = new ContentValues();
        String categoria = capitalizar(textoCategoria.getText().toString());
        if(!categoriaRepetida(categoria)){
            values.put(Estructura_BBDD.COLUMNA2_CATEGORIAS,categoria);
            long newRowId = sqLiteDatabase.insert(Estructura_BBDD.TABLE2_NAME, null, values);
        }else{
            // categoria repetida, no se introduce
        }
        textoCategoria.setText("");
        textoCategoria.requestFocus();

    }

    private Boolean categoriaRepetida(String categoria){
        SQLiteDatabase sqLiteDatabase = bbdd.getReadableDatabase();
        String[] projection = {
                Estructura_BBDD.COLUMNA2_CATEGORIAS
        };
        String selection = Estructura_BBDD.COLUMNA2_CATEGORIAS + " = ?";
        String[] selectionArgs = { categoria };

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
        // parece que no se puede cerrar el objeto sqlitedatabase ANTES de llamar al metodo del cursor getCount()

        if (cantidad>0){
            return true;
        }else{
            return false;
        }

    }*/

    // Crea una categoria por defecto para poder meter todas las palabras del Excel
    private void categoriaPorDefecto(){
        if(!categoriaRepetida("Defecto")){
            anadirCategoriaPorDefecto("Defecto");
        }
    }
    public void anadirCategoriaPorDefecto(String categoria){
        SQLiteDatabase sqLiteDatabase = bbdd.getWritableDatabase();
        ContentValues values = new ContentValues();
        categoria = capitalizar(categoria);
        values.put(Estructura_BBDD.COLUMNA2_CATEGORIAS,categoria);
        sqLiteDatabase.insert(Estructura_BBDD.TABLE2_NAME, null, values);

        Toast toast = new Toast(this);
        toast.setText("Se creo la categoria " + categoria);
        toast.show();
    }

    public void volverAtras(View view){
        finish();
    }

    public void onBackPressed(){
        volverAtras(null);

    }

    private String capitalizar(String palabra){
        String str = (palabra.substring(0, 1)).toUpperCase(Locale.ROOT) + (palabra.substring(1)).toLowerCase(Locale.ROOT);

        return str;
    }



}