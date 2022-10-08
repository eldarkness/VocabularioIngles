package com.eldarkness.vocabularioingles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.eldarkness.vocabularioingles.BBDD.BBDD_Controller;
import com.eldarkness.vocabularioingles.BBDD.Estructura_BBDD;

import java.util.Locale;

public class ActivityAnadirPalabras extends AppCompatActivity {

    private BBDD_Controller bbdd;
    EditText palabraEspanol;
    EditText palabraIngles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_palabras);
        bbdd = new BBDD_Controller(this);
        palabraEspanol = (EditText) findViewById(R.id.editTextEsp);
        palabraIngles = (EditText) findViewById(R.id.editTextIngles);
        palabraEspanol.setHint("Palabra en Español");
        palabraIngles.setHint("Palabra en Inglés");
        palabraEspanol.requestFocus();

    }

    public void IntroducirPalabrasDiccionario(View view){
        // tiene que añadir una palabra a la base de datos sino esta repetida, usar columna llamada Diccionario
        SQLiteDatabase sqLiteDatabase = bbdd.getWritableDatabase();
        String palabraEsp = palabraEspanol.getText().toString();
        String palabraIng = palabraIngles.getText().toString();

        if(!palabraEsp.equalsIgnoreCase("") && !palabraIng.equalsIgnoreCase("")){
            System.out.println("Has escrito algo en cada linea");
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

        if (c.getCount()>0){
            return true;
        }else{
            return false;
        }

    }
    public void volverAtras(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void reiniciarCuadros(){
        palabraEspanol.setText("");
        palabraIngles.setText("");
        palabraEspanol.requestFocus();

    }


}