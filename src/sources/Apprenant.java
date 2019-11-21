package sources;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Apprenant extends Personne {
    private static int cmpt = 0;

    private String specialite, anneeEtude;

    public Apprenant(String userName, String password, String email, Langue lang, String nom, String prenom, String matricule,
                     LocalDate dateNaissance, String specialite, String anneeEtude) {
        super(userName, password, email, lang, nom, prenom, matricule, dateNaissance);
        this.specialite = specialite;
        this.anneeEtude = anneeEtude;
    }

    @Override
    public void edit() {}

    public static Apprenant LoadApprenant(String userNameS) {
        if(UserNameExist(userNameS) || userType(userNameS).toLowerCase().equals("etudiant")) {
            String requestUser = "select * From Personne where userName = '" + userNameS + "'";
            ResultSet resUser = dataBase.selectRequest(requestUser);
            try{
                if (resUser.next()) {
                    String requestEtud = "select * From Etudiant where userName = '" + userNameS + "'";
                    ResultSet resEtud = dataBase.selectRequest(requestEtud);

                    if(resEtud.next()){
                        Apprenant app = new Apprenant(userNameS, resUser.getString("password"),
                                resUser.getString("email"),Langue.valueOf(resUser.getString("langue"))
                                ,resUser.getString("nom"), resUser.getString("prenom"),
                                resUser.getString("matricule"), resUser.getDate("DateN").toLocalDate(),
                                resEtud.getString("specialite"), resEtud.getString("anneeCour"));

                        return app;
                    }
                }
            }catch (SQLException e){return null;}
        }
        return null;
    }

    public static String genererMatricule(){ return "Etud" + cmpt + "#"; }

    public static Apprenant SignUp(String userNameS, String passwordS, String emailS, String nomS, String prenomS,
                                   String matriculeS, LocalDate dateNaissanceS, String specialiteS,
                                   String anneeEtudeS){

        if(!UserNameExist(userNameS) && !emailExist(emailS)){
            if(ajoutPersonne(userNameS,passwordS,emailS,nomS,prenomS,dateNaissanceS,"etudiant", Langue.French)){
                if(ajoutApprenant(userNameS, matriculeS, specialiteS, anneeEtudeS)){
                    cmpt++;
                    Apprenant app = new Apprenant(userNameS, passwordS, emailS, Langue.French ,nomS, prenomS,
                            matriculeS, dateNaissanceS, specialiteS, anneeEtudeS);
                    return app;
                }
            }
        }
        return null;
    }

    private static boolean ajoutApprenant(String userNameS, String matriculeS, String specialiteS, String anneeEtudeS){

        String requete = "insert into Etudiant(matriculeEtud ,userName, specialite, anneeCour) values('" + matriculeS +
                "','" + userNameS + "','" + specialiteS + "','" + anneeEtudeS + "')" ;
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
