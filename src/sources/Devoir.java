package sources;

import dataBase.Jdbc;

public class Devoir {
    private static Jdbc dataBase;

    private Formation form;
    private int numDevoir;
    private String enoncer;

    Devoir(Formation form, int numDevoir, String enoncer) {
        this.form = form;
        this.numDevoir = numDevoir;
        this.enoncer = enoncer;
    }

    @Override
    public String toString() {
        return "Devoir numero : " + numDevoir;
    }

    // Methodes Static
    public static void setDataBase(Jdbc db){
        dataBase = db;
    }

}
