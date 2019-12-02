package dataBase;

import java.sql.*;

public class Jdbc {

    //connecting to the database
    private static Connection conn;
    private static String url = "jdbc:sqlite:src/dataBase/dbSocialLearning.db";

        //connecting to the database
    private static void openConnexion(){
        try {
                // create a connection to the database
           conn = DriverManager.getConnection(url);

           System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
          System.out.println(e.getMessage());
        }
    }

    private static void closeConnection(){
        try {
            conn.close(); // I'm note sure it's that, i'm working on atom so...
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ResultSet selectRequest(String request){
        try{
            openConnexion();
            Statement stmt = conn.createStatement();
            ResultSet resultat = stmt.executeQuery(request);
            closeConnection();
            return resultat;
        }catch (Exception e){
            System.out.println(e);
            closeConnection();
            return null;
        }
    }

    public int insertRequest(String request){
        try{
            openConnexion();
            Statement stmt = conn.createStatement();
            int nbMaj = stmt.executeUpdate(request);
            closeConnection();
            return nbMaj;
        }catch (SQLException e){
            closeConnection();
            return 0;
        }
    }

    public boolean keyExist(String table, String key){
        String request = "select * from " + table + " where " + key;
        openConnexion();
        ResultSet res = selectRequest(request);
        try{
            if(res.next()){
                closeConnection();
                return true;
            }
        }catch (Exception e){
            closeConnection();
            return false;
        }
        closeConnection();
        return false;
    }
}
