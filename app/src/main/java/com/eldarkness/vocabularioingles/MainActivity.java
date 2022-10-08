package com.eldarkness.vocabularioingles;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eldarkness.vocabularioingles.BBDD.BBDD_Controller;
import com.eldarkness.vocabularioingles.BBDD.Estructura_BBDD;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<String> listaIng;
    public ArrayList<String> listaEsp;
    BBDD_Controller bbdd_controller;
    int indice;
    EditText textoPalabraIngles;
    TextView textoPalabraEspanol;
    TextView textoCuadroAcierto;
    Boolean siguientePalabra;
    ImageView checkAcierto;
    Button botonComprobar;
    String palabraIngles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoPalabraEspanol = (TextView) findViewById(R.id.PalabraEspanol);
        textoPalabraIngles = (EditText) findViewById(R.id.PalabraIngles);
        textoCuadroAcierto = (TextView) findViewById(R.id.mensajeAcierto);
        checkAcierto = (ImageView) findViewById(R.id.checkAcierto);
        siguientePalabra = false;
        botonComprobar = (Button) findViewById(R.id.BotonComprobar);
        bbdd_controller = new BBDD_Controller(this);

        mostrarPalabra(new View(this));


    }

    // invoca la primera palabra llamando al metodo siguientepalabra en el caso de que el textview
    // de la palabra en español este vacio
    public void mostrarPalabra(View view){

        if(textoPalabraEspanol.getText().toString().equals("")){
            siguientePalabra = false;
            SiguientePalabra();
        }

    }

    // se le llama desde el metodo anterior si la variable booleana siguientePalabra esta a true
    // se debe extraer una palabra al hacer desde la base de datos en este metodo
    public void SiguientePalabra() {
        reiniciarCuadros();
        SQLiteDatabase sqLiteDatabase = bbdd_controller.getReadableDatabase();

        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + Estructura_BBDD.TABLE_NAME2, null);

        System.out.println("Hay " + c.getCount() + " palabras en la base de datos");

        if(c.getCount() == 0){
            textoCuadroAcierto.setText("Debes introducir alguna palabra en el diccionario primero");
            return;

        }
        indice = (int) (Math.random() * c.getCount());
        System.out.println(indice);
        c.moveToFirst();
        int cont = 1;
        while(cont < indice){
            c.moveToNext();
            cont++;
        }
        textoPalabraEspanol.setText(c.getString(1));
        palabraIngles = c.getString(2);
        System.out.println("Español: " + c.getString(1) + " Ingles: " + c.getString(2));
        c.close();
    }




    // ultimo metodo a rellenar
    public void ComprobarPalabra(View view){
        checkAcierto.setImageResource(0);
        if (siguientePalabra){
            SiguientePalabra();
            siguientePalabra = false;

        }else{


            if (textoPalabraEspanol.getText().toString().equalsIgnoreCase("")) {
                textoCuadroAcierto.setText("Debes pulsar el boton Siguiente Palabra para jugar");
                return;
            } else if (textoPalabraIngles.getText().toString().equalsIgnoreCase("")){
                textoCuadroAcierto.setText("Debes introducir una palabra en Ingles para poder comprobarla");
                return;
            }

            if(textoPalabraIngles.getText().toString().equalsIgnoreCase(palabraIngles)){
                textoCuadroAcierto.setText("¡Has acertado!");
                checkAcierto.setImageResource(R.drawable.check_ok);
                siguientePalabra = true;
                palabraIngles = "";
            }else{
                checkAcierto.setImageResource(android.R.drawable.ic_delete);
                textoPalabraIngles.requestFocus();

            }


        }



    }



    // Utilidades, metodos pequeños pero muy usados, metodos poco complejos

    private void reiniciarCuadros(){
        textoPalabraEspanol.setText("");
        textoPalabraIngles.setText("");
        textoCuadroAcierto.setText("");
        textoPalabraIngles.requestFocus();
        // 0 equivale a nulo
        checkAcierto.setImageResource(0);

    }



    public void Salir(View view){
        finish();
    }

    public void cargarActividadAnadirPalabras(View view){
        Intent i = new Intent(this, ActivityAnadirPalabras.class);
        startActivity(i);

    }





}
