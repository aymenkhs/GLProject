package sources;

import dataBase.Jdbc;

public class Sondage {
    private static Jdbc dataBase;
    private static int cmpt = 0;


    // Methodes Static
    public static void setDataBase(Jdbc db){
        dataBase = db;
    }
}
