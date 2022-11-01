package com.eldarkness.vocabularioingles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Carousel;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.eldarkness.vocabularioingles.BBDD.BBDD_Controller;
import com.eldarkness.vocabularioingles.BBDD.Estructura_BBDD;

import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityAnadirPalabras extends AppCompatActivity {

    private BBDD_Controller bbdd;
    EditText palabraEspanol;
    EditText palabraIngles;
    TextView textoPalabraAnadida;
    Spinner spinnerCategorias;
    String categoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_anadir_palabras);
        bbdd = new BBDD_Controller(this);
        palabraEspanol = (EditText) findViewById(R.id.editTextEsp);
        palabraIngles = (EditText) findViewById(R.id.editTextIngles);
        palabraEspanol.requestFocus();
        textoPalabraAnadida = (TextView) findViewById(R.id.mensajePalabraAnadida);

        spinnerCategorias = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        // Aqui añadiria las categorias guardadas en el bundle
        if(extras == null){
            list.add("Introduce categoria");
            list.add("Escuela");
        }else{
           list.add(extras.getString("categoria"));
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(spinnerArrayAdapter);
        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoria = parent.getItemAtPosition(position).toString();
                textoPalabraAnadida.setText(parent.getItemAtPosition(position).toString());

                //    campoMaquina.setText(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    public void IntroducirPalabrasDiccionario(View view){
        // tiene que añadir una palabra a la base de datos sino esta repetida, usar columna llamada Diccionario
        SQLiteDatabase sqLiteDatabase = bbdd.getWritableDatabase();
        String palabraEsp = palabraEspanol.getText().toString();
        String palabraIng = palabraIngles.getText().toString();

        if(!palabraEsp.equalsIgnoreCase("") && !palabraIng.equalsIgnoreCase("") ){
            palabraEsp = capitalizar(palabraEsp);
            palabraIng = capitalizar(palabraIng);

            if(buscarPalabra(palabraEsp)){
                textoPalabraAnadida.setText("La palabra " + palabraEsp + " ya esta en la base de datos");
            }else{
                ContentValues values = new ContentValues();
                values.put(Estructura_BBDD.NOMBRE_COLUMNA2,palabraEsp);
                values.put(Estructura_BBDD.NOMBRE_COLUMNA3,palabraIng);
                long newRowId = sqLiteDatabase.insert(Estructura_BBDD.TABLE_NAME, null, values);
                if (newRowId > 0){
                    textoPalabraAnadida.setText("La palabra " + palabraEsp + " se ha insertado en la base de datos");
                }
            }
        } else {
            textoPalabraAnadida.setText(R.string.error_anadir_palabras);
        }
        sqLiteDatabase.close();
        reiniciarCuadros();

    }

    private String capitalizar(String palabra){
        String str = (palabra.substring(0, 1)).toUpperCase(Locale.ROOT) + (palabra.substring(1)).toLowerCase(Locale.ROOT);

        return str;
    }

    public Boolean buscarPalabra(String palabraEsp){

        SQLiteDatabase sqLiteDatabase = bbdd.getReadableDatabase();
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
    public void volverAtras(View view){
        // se termina con la actividad para que la que llamo a esta (mainactivity) no pierda los datos al llamar al onCreate
        finish();

    }

    public void cargarActividadCrearCategoria(View view){
        Intent i = new Intent(this, crearCategoria.class);
        startActivity(i);

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