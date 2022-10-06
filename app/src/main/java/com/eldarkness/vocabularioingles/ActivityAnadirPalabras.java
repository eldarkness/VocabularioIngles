package com.eldarkness.vocabularioingles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.eldarkness.vocabularioingles.BBDD.BBDD_Controller;

public class ActivityAnadirPalabras extends AppCompatActivity {

    private BBDD_Controller bbdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_palabras);
        bbdd = new BBDD_Controller(this);
    }
}