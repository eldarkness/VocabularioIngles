package com.eldarkness.vocabularioingles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;

public class crearCategoria extends AppCompatActivity {

    private String categoria;
    private EditText textoCategoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_categoria);
        textoCategoria = (EditText) findViewById(R.id.editTextCategoria);
    }

    public void anadirCategoria(View view){
        categoria = textoCategoria.getText().toString();
        Intent i = new Intent(this, ActivityAnadirPalabras.class);
        i.putExtra("categoria", categoria);
        startActivity(i);

    }


}