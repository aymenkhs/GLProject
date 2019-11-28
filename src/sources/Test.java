package sources;

import dataBase.Jdbc;

public class Test {
    private static Jdbc dataBase;

    private Formation form;
    private int numTest;

    Test(Formation form, int numTest) {
        this.form = form;
        this.numTest = numTest;
    }

    @Override
    public String toString() {
        return "Test numero : " + numTest;
    }

    // Methodes Static
    public static void setDataBase(Jdbc db){
        dataBase = db;
    }


}
