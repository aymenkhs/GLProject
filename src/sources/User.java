package sources;

import dataBase.Jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class User {
    protected String userName, password, email;
    protected Langue lang;
    protected static Jdbc dataBase;

    public User(String userName, String password, String email,Langue lang) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.lang = lang;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Langue getLang() {
        return lang;
    }

    public void setLang(Langue lang) {
        this.lang = lang;
    }

    public boolean login(String userN, String passw){
        if(userName.equals(userN) && password.equals(passw)){
            return true;
        }
        return false;
    }

    public static void setDataBase(Jdbc db){
        dataBase = db;
    }

    public void changerLangue(Langue l){
        this.lang = l;
    }

    public abstract void edit();

    protected static boolean UserNameExist(String userNameS) throws SQLException {
        String request = "select userName From Personne where userName = '" + userNameS +"'";

        ResultSet res = dataBase.selectRequest(request);

        if(res.next()){
            return true;
        }
        return false;
    }

    protected static String userType(String userNameS)throws SQLException{
        String request = "select type From Personne where userName = '" + userNameS +"'";

        ResultSet res = dataBase.selectRequest(request);

        if(res.next()){
            return res.getString("type");
        }
        return "";
    }

}
