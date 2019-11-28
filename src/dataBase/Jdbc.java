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
            System.out.println("Resultat is " + resultat.toString());
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
}
