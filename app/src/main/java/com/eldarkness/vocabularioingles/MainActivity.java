package com.eldarkness.vocabularioingles;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoPalabraEspanol = (TextView) findViewById(R.id.PalabraEspanol);
        textoPalabraIngles = (EditText) findViewById(R.id.PalabraIngles);
        textoCuadroAcierto = (TextView) findViewById(R.id.mensajeAcierto);
        checkAcierto = (ImageView) findViewById(R.id.checkAcierto);
        bbdd_controller = new BBDD_Controller(this);

        listaIng = new ArrayList<>();
        listaEsp = new ArrayList<>();

        anadirPalabras(bbdd_controller );
        System.out.println(listaIng.toString());
        System.out.println(listaEsp.toString());

        SiguientePalabra(new View(this));
        siguientePalabra = false;
    }

    /**
     *
     * @param bbdd_controller
     *      *
     * Es el primer metodo que se ejecuta, lo que hace es conectar con la base de datos sqlite y hacer un select-*
     * para extraer todas las palabras de la base de datos y con el metodo comprobarPalabraLista se va comprobando
     * si esa polabra esta o no en la lista de palabras que usuara la app que son dos arraylist de string uno para
     * cada idioma, sino esta esa palabra se añadira tanto en español como en ingles.
     */
    public void anadirPalabras(BBDD_Controller bbdd_controller){
        SQLiteDatabase sqLiteDatabase = bbdd_controller.getReadableDatabase();

        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + Estructura_BBDD.TABLE_NAME, null);
        c.moveToFirst();
        System.out.println(c.getCount());
        while(!c.isAfterLast()){
            /*if(!comprobarPalabraLista(c.getString(1))){
                listaIng.add(c.getString(1));
                listaEsp.add(c.getString(2));
            }*/
            listaIng.add(c.getString(1));
            listaEsp.add(c.getString(2));
            System.out.println(c.getString(1));
            c.moveToNext();
        }


        c.close();

    }


    /*
    public Boolean comprobarPalabraLista(String palabra){

        for (int i = 0; i < listaIng.size(); i++){
            System.out.println("Comprobando la palabra de la base de datos: " + palabra +
                    " si coincide con alguna de la lista " + listaIng.get(i));

            if(palabra.equalsIgnoreCase(listaIng.get(i))){
                return true;
            }

        }
        return false;

    }*/


    public void SiguientePalabra(View view) {
        indice = (int) (Math.random() * listaEsp.size());
        textoPalabraEspanol.setText("");
        textoPalabraIngles.setText("");
        textoCuadroAcierto.setText("");
        textoPalabraEspanol.setText("" + listaEsp.get(indice));


    }

    public void ComprobarPalabra(View view){
        System.out.println("comprobar palabra " +listaIng.size());
        if (siguientePalabra){
            SiguientePalabra(view);
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
