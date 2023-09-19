package com.eldarkness.vocabularioingles.dto;

public class PalabraDiccionario {
    private String palabraEsp;
    private String palabraEng;
    private String categoria;

    public PalabraDiccionario(String palabraEsp, String palabraEng){
        this.palabraEsp = palabraEsp;
        this.palabraEng = palabraEng;
    }

    public PalabraDiccionario(String palabraEsp, String palabraEng, String categoria){
        this.palabraEsp = palabraEsp;
        this.palabraEng = palabraEng;
        this.categoria = categoria;
    }

    public String getPalabraEsp() {
        return palabraEsp;
    }

    public void setPalabraEsp(String palabraEsp) {
        this.palabraEsp = palabraEsp;
    }

    public String getPalabraEng() {
        return palabraEng;
    }

    public void setPalabraEng(String palabraEng) {
        this.palabraEng = palabraEng;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
