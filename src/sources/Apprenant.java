package sources;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Apprenant extends Personne {
    private static int cmpt = 0;

    private String specialite;
    private int anneeEtude;

    public Apprenant(String userName, String password, String email,Langue lang, String nom, String prenom, String matricule,
                     Date dateNaissance, String specialite, int anneeEtude) {
        super(userName, password, email, lang, nom, prenom, matricule, dateNaissance);
        this.specialite = specialite;
        this.anneeEtude = anneeEtude;
    }

    @Override
    public void edit() {}

    public static Apprenant LoadApprenant(String userNameS) throws SQLException {
        if(UserNameExist(userNameS) || userType(userNameS).toLowerCase().equals("etudiant")) {
            String requestUser = "select * From Personne where userName = '" + userNameS + "'";
            ResultSet resUser = dataBase.selectRequest(requestUser);

            if (resUser.next()) {
                String requestEtud = "select * From Etudiant where userName = '" + userNameS + "'";
                ResultSet resEtud = dataBase.selectRequest(requestEtud);

                if(resEtud.next()){
                    Apprenant app = new Apprenant(userNameS, resUser.getString("password"),
                            resUser.getString("email"),Langue.valueOf(resUser.getString("langue"))
                            ,resUser.getString("nom"), resUser.getString("prenom"),
                            resUser.getString("matricule"), resUser.getDate("DateN"),
                            resEtud.getString("specialite"), resEtud.getInt("anneeCour"));

                    return app;
                }
            }
        }
        return null;
    }

    public static Apprenant SignUp(String userNameS, String passwordS, String emailS, String nomS, String prenomS,
                                   String matriculeS, Date dateNaissanceS, String specialiteS,
                                   int anneeEtudeS) throws SQLException{

        if(!UserNameExist(userNameS)){
            if(ajoutPersonne(userNameS,passwordS,emailS,nomS,prenomS,dateNaissanceS,"etudiant", Langue.French)){
                if(ajoutApprenant(userNameS, matriculeS, specialiteS, anneeEtudeS)){
                    Apprenant app = new Apprenant(userNameS, passwordS, emailS, Langue.French ,nomS, prenomS,
                            matriculeS, dateNaissanceS, specialiteS, anneeEtudeS);
                    return app;
                }
            }
        }
        return null;
    }

    private static boolean ajoutApprenant(String userNameS, String matriculeS, String specialiteS, int anneeEtudeS){

        String requete = "insert into Etudiant(matriculeEtud ,userName, specialite, anneeCour) values('" + matriculeS +
                "','" + userNameS + "','" + specialiteS + "'," + anneeEtudeS + ")" ;
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
