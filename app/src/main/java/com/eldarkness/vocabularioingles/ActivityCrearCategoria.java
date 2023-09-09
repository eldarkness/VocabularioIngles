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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bbdd = new BBDD_Controller(this);
        setContentView(R.layout.activity_crear_categoria);
        textoCategoria = (EditText) findViewById(R.id.editTextCategoria);
        categoriaPorDefecto();
    }

    public void anadirCategoria(View view){
        String categoria = textoCategoria.getText().toString();
        if(categoria != ""){
            return;
        }
        categoria = capitalizar(categoria);

        if(!bbdd.categoriaRepetida(categoria)){
            bbdd.anadirCategoria(categoria);
        }
        textoCategoria.setText("");
        textoCategoria.requestFocus();

    }


    /***
     * Solo se deberia ejecutar una vez, lo que hace es añadir una categoria por defecto para poder introducir
     * las palabras que vienen del excel. Quizas sea reemplazado por lo de dejar que el usuario cree una categoria
     * cuando importa las palabras, aun asi se podria dejar por si no se introduce ninguna y prevenir errores
     */
    private void categoriaPorDefecto(){
        if(!bbdd.categoriaRepetida("Defecto")){
            bbdd.anadirCategoria("Defecto");
            Toast toast = new Toast(this);
            toast.setText("Se creo la categoria por defecto");
            toast.show();
        }
    }

    private String capitalizar(String palabra){
        String str = (palabra.substring(0, 1)).toUpperCase(Locale.ROOT) + (palabra.substring(1)).toLowerCase(Locale.ROOT);

        return str;
    }

    public void volverAtras(View view){
        finish();
    }

    public void onBackPressed(){
        volverAtras(null);

    }





}