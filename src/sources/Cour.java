package sources;

import dataBase.Jdbc;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cour {
    private static Jdbc dataBase;

    private String nomCour, pathCour;
    private Formation form;
    private File fcour;

    Cour(Formation form, String nomCour, String contenuCour) {
        this.nomCour = nomCour;
        this.form = form;
        generatePath();
        this.sauvgCour(contenuCour);
    }

    Cour(String path, Formation form, String nomCour) {
        this.nomCour = nomCour;
        this.form = form;
        this.pathCour = path;
        createFile();
    }

    @Override
    public String toString() {
        return nomCour;
    }

    private void generatePath(){
        String backslash= System.getProperty("file.separator");
        pathCour = "Files" + backslash + "Cours" + backslash + "Form" + form.getNumFormation() + backslash + "Cr" + nomCour;
        createFile();

        String request = "update Cour set pathContenue = '" + pathCour + "' where numFormation = " + form.getNumFormation()
                + " and nomCour = '" + nomCour + "'";
        dataBase.updateRequest(request);
    }

    private void createFile(){
        fcour = new File(pathCour);
        if(!fcour.exists()){
            try{
                fcour.createNewFile();
            }catch (IOException e){}

        }
    }


    public void sauvgCour(String cour){
        ObjectOutputStream writeStream = null;
        try {
            final FileOutputStream writeFile = new FileOutputStream(fcour);
            writeStream = new ObjectOutputStream(writeFile);
            writeStream.writeObject(cour);
            writeStream.flush();
        }catch(final java.io.IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(writeStream != null) {
                    writeStream.flush();
                    writeStream.close();
                }
            }catch(final IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String chargerCour(){
        ObjectInputStream readStream = null;
        try {
            final FileInputStream readFile = new FileInputStream(fcour);
            readStream = new ObjectInputStream(readFile);
            return (String) readStream.readObject();
        }catch(final Exception e){
            return "";
        }
    }

    protected boolean delete(){
        String requete = "delete from Cour where numFormation=" + form.getNumFormation() + " and nomCour='" + nomCour + "'";
        return dataBase.deleteRequest(requete)!=0;
    }

    public static boolean delete(int numFormation, String nomCour) {
        String requete = "delete from Cour where numFormation=" + numFormation + " and nomCour='" + nomCour + "'";
        return dataBase.deleteRequest(requete)!=0;
    }

    public String getNomCour() {
        return nomCour;
    }

    // Methodes Static
    public static void setDataBase(Jdbc db){
        dataBase = db;
    }

    public static Cour getCours(Formation form, String nomCour) {
        String request = "Select * From Cour Where numFormation = " +form.getNumFormation()+ " AND nomCour = '" +nomCour+ "'";
        ResultSet res = dataBase.selectRequest(request);
        try {
            if(res.next()) {
                return new Cour(res.getString("pathContenue"), form, res.getString("nomCour"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}