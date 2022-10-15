package com.eldarkness.vocabularioingles;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eldarkness.vocabularioingles.BBDD.BBDD_Controller;
import com.eldarkness.vocabularioingles.BBDD.Estructura_BBDD;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BBDD_Controller bbdd_controller;
    int indice;
    int error = 0;
    EditText textoPalabraIngles;
    TextView textoPalabraEspanol;
    TextView textoCuadroAcierto;
    Boolean siguientePalabra;
    ImageView checkAcierto;
    Button botonComprobar;
    String palabraIngles;
    private ControladorPalabras controladorPalabras;
    EventoTeclado eventoTeclado;
    InputMethodManager miteclado;
    ArrayList<String> listaEsp = new ArrayList<>();
    ArrayList<String> listaEng = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controladorPalabras = new ControladorPalabras();
        textoPalabraEspanol = (TextView) findViewById(R.id.PalabraEspanol);
        textoPalabraIngles = (EditText) findViewById(R.id.PalabraIngles);
        textoCuadroAcierto = (TextView) findViewById(R.id.mensajeAcierto);
        checkAcierto = (ImageView) findViewById(R.id.checkAcierto);
        siguientePalabra = false;
        botonComprobar = (Button) findViewById(R.id.BotonComprobar);
        eventoTeclado = new EventoTeclado();
        textoPalabraIngles.setOnEditorActionListener(eventoTeclado);
        bbdd_controller = new BBDD_Controller(this);
        miteclado = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //miteclado.hideSoftInputFromWindow(textoPalabraIngles.getWindowToken(),InputMethodManager.HIDE_IMPLICIT_ONLY);
        mostrarPalabra(null);
        /*try{
            if(getIntent().getExtras().getStringArrayList("listaEsp") != null
                && getIntent().getExtras().getStringArrayList("listaEng") != null){

                rellenarArrayList();
                controladorPalabras.mostrarLista();
                System.out.println(controladorPalabras.contador);
                }

        }catch(Exception e){

        }*/
        //miteclado.showSoftInput(textoPalabraIngles,InputMethodManager.SHOW_IMPLICIT);
        //miteclado.showSoftInput(textoPalabraIngles, InputMethodManager.SHOW_FORCED);

    }

    /*
    Falta por implementar:
    -Que no se cierre el teclado cuando se llame al metodo comprobarPalabra desde el evento de teclado dandole al next
    -Tema de persistencia de datos con el tema de las listas etc
    -Crear una lista para poder ver el resultado de las 3-5 ultimas palabras.

     */


    /**
     *
     * @param view
     *
     * Metodo que es llamado automaticamente en el onCreate para cargar la primera palabra sin que el
     * usuario tenga que hacer nada. Se puede llamar tambien desde el front para mostrar la traduccion
     * de la palabra en español
     */
    public void mostrarPalabra(View view){

        if(!textoPalabraEspanol.getText().toString().isEmpty()){
            textoPalabraIngles.setText(palabraIngles);

        }else{
            SiguientePalabra();
        }

    }



    /**
     * Se llama siempre desde el metodo comprobarPalabra tanto si el usuario ha acertado la palabra como sino.
     * Primero se reinician los cuadros de texto y se comprueba si el contador esta a cero. Si es asi se carga una palabra
     * de la lista de palabras equivocadas sino se hace un select de todas la palabras de la bbdd y se carga una al azar
      */
    public void SiguientePalabra() {

        reiniciarCuadros();
        if(controladorPalabras.contador > 0){
            controladorPalabras.contador--;
        }

        if(controladorPalabras.contador == 0 && controladorPalabras.getPalabrasEquivocadas().size() > 0){
            // cargar la palabra equivocada y borrarla de la lista y llamar al metodo generarcontador
            textoPalabraEspanol.setText(controladorPalabras.getPalabrasEquivocadas().get(0).getPalabraEsp());
            palabraIngles = controladorPalabras.getPalabrasEquivocadas().get(0).getPalabraEng();
            controladorPalabras.mostrarLista();
            controladorPalabras.getPalabrasEquivocadas().remove(0);
            controladorPalabras.mostrarLista();
            System.out.println("Oportunidad extra, se vuelve a cargar la palabra: "+  textoPalabraEspanol.getText().toString());
            if(controladorPalabras.getPalabrasEquivocadas().size() > 0){
                controladorPalabras.generarContador();
            }

            return;

        }
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
        int cont = 0;
        while(cont < indice){
            c.moveToNext();
            cont++;
        }
        textoPalabraEspanol.setText(c.getString(1));
        palabraIngles = c.getString(2);
        System.out.println("Español: " + c.getString(1) + " Ingles: " + c.getString(2));
        c.close();

    }

    /**
     *
     * @param view
     */
    public void ComprobarPalabra(View view){

        checkAcierto.setImageResource(0);

        // Primero se comprueba que ninguno de los dos cuadros de texto este vacio, si es asi se sale del metodo
        if (textoPalabraEspanol.getText().toString().equalsIgnoreCase("")) {
            textoCuadroAcierto.setText("Debes pulsar el boton Mostrar Palabra para jugar");
            return;
        } else if (textoPalabraIngles.getText().toString().equalsIgnoreCase("")){
            textoCuadroAcierto.setText("Debes introducir una palabra en Ingles para poder comprobarla");
            return;
        }

        // se comprueba la palabra, si es acertada se muestra icono se pone el contador de error a 0 y la palabraingles a cadena vacia
        // si es erronea se le da una oportunidad mas, si se vuelve a equivocar se añade esa palabra a la lista del controladorPalabras y
        // si el contador estaba a 0 genera lo llama para generar un numero aleatorio.
        if(textoPalabraIngles.getText().toString().equalsIgnoreCase(palabraIngles)){
            textoCuadroAcierto.setText("¡Has acertado!");
            checkAcierto.setImageResource(R.drawable.check_ok);
            error = 0;
            palabraIngles = "";
            SiguientePalabra();
        }else{
            switch (error){
                case 0:
                    textoCuadroAcierto.setText("¡Has Fallado, prueba otra vez!");
                    checkAcierto.setImageResource(android.R.drawable.ic_delete);
                    error++;
                    textoPalabraIngles.requestFocus();
                    break;
                case 1:
                    textoCuadroAcierto.setText("¡Has Fallado, tendrás otra oportunidad mas adelante");
                    error = 0;
                    checkAcierto.setImageResource(android.R.drawable.ic_delete);
                    controladorPalabras.anadirPalabras(textoPalabraEspanol.getText().toString(),palabraIngles);
                    // generara un contador la primera vez que el usuario se equivoque en una palabra y cada vez
                    // que se añada la primera palabra a la lista de palabras equivocadas
                    if(controladorPalabras.contador == 0){
                        controladorPalabras.generarContador();
                    }
                    SiguientePalabra();
                    break;
            }

        }
        //miteclado.hideSoftInputFromWindow(textoPalabraIngles.getWindowToken(),0);

    } // metodo comprobarPalabra


    // Utilidades, metodos pequeños pero muy usados, metodos poco complejos

    private void reiniciarCuadros(){
        textoPalabraEspanol.setText("");
        textoPalabraIngles.setText("");
        textoCuadroAcierto.setText("");
        textoPalabraIngles.requestFocus();
        // 0 equivale a nulo
        checkAcierto.setImageResource(0);

    }

    private void rellenarArrayList() {
        controladorPalabras.contador = getIntent().getExtras().getInt("contador",0);
        listaEsp = getIntent().getExtras().getStringArrayList("listaEsp");
        listaEng = getIntent().getExtras().getStringArrayList("listaEng");
        for (int i = 0; i < listaEsp.size(); i++) {
            controladorPalabras.getPalabrasEquivocadas().add(new PalabraEquivocada(listaEsp.get(i),listaEng.get(i)));
        }

    }

    class EventoTeclado implements TextView.OnEditorActionListener{

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if(actionId == EditorInfo.IME_ACTION_DONE){
                // metodo al que queremos llamar
                /*InputMethodManager miteclado = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                miteclado.showSoftInputFromInputMethod(textoPalabraIngles.getWindowToken(), 0);*/
                ComprobarPalabra(null);
            }

            return false;
        }
    }

    public void Salir(View view){
        finish();
    }

    public void cargarActividadAnadirPalabras(View view){
        if (controladorPalabras.getPalabrasEquivocadas().size() > 0){
            for (int i = 0; i < controladorPalabras.getPalabrasEquivocadas().size(); i++){
                listaEsp.add(controladorPalabras.getPalabrasEquivocadas().get(i).getPalabraEsp());
                listaEng.add(controladorPalabras.getPalabrasEquivocadas().get(i).getPalabraEng());
            }
        }else{
            System.out.println("upps parece que la lista esta vacia, valor: " + controladorPalabras.getPalabrasEquivocadas().size());
        }

        Intent i = new Intent(this, ActivityAnadirPalabras.class);

        i.putExtra("contador",controladorPalabras.contador);
        i.putExtra("listaEsp",listaEsp);
        i.putExtra("listaEng",listaEng);

        startActivity(i);

    }


}
