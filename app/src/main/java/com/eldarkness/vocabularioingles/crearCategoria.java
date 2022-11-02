package com.eldarkness.vocabularioingles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class crearCategoria extends AppCompatActivity {

    private String categoria;
    private EditText textoCategoria;
    private ArrayList<String> categorias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_categoria);
        textoCategoria = (EditText) findViewById(R.id.editTextCategoria);
        categorias = new ArrayList<>();
    }

    public void anadirCategoria(View view){
        categorias.add(textoCategoria.getText().toString());
        textoCategoria.setText("");
        textoCategoria.requestFocus();

    }

    public void volverAtras(View view){
        Intent i = new Intent(this, ActivityAnadirPalabras.class);

        i.putStringArrayListExtra("categorias",categorias);
        startActivity(i);
        finish();
    }

    public void onBackPressed(){
        volverAtras(null);

    }


}