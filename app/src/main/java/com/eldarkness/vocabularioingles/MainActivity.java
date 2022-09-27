package com.eldarkness.vocabularioingles;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<String> listaIng;
    public ArrayList<String> listaEsp;
    int indice;
    EditText textoPalabraIngles;
    TextView textoPalabraEspanol;
    TextView textoCuadroAcierto;
    Boolean siguientePalabra;
    ImageView checkAcierto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoPalabraEspanol = (TextView) findViewById(R.id.PalabraEspanol);
        textoPalabraIngles = (EditText) findViewById(R.id.PalabraIngles);
        textoCuadroAcierto = (TextView) findViewById(R.id.mensajeAcierto);
        checkAcierto = (ImageView) findViewById(R.id.checkAcierto);

        listaIng = new ArrayList<>();
        listaEsp = new ArrayList<>();

        listaIng.add("Blue");
        listaEsp.add("Azul");
        listaIng.add("Orange");
        listaEsp.add("Naranja");
        listaIng.add("Cut");
        listaEsp.add("Cortar");
        listaIng.add("Speak");
        listaEsp.add("Hablar");
        listaIng.add("Play");
        listaEsp.add("Jugar");

        PasarPalabra(new View(this));
        siguientePalabra = false;
    }

    public void PasarPalabra(View view) {
        indice = (int) (Math.random() * listaEsp.size());
        textoPalabraEspanol.setText("");
        textoPalabraIngles.setText("");
        textoCuadroAcierto.setText("");
        textoPalabraEspanol.setText("" + listaEsp.get(indice));


    }

    public void ComprobarPalabra(View view){

        if (siguientePalabra){
            PasarPalabra(view);
            siguientePalabra = false;

        }else{

            String palabraEspanol = textoPalabraEspanol.getText().toString();
            String palabraIngles = textoPalabraIngles.getText().toString();

            if (palabraEspanol.equalsIgnoreCase("")) {
                textoCuadroAcierto.setText("Debes pulsar el boton Siguiente Palabra para jugar");
                return;
            } else if (palabraIngles.equalsIgnoreCase("")){
                textoCuadroAcierto.setText("Debes introducir una palabra en Ingles para poder comprobarla");
                return;
            }

            if(listaIng.get(indice).equalsIgnoreCase(palabraIngles)){
                textoCuadroAcierto.setText("¡Has acertado!");
                checkAcierto.setImageResource(R.drawable.check_ok);
                siguientePalabra = true;
            }else{
                checkAcierto.setImageResource(android.R.drawable.ic_delete);
                textoCuadroAcierto.setText("¡Has fallado!, la respuesta correcta era: " + listaIng.get(indice));

            }

        }



    }

    public void Salir(View view){
        System.exit(0);
    }

    public void cargarActividadExcelDAO(View view){
        Intent i = new Intent(this, Excel_DAO.class);
        startActivity(i);


    }
}
