package com.eldarkness.vocabularioingles;

public class PalabraEquivocada {
    private String palabraEsp;
    private String palabraEng;

    public PalabraEquivocada(String palabraEsp, String palabraEng){
        this.palabraEsp = palabraEsp;
        this.palabraEng = palabraEng;
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
}
