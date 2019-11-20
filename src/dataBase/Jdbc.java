package dataBase;

import java.sql.*;

public class Jdbc {
    Connection conn;

    public Jdbc() throws Exception {

        Class.forName("oracle.jdbc.driver.OracleDriver");

        String password = "password";
        String user = "c##socialLearning";
        String url = "jdbc:oracle:thin:@//127.0.0.1:1521/orcl";

        conn = DriverManager.getConnection(url,user, password);
    }

    public ResultSet selectRequest(String request){
        try{
            Statement stmt = conn.createStatement();
            ResultSet resultat = stmt.executeQuery(request);
            return resultat;
        }catch (Exception e){
            //System.out.println(e);
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
