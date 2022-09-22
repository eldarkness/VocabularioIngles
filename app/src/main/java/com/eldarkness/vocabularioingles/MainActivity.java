package com.eldarkness.vocabularioingles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<String> listaIng;
    public ArrayList<String> listaEsp;
    int indice;
    TextView textoPalabraIngles;
    EditText textoPalabraEspanol;
    TextView textoCuadroAcierto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoPalabraIngles = (TextView) findViewById(R.id.PalabraIngles);
        textoPalabraEspanol = (EditText) findViewById(R.id.PalabraEspanol);
        textoCuadroAcierto = (TextView) findViewById(R.id.mensajeAcierto);

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


    }

    public void PasarPalabra(View view) {
        indice = (int) (Math.random() * listaIng.size());
        textoPalabraEspanol.setText("");
        textoPalabraIngles.setText("" + listaIng.get(indice));

    }

    public void ComprobarPalabra(View view){
        String palabraEspanol = textoPalabraEspanol.getText().toString();
        String palabraIngles = textoPalabraIngles.getText().toString();

        if (palabraIngles.equalsIgnoreCase("")) {
            textoCuadroAcierto.setText("Debes pulsar el boton Siguiente Palabra para jugar");
        } else if (palabraEspanol.equalsIgnoreCase("")){
             textoCuadroAcierto.setText("Debes introducir una palabra en español para poder comprobarla");
            return;
        }
        if(listaEsp.get(indice).equalsIgnoreCase(palabraEspanol)){
            textoCuadroAcierto.setText("¡Has acertado!");
        }else{
            textoCuadroAcierto.setText("¡Has fallado!, la respuesta correcta era: " + listaEsp.get(indice));
        }

    }
}
