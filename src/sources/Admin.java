package sources;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends User {

    public Admin(String userName, String password, String email,Langue lang) {
        super(userName, password, email,lang);
    }



    // Methodes Static
    public static boolean thereIsAdmin(){
        String requestUser = "select * From Personne where type = 'Admin'";
        ResultSet res = dataBase.selectRequest(requestUser);
        try{
            if (res.next()) {return true;}
            return false;
        }catch (SQLException e){return true;}
    }

    public static boolean addAdmin(){
        String userName = "admin", password = "password", email = "";
        Langue langue = Langue.French;

        Admin admin = new Admin(userName, password, email, langue);
        String requete = "insert into Personne(userName, email, password, langue, type) values('"+ userName + "','"  +
                email + "','" + password + "','" + langue.name() + "','Admin')" ;
        int cp = 0;
        try{
            cp = dataBase.insertRequest(requete);
        }catch(Exception e){}
        if (cp == 0){
            return false;
        }
        return true;
    }

    public static Admin LoadAdmin(String userNameS){
        if(UserNameExist(userNameS) || userType(userNameS).toLowerCase().equals("Admin")){
            String requestUser = "select * From Personne where userName = '" + userNameS + "'";
            ResultSet res = dataBase.selectRequest(requestUser);
            try{
                if (res.next()) {
                    Admin admin = new Admin(userNameS, res.getString("password"),
                            res.getString("email"),Langue.valueOf(res.getString("langue")));
                    return admin;
                }
            }catch (SQLException e){return null;}
        }
        return null;
    }


}
