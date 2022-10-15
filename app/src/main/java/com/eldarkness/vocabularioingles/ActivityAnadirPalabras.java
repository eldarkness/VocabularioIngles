package com.eldarkness.vocabularioingles;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.eldarkness.vocabularioingles.BBDD.BBDD_Controller;
import com.eldarkness.vocabularioingles.BBDD.Estructura_BBDD;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityAnadirPalabras extends AppCompatActivity {

    private BBDD_Controller bbdd;
    EditText palabraEspanol;
    EditText palabraIngles;
    private ArrayList<String> listaEsp;
    private ArrayList<String> listaEng;
    private int contador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            listaEsp = getIntent().getExtras().getStringArrayList("listaEsp");
            listaEng = getIntent().getExtras().getStringArrayList("listaEng");
            contador = getIntent().getExtras().getInt("contador",0);
            System.out.println("La palabra " + listaEsp.get(0) + " viajo hasta aqui desde la actividad anterior");
            System.out.println("La palabra " + listaEng.get(0) + " viajo hasta aqui desde la actividad anterior");
        }catch (Exception e){

        }

        setContentView(R.layout.activity_anadir_palabras);
        bbdd = new BBDD_Controller(this);
        palabraEspanol = (EditText) findViewById(R.id.editTextEsp);
        palabraIngles = (EditText) findViewById(R.id.editTextIngles);
        palabraEspanol.requestFocus();

    }

    public void IntroducirPalabrasDiccionario(View view){
        // tiene que añadir una palabra a la base de datos sino esta repetida, usar columna llamada Diccionario
        SQLiteDatabase sqLiteDatabase = bbdd.getWritableDatabase();
        String palabraEsp = palabraEspanol.getText().toString();
        String palabraIng = palabraIngles.getText().toString();

        if(!palabraEsp.equalsIgnoreCase("") && !palabraIng.equalsIgnoreCase("")){

            if(buscarPalabra(palabraEsp)){
                System.out.println("La palabra " + palabraEsp + " ya esta en la base de datos");
            }else{
                ContentValues values = new ContentValues();
                values.put(Estructura_BBDD.NOMBRE_COLUMNA3,capitalizar(palabraEsp));
                values.put(Estructura_BBDD.NOMBRE_COLUMNA2,capitalizar(palabraIng));
                long newRowId = sqLiteDatabase.insert(Estructura_BBDD.TABLE_NAME2, null, values);
                if (newRowId > 0){
                    System.out.println("La palabra " + palabraEsp + " se ha insertado en la base de datos");
                }
            }
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
                Estructura_BBDD.NOMBRE_COLUMNA3
        };
        String selection = Estructura_BBDD.NOMBRE_COLUMNA3 + " = ?";
        String[] selectionArgs = { palabraEsp };

        Cursor c = sqLiteDatabase.query(
                Estructura_BBDD.TABLE_NAME2,
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

        /*Intent i = new Intent(this, MainActivity.class);
        i.putExtra("contador",contador);
        i.putExtra("listaEsp",listaEsp);
        i.putExtra("listaEng",listaEng);
        startActivity(i);*/
        finish();

    }

    private void reiniciarCuadros(){
        palabraEspanol.setText("");
        palabraIngles.setText("");
        palabraEspanol.requestFocus();

    }


}