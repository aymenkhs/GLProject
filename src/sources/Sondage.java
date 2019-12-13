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

    public String getNomCreateur() {
        String request = "SELECT * FROM Personne WHERE userName = '" + getUserNameCreateur() +"'";
        ResultSet res = dataBase.selectRequest(request);
        if(res != null) {
            try {
                return res.getString("nom") +" "+ res.getString("prenom");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
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

    public static int nbSondage(){
        String request = "select max(numSondage) from Sondage";
        ResultSet res = dataBase.selectRequest(request);
        try{
            if(res.next()){
                return res.getInt("max(numSondage)")+1;
            }
            return 0;
        }catch (SQLException e){return -1;}
    }

    public static Sondage createSondage(int numSondage,String userName, String description, String typeParticiapant){
        if(!dataBase.keyExist("Sondage", "numSondage = " + numSondage)){
            if(addSondage(numSondage, userName, description, typeParticiapant)){
                return new Sondage(numSondage, userName, description, typeParticiapant);
            }
        }
        return null;
    }

    private static boolean addSondage(int numSondage,String userName, String description, String typeParticiapant){
        String requete = "insert into Sondage(numSondage ,userName, description, typeParticipant) values(" +
                numSondage + ",'" + userName + "','" + description + "','" + typeParticiapant + "')" ;
        if (dataBase.insertRequest(requete) == 0){
            return false;
        }
        return true;
    }

    public boolean addChoix(int numChoix, String nomChoix) {
        String request = "Insert into Choix(numChoix, numSondage, nomChoix) values(" +
                numChoix +",'" + this.numSondage + "','" + nomChoix + "')" ;
        if(dataBase.insertRequest(request) == 0) {
            return false;
        }
        return true;
    }

    public ArrayList<String> loadChoix() {
        String request = "Select * from Choix where numSondage = " + this.numSondage +"";
        ResultSet res = dataBase.selectRequest(request);
        ArrayList<String> choices = new ArrayList<>();

        while(true) {
            try {
                if (!res.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                choices.add(res.getString("nomChoix"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return choices;
    }

    public boolean checkIfAnswered(String userName) {
        String request = "Select userName from Participer where numSondage = " + this.numSondage + "";
        ResultSet res = dataBase.selectRequest(request);
        try {
            if(res.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAnswer(String userName) {
        String request = "Delete from Participer where numSondage = " + this.numSondage + " AND " +
                "userName = '" + userName + "'";
        if(dataBase.deleteRequest(request) == 0) {
            return false;
        }
        return true;
    }

    public void answer(String userName, String choix) {
        String selectRequest = "Select numChoix from Choix where numSondage = " + this.numSondage + " AND " +
                " nomChoix + '" + choix + "'";
        ResultSet res = dataBase.selectRequest(selectRequest);
        int numChoix = 1;
        try {
            if(res.next()) {
                numChoix = res.getInt("numChoix");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String insertRequest = "Insert into Participer Values (" +
                this.numSondage + ",'" + userName + "'," + numChoix + ")";
        System.out.println("The sondage has been answered:" + dataBase.insertRequest(insertRequest));
    }

    public boolean delete() {
        String request = "Delete from Sondage where numSondage = " + this.numSondage + "";
        if(dataBase.deleteRequest(request) == 0) {
            return false;
        }
        return true;
    }

}
