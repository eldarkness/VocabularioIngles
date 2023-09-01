package com.eldarkness.vocabularioingles;

import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.eldarkness.vocabularioingles.BBDD.BBDD_Controller;
import com.eldarkness.vocabularioingles.BBDD.Estructura_BBDD;
import com.eldarkness.vocabularioingles.ExcelParser.ExcelController;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BBDD_Controller bbdd_controller;
    int indice;
    int error = 0;
    EditText textoPalabraIngles;
    TextView textoPalabraEspanol;
    TextView textoMensaje;
    Boolean siguientePalabra;
    ImageView checkAcierto;
    Button botonComprobar;
    Button botonCargarPalabrasCategoria;
    String palabraIngles;
    private ControladorPalabras controladorPalabras;
    EventoTeclado eventoTeclado;

    InputMethodManager IMM;

    private final int REQUEST_CODE_EXCEL = 1;
    private ArrayList<PalabraDiccionario> listaPalabras;
    private ArrayList<PalabraDiccionario> listaPalabrasBackUp;
    private ArrayList<String> listaCategorias;
    Spinner spinnerCategorias;

    ExcelController excelController;

    private LinearLayout layoutRegistro;
    final static int APP_STORAGE_ACCESS_REQUEST_CODE = 501;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controladorPalabras = new ControladorPalabras();
        textoPalabraEspanol = (TextView) findViewById(R.id.PalabraEspanol);
        textoPalabraIngles = (EditText) findViewById(R.id.PalabraIngles);
        layoutRegistro = findViewById(R.id.layoutRegistro);
        siguientePalabra = false;
        botonComprobar = (Button) findViewById(R.id.BotonComprobar);
        botonCargarPalabrasCategoria = (Button) findViewById(R.id.BotonCargarPalCat);
        eventoTeclado = new EventoTeclado();
        textoPalabraIngles.setOnEditorActionListener(eventoTeclado);
        bbdd_controller = new BBDD_Controller(this);
        listaPalabras = new ArrayList<>();
        listaPalabrasBackUp = new ArrayList<>();
        spinnerCategorias = (Spinner) findViewById(R.id.spinnerCategorias2);
        System.out.println("La lista tiene " + listaPalabras.size() + " palabras");
        IMM = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        listaCategorias = cargarCategorias();
        CargarSpinnerCategorias();
        rellenarLista();
        mostrarPalabra(null);

        // Primero comprobamos si la APP tiene permiso para acceder al almacenamiento externo
        // sino lo pedimos al inicio de la APP
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if(Environment.isExternalStorageManager()){
                System.out.println("Se tiene acceso al almacenamiento externo");
            }else{
                permisoAcceso();
            }
        }

        // Aqui empieza el codigo relacionado con el excel
        excelController = new ExcelController();
        excelController.createExcel(getApplicationContext(), "LibroExcel.xls");
        excelController.leerExcel();


        //miteclado.showSoftInput(textoPalabraIngles,InputMethodManager.SHOW_IMPLICIT);
        //miteclado.showSoftInput(textoPalabraIngles, InputMethodManager.SHOW_FORCED);

    }

    private void permisoAcceso(){
        Intent intent = new Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, Uri.parse("package:" + BuildConfig.APPLICATION_ID));
        startActivityForResult(intent, APP_STORAGE_ACCESS_REQUEST_CODE);
    }

    public void openFileChooser(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/vnd.ms-excel");
        startActivityForResult(intent, REQUEST_CODE_EXCEL);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        // Comprobacion del permiso para poder acceder a las carpetas del dispositivo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && requestCode == APP_STORAGE_ACCESS_REQUEST_CODE) {
            if(Environment.isExternalStorageManager()){
                System.out.println("El usuario ha autorizado el permiso");
            }
            System.out.println("El usuario no ha autorizado el permiso");

        }else{
            System.out.println("La version del SDK es inferior a la minima requerida");
        }

        // Comprobacion a la llamada de intent para seleccionar un libro excel en el dispositivo
        if(requestCode == REQUEST_CODE_EXCEL && resultCode == Activity.RESULT_OK){
            if(data == null){
                return;
            }
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                System.out.println(inputStream.toString());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                File file = new File(getCacheDir(), "cacheFileAppeal.srl");
                try (OutputStream output = new FileOutputStream(file)) {
                    byte[] buffer = new byte[4 * 1024]; // or other buffer size
                    int read;

                    while ((read = input.read(buffer)) != -1) {
                        output.write(buffer, 0, read);
                    }

                    output.flush();
                }
            } finally {
                input.close();
            }


            System.out.println("***** Comienzan los juegos del hambre *****");
            System.out.println(uri.getPath());
            System.out.println(uri.getHost());

            System.out.println(uri.getScheme());
            System.out.println(uri.normalizeScheme().getPath());
            /*try {
                FileInputStream fileInputStream = new FileInputStream(file);
                Workbook workbook = new HSSFWorkbook(fileInputStream);
                System.out.println("Estoy recuperando la info de tu excel" + workbook.getSheetAt(0).getRow(0).getCell(0));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/
            //System.out.println("******* El URI ese es " +file.getName() + "  **********");

        }
    }



    private LinearLayout crearLayout(String palabraIngles, String palabraEspanol, int estado){
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        ImageView imageView = new ImageView(this);
        TextView textView = new TextView(this);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(21);

        if (estado == 0) {
            textView.setTextColor(Color.BLACK);
            textView.setTextColor(Color.parseColor("#229994" ));
            textView.setText(palabraEspanol + "  |  " + palabraIngles);
            imageView.setImageResource(R.drawable.check_ok);
        }
        else if (estado == 1) {
            textView.setTextColor(Color.parseColor("#f62919" ));
            textView.setText(R.string.primer_fallo);

        }
        else{
            textView.setTextColor(Color.parseColor("#f62919" ));
            textView.setText(palabraEspanol);
            imageView.setImageResource(android.R.drawable.ic_delete);

        }

        linearLayout.addView(textView, 0);
        linearLayout.addView(imageView,80, 80);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 1.0f;
        params.gravity = Gravity.CENTER;
        linearLayout.setLayoutParams(params);

        return linearLayout;
    }

    private TextView crearVista(String palabraIngles, String palabraEspanol, int estado){
        // aciertos en verde esmeralda, fallos en rojo
        TextView textView = new TextView(this);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(21);

        if (estado == 0) {
            textView.setTextColor(Color.parseColor("#229994" ));
            textView.setText(palabraEspanol + " -> " + palabraIngles);
        }
        else if (estado == 1) {
            textView.setTextColor(Color.parseColor("#f62919" ));
            textView.setText(R.string.primer_fallo);
        }
        else{
            textView.setTextColor(Color.parseColor("#f62919" ));
            textView.setText(R.string.segundo_fallo);
        }

        return textView;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_mainactivity, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();

        if(id == R.id.menu_IntroducirPalabras){
            cargarActividadAnadirPalabras(null);

        }else if(id == R.id.menu_CrearCategoria){
            cargarActividadCrearCategoria(null);
        }else if(id == R.id.menu_cargar_excel) {
            openFileChooser(null);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void cargarExcel(){

    }


    public void onResume(){
        super.onResume();
        // se llama a este metodo para que se cargen, en la lista ya iniciada, las palabas que se
        // acaban de añadir en la actividad de la que se esta volviendo
        anadirUltimasPalabras();
        listaCategorias = cargarCategorias();
        CargarSpinnerCategorias();
        IMM.showSoftInputFromInputMethod(textoPalabraIngles.getWindowToken(),InputMethodManager.SHOW_FORCED);
    }

    private void rellenarLista(){
        // Tiene que comprobar la categoria que tenga seleccionada el spinner y rellenar la lista solo con esas palabras
        // por lo que hay que acceder a la columna 3 de esa tabla
        SQLiteDatabase sqLiteDatabase = bbdd_controller.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + Estructura_BBDD.TABLE_NAME, null);

        if(c.getCount() == 0){
            //textoMensaje.setText(R.string.diccionario_vacio);
            botonCargarPalabrasCategoria.setEnabled(false);
            return;
        }
        c.moveToFirst();

        listaPalabras.clear();
        listaPalabrasBackUp.clear();
        String categoria = spinnerCategorias.getSelectedItem().toString();
        while(!c.isAfterLast()){
            if(categoria.equalsIgnoreCase("Todas")){
                listaPalabras.add(new PalabraDiccionario(c.getString(1),c.getString(2)));
                listaPalabrasBackUp.add(new PalabraDiccionario(c.getString(1),c.getString(2)));
            }else if(categoria.equalsIgnoreCase(c.getString(3))){
                listaPalabras.add(new PalabraDiccionario(c.getString(1),c.getString(2)));
                listaPalabrasBackUp.add(new PalabraDiccionario(c.getString(1),c.getString(2)));
            }
            c.moveToNext();
        }
        c.close();


    }


    /**
     *
     * @param view
     *
     * Metodo que es llamado automaticamente en el onCreate para cargar la primera palabra sin que el
     * usuario tenga que hacer nada. Se puede llamar tambien desde el front para mostrar la traduccion
     * de la palabra en español
     */
    public void mostrarPalabra(View view){


        // sino esta vacio significa que hay una palabra en español y que el usuario quiere saber la respuesta
        if(!textoPalabraEspanol.getText().toString().isEmpty()){
            textoPalabraIngles.setText(palabraIngles);

        }else if (listaPalabras.size() == 0){
            listaPalabrasBackUp.clear();
            rellenarLista();
        }else{
            SiguientePalabra();
        }

    }


    public void CargarPalabrasPorCategoria(View view){
        reiniciarCuadros(false);
        rellenarLista();

        if(listaPalabras.size() == 0){
            //textoMensaje.setText(getString(R.string.no_palabras_categoria) + spinnerCategorias.getSelectedItem().toString());
        }else{
            //textoMensaje.setText("Se han cargado las palabras de la categoria: " +spinnerCategorias.getSelectedItem().toString());
        }
        mostrarPalabra(null);

    }


    /**
     * Se llama siempre desde el metodo comprobarPalabra tanto si el usuario ha acertado la palabra como sino.
     * Primero se reinician los cuadros de texto y se comprueba si el contador esta a cero. Si es asi se carga una palabra
     * de la lista de palabras equivocadas sino se hace un select de todas la palabras de la bbdd y se carga una al azar
      */
    public void SiguientePalabra() {

        System.out.println("Acabo de entrar a siguientepalabra \n");
        mostrarLista(listaPalabras);
        reiniciarCuadros(false);
        if(controladorPalabras.contador > 0){
            controladorPalabras.reducirContador();
        }

        if(controladorPalabras.contador == 0 && controladorPalabras.getPalabrasEquivocadas().size() > 0){
            // carga la palabra equivocada, la borrar de la lista y llama al metodo generarcontador si es necesario
            textoPalabraEspanol.setText(controladorPalabras.getPalabrasEquivocadas().get(0).getPalabraEsp());
            palabraIngles = controladorPalabras.getPalabrasEquivocadas().get(0).getPalabraEng();
            controladorPalabras.getPalabrasEquivocadas().remove(0);
            //textoMensaje.setText("Oportunidad extra, se vuelve a cargar la palabra: " + textoPalabraEspanol.getText().toString());
            if(controladorPalabras.getPalabrasEquivocadas().size() > 0){
                controladorPalabras.generarContador();
            }
            return;
        }

        //numero aleatorio
        int indice = (int) (Math.random() * listaPalabras.size());
        System.out.println("Sacando numero aleatorio...");
        System.out.println("Palabras en la listaPalabras: " + listaPalabras.size());
        System.out.println("Numero aleatorio: " +indice);
        textoPalabraEspanol.setText(listaPalabras.get(indice).getPalabraEsp());
        palabraIngles = listaPalabras.get(indice).getPalabraEng();
        System.out.println("Español: " + listaPalabras.get(0).getPalabraEsp() + " Ingles: " + listaPalabras.get(0).getPalabraEng());
        listaPalabras.remove(indice);

        // ocultar aqui el layout de las categorias para que to do se suba para arriba

        //miteclado.showSoftInputFromInputMethod(null, null, null);
    }

    /**
     * @param view
     *
     * Este metodo comprueba la palabra, si es acertada se muestra icono, se pone el contador de error a 0 y la palabraingles a cadena vacia
     * si es erronea se le da una oportunidad mas, si se vuelve a equivocar se añade esa palabra a la lista del controladorPalabras y
     * si el contador estaba a 0 llama al metodo correspondiente para generar un numero aleatorio.
     */

    public void ComprobarPalabra(View view){

        // Primero se comprueba que ninguno de los dos cuadros de texto este vacio, si es asi se sale del metodo
        if (textoPalabraEspanol.getText().toString().equalsIgnoreCase("")) {
            //textoMensaje.setText(R.string.pulsa_mostrarPalabra);
            return;
        } else if (textoPalabraIngles.getText().toString().equalsIgnoreCase("")){
            //textoMensaje.setText(R.string.introduce_palabraIngles);
            return;
        }

        //acierto
        if(textoPalabraIngles.getText().toString().equalsIgnoreCase(palabraIngles)){

            layoutRegistro.addView(crearLayout(palabraIngles, textoPalabraEspanol.getText().toString(), 0));
            error = 0;
            palabraIngles = "";


        // fallo, si era el primer error entonces la variable error se setea a 1, si ha sido el segundo error
        // se pone la variable error a 0 y se añade la palabra a a lista de fallos
        }else{
            switch (error){
                case 0:

                    layoutRegistro.addView(crearLayout(palabraIngles, textoPalabraEspanol.getText().toString(), 1));
                    error++;
                    textoPalabraIngles.requestFocus();
                    break;
                case 1:

                    error = 0;

                    controladorPalabras.anadirPalabras(textoPalabraEspanol.getText().toString(),palabraIngles);
                    layoutRegistro.addView(crearLayout(palabraIngles, textoPalabraEspanol.getText().toString(), 2));
                    // generara un contador la primera vez que el usuario se equivoque en una palabra y cada vez
                    // que se añada la primera palabra a la lista de palabras equivocadas
                    if(controladorPalabras.contador == 0){
                        controladorPalabras.generarContador();
                    }

                    break;
            }

        }

        if(layoutRegistro.getChildCount() > 10){
            layoutRegistro.removeViewAt(10);
        }

        // Una vez que se ha comprobado la palabra se comprueba si quedan palabras en la lista
        // si hay palabras entonces se carga la siguiente sino se llama al metodo rellenarLista
        if(error == 0 && listaPalabras.size() == 0){
            rellenarLista();
            SiguientePalabra();
        }else if(error == 0){
            SiguientePalabra();
        }


    } // fin del metodo comprobarPalabra


    /**
     * Al volver desde la actividad añadirPalabras comprueba si hay alguna nueva ya las añade a la lista
     * Habra que comprobar en un futuro que solo se añadan las palabras nuevas introducidas en la categoria
     * que actualmente este cargada
     */

    private void anadirUltimasPalabras(){
        SQLiteDatabase sqLiteDatabase = bbdd_controller.getReadableDatabase();

        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + Estructura_BBDD.TABLE_NAME, null);

        if(c.getCount() == 0){
            return;
        }
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(!estaEnLalista(c.getString(1))){
                listaPalabras.add(new PalabraDiccionario(c.getString(1),c.getString(2)));
                listaPalabrasBackUp.add(new PalabraDiccionario(c.getString(1),c.getString(2)));
                System.out.println("Se añadio la palabra: " +listaPalabras.get(listaPalabras.size()-1).getPalabraEsp());
            }

            c.moveToNext();

        }
        System.out.println("La lista tiene " + listaPalabras.size() + " palabras");
        c.close();

    }


    /******
     *
     * Utilidades, metodos pequeños pero muy usados, metodos poco complejos, generalmente privates o classes internas
     *                                                                                                          ******/

    private boolean estaEnLalista(String palabraEsp) {
        for (int i = 0; i < listaPalabrasBackUp.size(); i++){
            if (palabraEsp.equalsIgnoreCase(listaPalabrasBackUp.get(i).getPalabraEsp())) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<String> cargarCategorias(){
        ArrayList<String> listaCategorias = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = bbdd_controller.getReadableDatabase();

        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + Estructura_BBDD.TABLE2_NAME, null);

        if(c.getCount()>0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                listaCategorias.add(c.getString(1));
                c.moveToNext();
            }
            listaCategorias.add(0,"Todas");
        }else{
            listaCategorias.add("Crea una Categoria");
        }
        c.close();
        return listaCategorias;


    }

    private void CargarSpinnerCategorias(){
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCategorias);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(spinnerArrayAdapter);

    }


    private void reiniciarCuadros(boolean reiniciarMensaje){
        textoPalabraEspanol.setText("");
        textoPalabraIngles.setText("");
        if(reiniciarMensaje){

        }
        textoPalabraIngles.requestFocus();
        palabraIngles = "";
        // 0 equivale a nulo


    }


    // clase interna, pone a la escucha el edittext que ingresa la palabra en ingles y llama al metodo comprobarPalabra
    class EventoTeclado implements TextView.OnEditorActionListener{

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if(actionId == EditorInfo.IME_ACTION_NEXT){
                // metodo al que queremos llamar
                ComprobarPalabra(null);
                IMM.showSoftInputFromInputMethod(textoPalabraIngles.getWindowToken(),InputMethodManager.SHOW_FORCED);
            }
            return false;
        }
    }

    private void mostrarLista(ArrayList<PalabraDiccionario> listaPalabras){
        if(listaPalabras.size() > 0){
            for (PalabraDiccionario palabra : listaPalabras) {
                System.out.print("Ingles: " + palabra.getPalabraEng() + " | Español: "  +palabra.getPalabraEsp() + "\n" );
            }
        }
    }

    // Carga de actividades
    public void cargarActividadAnadirPalabras(View view){
        if (controladorPalabras.getPalabrasEquivocadas().size() > 0){
            System.out.println("La lista Palabras Equivocadas tiene: " + controladorPalabras.getPalabrasEquivocadas().size() + " palabras");
        }else{
            System.out.println("upps parece que la lista esta vacia, valor: " + controladorPalabras.getPalabrasEquivocadas().size());
        }

        Intent i = new Intent(this, ActivityAnadirPalabras.class);

        startActivity(i);

    }

    private void cargarActividadCrearCategoria(Object o) {
        Intent i = new Intent(this, crearCategoria.class);
        startActivity(i);
    }





}
