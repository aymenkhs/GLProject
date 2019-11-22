package sources;

import dataBase.Jdbc;

public class Cour {
    private static Jdbc dataBase;

    private String nomCour;

    // Methodes Static
    public static void setDataBase(Jdbc db){
        dataBase = db;
    }
}
