package sources;

import dataBase.Jdbc;

public class Cour {
    private static Jdbc dataBase;
    private static int cmpt = 0;



    public static void setDataBase(Jdbc db){
        dataBase = db;
    }
}
