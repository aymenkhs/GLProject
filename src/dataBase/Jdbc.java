package dataBase;

import java.sql.*;

public class Jdbc {
    Connection conn;

    //connecting to the database
    public Jdbc() {

        try {
            // db parameters
            String url = "jdbc:sqlite:src/dataBase/dbSocialLearning.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public ResultSet selectRequest(String request){
        try{
            Statement stmt = conn.createStatement();
            ResultSet resultat = stmt.executeQuery(request);
            return resultat;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public int insertRequest(String request){
        try{
            Statement stmt = conn.createStatement();
            int nbMaj = stmt.executeUpdate(request);
            return nbMaj;
        }catch (SQLException e){
            return 0;
        }
    }

    public boolean keyExist(String table, String key){
        String request = "select * from " + table + " where " + key;

        ResultSet res = selectRequest(request);
        try{
            if(res.next()){
                return true;
            }
        }catch (Exception e){ return false; }
        return false;
    }

    public int updateRequest(String request){
        try{
            Statement stmt = conn.createStatement();
            int nbMaj = stmt.executeUpdate(request);
            return nbMaj;
        }catch (SQLException e){
            return 0;
        }
    }

    public int deleteRequest(String request){
        try{
            Statement stmt = conn.createStatement();
            int nbMaj = stmt.executeUpdate(request);
            return nbMaj;
        }catch (SQLException e){
            return 0;
        }
    }
}
