package sources;

import dataBase.Jdbc;

public class Cour {
    private static Jdbc dataBase;

    private String nomCour, pathCour;
    private Formation form;

    public Cour(Formation form, String nomCour) {
        this.nomCour = nomCour;
        this.form = form;

        generatePath();
    }

    public Cour(Formation form, String nomCour, String path) {
        this.nomCour = nomCour;
        this.form = form;
        this.pathCour = path;
    }

    @Override
    public String toString() {
        return nomCour;
    }

    private void generatePath(){
        String backslash= System.getProperty("file.separator") ;
        pathCour ="Files" + backslash + "Cours" + backslash + "Form" + form.getNumFormation() + backslash +"Cr" + nomCour;

        String request = "update Cour set pathContenue = " + pathCour + " where numFormation = " + form.getNumFormation()
                + " and nomCour = " + nomCour;
        dataBase.updateRequest(request);
    }


    // Methodes Static
    public static void setDataBase(Jdbc db){
        dataBase = db;
    }
}
