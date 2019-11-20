package sources;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Instructeur extends Personne {
    private static int cmpt = 0;
    private String grade, dommaine;

    public Instructeur(String userName, String password, String email,Langue lang, String nom, String prenom,
                       String matricule, Date dateNaissance, String grade, String dommaine) {
        super(userName, password, email,lang, nom, prenom, matricule, dateNaissance);
        //matricule = "ENS" + cmpt;
        this.grade = grade;
        this.dommaine = dommaine;
    }

    @Override
    public void edit() {

    }

    public static Instructeur LoadInstructeur(String userNameS) throws SQLException {
        if(UserNameExist(userNameS) || userType(userNameS).toLowerCase().equals("enseignant")) {
            String requestUser = "select * From Personne where userName = '" + userNameS + "'";
            ResultSet resUser = dataBase.selectRequest(requestUser);

            if (resUser.next()) {
                String requestEns = "select * From Enseignant where userName = '" + userNameS + "'";
                ResultSet resEns = dataBase.selectRequest(requestEns);

                if(resEns.next()){
                    Instructeur inst = new Instructeur(userNameS, resUser.getString("password"),
                            resUser.getString("email"),Langue.valueOf(resUser.getString("langue"))
                            ,resUser.getString("nom"), resUser.getString("prenom"),
                            resUser.getString("matricule"), resUser.getDate("DateN"),
                            resEns.getString("grade"), resEns.getString("dommaine"));

                    return inst;
                }
            }
        }
        return null;
    }

    public static Instructeur SignUp(String userNameS, String passwordS, String emailS, String nomS, String prenomS,
                             String matriculeS, Date dateNaissanceS, String gradeS, String domaineS)throws SQLException{
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

    private static boolean ajoutInstructeur(String userNameS, String matriculeS, String gradeS, String dommaineS){

        String requete = "insert into Enseignant(matriculeEns ,userName, grade, dommaine) values('" + matriculeS + "','"
                + userNameS + "','" + gradeS + "'," + dommaineS + ")" ;
        int cp = 0;

        try{
            cp = dataBase.insertRequest(requete);
        }catch(Exception e){return false;}
        if (cp == 0){
            return false;
        }
        return true;
    }

}