package sources;

import dataBase.Jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class User {
    protected static Jdbc dataBase;

    protected String userName, password, email;
    protected Langue lang;

    public User(String userName, String password, String email,Langue lang) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.lang = lang;
    }

    // geters && seters dont on pourraits avoir besoin (on supprimmera ce qui ne sont pas utilisera la fin du projet)
    public String getUserName() { return userName;}

    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public Langue getLang() { return lang; }

    public void changerLangue(Langue l){
        this.lang = l;
    }

    // methodes

    public ArrayList<Sondage> listSondagesCree(){
        return Sondage.loadSondagesPerCreateur(userName);
    }


    // methodes static
    public static void setDataBase(Jdbc db){
        dataBase = db;
    }

    public static boolean login(String userN, String passw){
        if(UserNameExist(userN)){
            return passwordMatch(userN,passw);
        }
        return false;
    }

    private static boolean passwordMatch(String userN, String passw){
        String request = "select password From Personne where userName = '" + userN +"'";

        ResultSet res = dataBase.selectRequest(request);
        try{
            if(res.next()){
                if (passw.equals(res.getString("password"))){
                    return true;
                }
            }
        }catch (SQLException e){ return false;}
        return false;
    }

    protected static boolean UserNameExist(String userNameS){
        String request = "select userName From Personne where userName = '" + userNameS +"'";

        ResultSet res = dataBase.selectRequest(request);
        try{
            if(res.next()){
                return true;
            }
        }catch (Exception e){ return false; }
        return false;
    }

    protected static boolean emailExist(String email){
        String request = "select userName From Personne where email = '" + email +"'";

        ResultSet res = dataBase.selectRequest(request);
        try{
            if(res.next()){
                return true;
            }
        }catch (SQLException e){ return false;}
        return false;
    }

    public static String userType(String userNameS){
        String request = "select type From Personne where userName = '" + userNameS +"'";

        ResultSet res = dataBase.selectRequest(request);
        try{
            if(res.next()){
                return res.getString("type");
            }
        }catch(SQLException e){ return "";}
        return "";
    }

    public static ArrayList<User> loadAllUsers(){
        ArrayList<User> list = new ArrayList<>();

        String requestUser = "select * From Personne";
        ResultSet resUser = dataBase.selectRequest(requestUser);
        try
        {
            while (resUser.next()) {

                String type = resUser.getString("type");
                String userNameS = resUser.getString("userName");
                if(type.equals("etudiant")){
                    String requestEtud = "select * From Etudiant where userName = '" + userNameS + "'";
                    ResultSet resEtud = dataBase.selectRequest(requestEtud);

                    if(resEtud.next()){

                        Apprenant app = new Apprenant(userNameS, resUser.getString("password"),
                                resUser.getString("email"),Langue.valueOf(resUser.getString("langue"))
                                ,resUser.getString("nom"), resUser.getString("prenom"),
                                resEtud.getInt("matriculeEtud"),resUser.getString("dateN"),
                                resEtud.getString("specialite"), resEtud.getString("anneeCour"));

                        list.add(app);
                    }
                }else if(type.equals("enseignant")){
                    String requestEns = "select * From Enseignant where userName = '" + userNameS + "'";
                    ResultSet resEns = dataBase.selectRequest(requestEns);

                    if(resEns.next()){
                        Instructeur inst = new Instructeur(userNameS, resUser.getString("password"),
                                resUser.getString("email"),Langue.valueOf(resUser.getString("langue"))
                                ,resUser.getString("nom"), resUser.getString("prenom"),
                                resEns.getInt("matriculeEns"), resUser.getString("dateN"),
                                resEns.getString("grade"), resEns.getString("dommaine"));

                        list.add(inst);
                    }
                }else{ //admin
                    Admin admin = new Admin(userNameS, resUser.getString("password"),
                            resUser.getString("email"),Langue.valueOf(resUser.getString("langue")));
                    list.add(admin);
                }
            }
            return list;
        }catch (SQLException e){ return null;}
    }
}
