package sources;

import dataBase.Jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Sondage {
    private static Jdbc dataBase;

    private int numSondage;
    private String userNameCreateur, description, typeParticiapant;

    public Sondage(int numSondage, String userNameCreateur, String description, String typeParticiapant) {
        this.numSondage = numSondage;
        this.userNameCreateur = userNameCreateur;
        this.description = description;
        this.typeParticiapant = typeParticiapant;
    }

    public int getNumSondage() {
        return numSondage;
    }

    public String getUserNameCreateur() {
        return userNameCreateur;
    }

    public String getDescription() {
        return description;
    }

    public String getTypeParticiapant() {
        return typeParticiapant;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTypeParticiapant(String typeParticiapant) {
        this.typeParticiapant = typeParticiapant;
    }


    // Methodes Static
    public static void setDataBase(Jdbc db){
        dataBase = db;
    }

    public static ArrayList<Sondage> loadSondagesPerCreateur(String userName){
        ArrayList<Sondage> list = new ArrayList<>();

        String request = "SELECT * FROM Sondage where userName = '"+userName+"'";
        ResultSet res = dataBase.selectRequest(request);

        try{
            while (res.next()) {
                Sondage sond = new Sondage(res.getInt("numSondage"), res.getString("userName"),
                        res.getString("description"), res.getString("typeParticipant"));
                list.add(sond);
            }
        }catch (SQLException e){}
        return list;
    }

    public static ArrayList<Sondage> loadSondagesParticiper(String userName){
        ArrayList<Sondage> list = new ArrayList<>();

        String request = "SELECT * FROM Sondage where numSondage IN (SELECT numSondage FROM Participer where userName = '"
                + userName + "')";
        ResultSet res = dataBase.selectRequest(request);

        try{
            while (res.next()) {
                Sondage sond = new Sondage(res.getInt("numSondage"), res.getString("userName"),
                        res.getString("description"), res.getString("typeParticipant"));
                list.add(sond);
            }
        }catch (SQLException e){}
        return list;
    }

    public static ArrayList<Sondage> loadAllSondages(){
        ArrayList<Sondage> list = new ArrayList<>();

        String request = "SELECT * FROM Sondage";
        ResultSet res = dataBase.selectRequest(request);

        try{
            while (res.next()) {
                Sondage sond = new Sondage(res.getInt("numSondage"), res.getString("userName"),
                        res.getString("description"), res.getString("typeParticipant"));
                list.add(sond);
            }
        }catch (SQLException e){}
        return list;
    }
}
