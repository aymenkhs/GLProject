package sources;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Instructeur extends Personne {

    private String grade, dommaine;

    public Instructeur(String userName, String password, String email,Langue lang, String nom, String prenom,
                       int matricule, LocalDate dateNaissance, String grade, String dommaine) {
        super(userName, password, email,lang, nom, prenom, matricule, dateNaissance);
        //matricule = "ENS" + cmpt;
        this.grade = grade;
        this.dommaine = dommaine;
    }


    // Formtions

    public Formation creeFormation(int numFormation,String nomFormation, String description){
        return Formation.createFormation(numFormation, nomFormation, this, description);
    }




    // Methodes Static
    public static Instructeur LoadInstructeur(String userNameS){
        if(UserNameExist(userNameS) || userType(userNameS).toLowerCase().equals("enseignant")) {
            String requestUser = "select * From Personne where userName = '" + userNameS + "'";
            ResultSet resUser = dataBase.selectRequest(requestUser);

            try{
                if (resUser.next()) {
                    String requestEns = "select * From Enseignant where userName = '" + userNameS + "'";
                    ResultSet resEns = dataBase.selectRequest(requestEns);

                    //momentaner
                    LocalDate date = LocalDate.now();

                    if(resEns.next()){
                        Instructeur inst = new Instructeur(userNameS, resUser.getString("password"),
                                resUser.getString("email"),Langue.valueOf(resUser.getString("langue"))
                                ,resUser.getString("nom"), resUser.getString("prenom"),
                                resEns.getInt("matriculeEns"), date,
                                resEns.getString("grade"), resEns.getString("dommaine"));

                        return inst;
                    }
                }
            }catch (SQLException e){
                System.out.println(e);
                return null;}
        }
        return null;
    }

    public static String getUserWithMat(int matricule){
        String request = "select userName From Enseignant where matriculeEns = " + matricule;
        ResultSet res = dataBase.selectRequest(request);
        try{
            if(res.next()){
                return res.getString("userName");
            }
        }catch (SQLException e){}
        return null;
    }

    public static Instructeur SignUp(String userNameS, String passwordS, String emailS, String nomS, String prenomS,
                                     int matriculeS, LocalDate dateNaissanceS, String gradeS, String domaineS){
        if(!UserNameExist(userNameS)){
            if(ajoutPersonne(userNameS,passwordS,emailS,nomS,prenomS,dateNaissanceS,"enseignant", Langue.French)){
                if(ajoutInstructeur(userNameS, matriculeS, gradeS, domaineS)){
                    Instructeur inst = new Instructeur(userNameS, passwordS, emailS, Langue.French ,nomS, prenomS, matriculeS,
                            dateNaissanceS, gradeS, domaineS);
                    return inst;
                }
            }
        }
        return null;
    }

    private static boolean ajoutInstructeur(String userNameS, int matriculeS, String gradeS, String dommaineS){

        String requete = "insert into Enseignant(matriculeEns ,userName, grade, dommaine) values(" + matriculeS + ",'"
                + userNameS + "','" + gradeS + "','" + dommaineS + "')" ;
        int cp = 0;

        try{
            cp = dataBase.insertRequest(requete);
        }catch(Exception e){return false;}
        if (cp == 0){
            return false;
        }
        return true;
    }

    public static int nbPersonne(){
        String request = "select max(matriculeEns) from Enseignant";
        ResultSet res = dataBase.selectRequest(request);
        try{
            if(res.next()){
                return res.getInt("max(matriculeEns)")+1;
            }
            return 0;
        }catch (SQLException e){return -1;}
    }


}