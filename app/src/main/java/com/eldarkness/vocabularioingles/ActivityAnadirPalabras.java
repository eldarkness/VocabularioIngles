package com.eldarkness.vocabularioingles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.eldarkness.vocabularioingles.BBDD.BBDD_Controller;
import com.eldarkness.vocabularioingles.BBDD.Estructura_BBDD;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityAnadirPalabras extends AppCompatActivity {

    private BBDD_Controller bbdd;
    EditText palabraEspanol;
    EditText palabraIngles;
    TextView textoPalabraAnadida;
    Spinner spinnerCategorias;
    ArrayList<String> listaCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_anadir_palabras);
        bbdd = new BBDD_Controller(this);
        palabraEspanol = (EditText) findViewById(R.id.editTextEsp);
        palabraIngles = (EditText) findViewById(R.id.editTextIngles);
        palabraEspanol.requestFocus();
        textoPalabraAnadida = (TextView) findViewById(R.id.mensajePalabraAnadida);
        spinnerCategorias = (Spinner) findViewById(R.id.spinnerCategorias);

    }

    public void IntroducirPalabrasDiccionario(View view){

        // tiene que añadir una palabra a la base de datos sino esta repetida, usar columna llamada Diccionario
        String palabraEsp = palabraEspanol.getText().toString();
        String palabraIng = palabraIngles.getText().toString();
        String categoria = spinnerCategorias.getSelectedItem().toString();

        if(palabraEsp.equalsIgnoreCase("") || palabraIng.equalsIgnoreCase("")){
            textoPalabraAnadida.setText(R.string.AnadirPalabras_PalabrasVacio);
            return;
        }

        if(spinnerCategorias.getSelectedItem().toString().equalsIgnoreCase("Crea una categoria")){
            textoPalabraAnadida.setText(R.string.AnadirPalabras_NoCategorias);
            cargarActividadCrearCategoria(null);
        }

        if(bbdd.IntroducirPalabrasDiccionario(palabraEsp,palabraIng, categoria)){
            // decir que se han añadido las palabras al diccionario
            reiniciarCuadros();
        }
    }

    public void cargarActividadCrearCategoria(View view){
        Intent i = new Intent(this, ActivityCrearCategoria.class);
        startActivity(i);

    }

    public void onResume() {
        super.onResume();
        listaCategorias = bbdd.cargarCategorias();
        CargarSpinnerCategorias();
    }

    private void CargarSpinnerCategorias(){
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCategorias);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(spinnerArrayAdapter);

    }

    private void reiniciarCuadros(){
        palabraEspanol.setText("");
        palabraIngles.setText("");
        palabraEspanol.requestFocus();

    }

    public void volverAtras(View view){
        // se termina con la actividad para que la que llamo a esta (mainactivity) no pierda los datos al llamar al onCreate
        finish();
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}

 /*String[] arraySpinner = new String[] {
               ""
        };
        Spinner s = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s.setAdapter(adapter);
        adapter.add("Casa");
        adapter.notifyDataSetChanged();
        s.setAdapter(adapter);*/

 /* creo que al final no va a hacer falta el setOnItemSelectedListener pero de momento lo dejo ahsta que lo compruebe bien
        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                palabraSpinner = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //System.out.println();*/