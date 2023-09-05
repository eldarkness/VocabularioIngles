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

        // hay que comprobar si esto servia para algo sino lo eliminaremos
        if(savedInstanceState != null){
            System.out.println("Tiene algooo");
        }
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_anadir_palabras);
        bbdd = new BBDD_Controller(this);
        palabraEspanol = (EditText) findViewById(R.id.editTextEsp);
        palabraIngles = (EditText) findViewById(R.id.editTextIngles);
        palabraEspanol.requestFocus();
        textoPalabraAnadida = (TextView) findViewById(R.id.mensajePalabraAnadida);

        spinnerCategorias = (Spinner) findViewById(R.id.spinnerCategorias);

    }

    public void volverAtras(View view){
        // se termina con la actividad para que la que llamo a esta (mainactivity) no pierda los datos al llamar al onCreate
        finish();
    }


    public void onResume() {
        super.onResume();
        listaCategorias = cargarCategorias();
        CargarSpinnerCategorias();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private ArrayList<String> cargarCategorias(){
        ArrayList<String> listaCategorias = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = bbdd.getReadableDatabase();

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

    public void IntroducirPalabrasDiccionario(View view){

        // tiene que añadir una palabra a la base de datos sino esta repetida, usar columna llamada Diccionario
        String palabraEsp = palabraEspanol.getText().toString();
        String palabraIng = palabraIngles.getText().toString();
        String categoria = spinnerCategorias.getSelectedItem().toString();

        if(palabraEsp.equalsIgnoreCase("") || palabraIng.equalsIgnoreCase("")
        || palabraEsp == null || palabraIng == null ){
            textoPalabraAnadida.setText("Debes introducir una palabra tanto en Español como en Inglés");
            return;
        }

        if(spinnerCategorias.getSelectedItem().toString().equalsIgnoreCase("Crea una categoria")){
            textoPalabraAnadida.setText("Primero debes crear una categoria");
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

    private void CargarSpinnerCategorias(){
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCategorias);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(spinnerArrayAdapter);

    }

    private String capitalizar(String palabra){
        String str = (palabra.substring(0, 1)).toUpperCase(Locale.ROOT) + (palabra.substring(1)).toLowerCase(Locale.ROOT);

        return str;
    }

    private void reiniciarCuadros(){
        palabraEspanol.setText("");
        palabraIngles.setText("");
        palabraEspanol.requestFocus();

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