package com.eldarkness.vocabularioingles;

import java.util.ArrayList;

public class ControladorPalabras {

    private ArrayList<PalabraEquivocada> palabrasEquivocadas = new ArrayList();
    int contador = 0;

    public ControladorPalabras(){
        // en un futuro meteremos aqui las palabras que nos lleguen del bundle que hayamos guardado de la sesion anterior
    }

    public ArrayList<PalabraEquivocada> getPalabrasEquivocadas() {
        return palabrasEquivocadas;
    }

    public void anadirPalabras(String palabraEsp, String palabraEng){
        if(!comprobarPalabra(palabraEsp)){
            palabrasEquivocadas.add(new PalabraEquivocada(palabraEsp,palabraEng));
        }else{
            // si es true significa que la palabra ya esta en la lista de palabras equivocadas
            // se podria poner aqui una pista de que palabra para que pueda ser adivinada mas facilmenta
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

        contador = numeroAleatorio(5, 8);

    }

    private int numeroAleatorio(int nMinimo, int nMaximo){
        return (int) (Math.random() * (nMaximo - nMinimo + 1) + nMinimo);
    }

    public void mostrarLista(){
        for (int i = 0; i<palabrasEquivocadas.size();i++){
            System.out.println(i +" - Palabra de la lista: " +palabrasEquivocadas.get(i).getPalabraEsp());
        }

    }


}
