package com.eldarkness.vocabularioingles;

import java.util.ArrayList;

public class ControladorPalabras {
    private ArrayList<String> listaEsp = new ArrayList<>();
    private ArrayList<String> listaEng = new ArrayList<>();
    private ArrayList<PalabraEquivocada> palabrasEquivocadas = new ArrayList();
    int contador = 0;

    public ControladorPalabras(){
        // en un futuro meteremos aqui las palabras que nos lleguen del bundle que hayamos guardado de la sesion anterior
    }

    public void anadirPalabras(String palabraEsp, String palabraEng){
        if(!comprobarPalabra(palabraEsp)){
            palabrasEquivocadas.add(new PalabraEquivocada(palabraEsp,palabraEng));
        }else{
            // si es true significa que la palabra ya esta en el diccionario
        }


    }

    public Boolean comprobarPalabra(String palabraEsp){
        for (int i = 0; i<palabrasEquivocadas.size() ; i++){
            if(palabraEsp.equalsIgnoreCase(palabrasEquivocadas.get(i).getPalabraEsp())){
                return true;
            }
        }
        return false;
    }

    public void generarContador(){
        contador = (int) (Math.random() * (30 + 1 - 20)) + 30;
        System.out.println((Math.random() * (30 + 1 - 20)) + 30);
    }


}
