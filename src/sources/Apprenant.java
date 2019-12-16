package sources;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Apprenant extends Personne {

    private String specialite, anneeEtude;

    public Apprenant(String userName, String password, String email, Langue lang, String nom, String prenom, int matricule,
                     LocalDate dateNaissance, String specialite, String anneeEtude) {
        super(userName, password, email, lang, nom, prenom, matricule, dateNaissance);
        this.specialite = specialite;
        this.anneeEtude = anneeEtude;
    }



    // Methodes Static
    public static Apprenant LoadApprenant(String userNameS) {
        if(UserNameExist(userNameS) || userType(userNameS).toLowerCase().equals("etudiant")) {
            String requestUser = "select * From Personne where userName = '" + userNameS + "'";
            ResultSet resUser = dataBase.selectRequest(requestUser);
            try{
                if (resUser.next()) {
                    String requestEtud = "select * From Etudiant where userName = '" + userNameS + "'";
                    ResultSet resEtud = dataBase.selectRequest(requestEtud);

                    if(resEtud.next()){
                        /*String s = resUser.getString("DateN");
                        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/YYYY");
                        System.out.println(df);
                        LocalDate date = LocalDate.parse(s, df);*/
                        LocalDate date = LocalDate.now();

                        Apprenant app = new Apprenant(userNameS, resUser.getString("password"),
                                resUser.getString("email"),Langue.valueOf(resUser.getString("langue"))
                                ,resUser.getString("nom"), resUser.getString("prenom"),
                                resEtud.getInt("matriculeEtud"),date,
                                resEtud.getString("specialite"), resEtud.getString("anneeCour"));

                        return app;
                    }
                }
            }catch (Exception e){
                System.out.println(e);
                return null;}
        }
        return null;
    }

    public static ArrayList<Apprenant> loadAllApprenant(){
        ArrayList<Apprenant> list = new ArrayList<>();

        String requestUser = "select * From Personne where type = 'etudiant'";
        ResultSet resUser = dataBase.selectRequest(requestUser);
        try
        {
            while (resUser.next()) {
                String userNameS = resUser.getString("userName");
                String requestEtud = "select * From Etudiant where userName = '" + userNameS + "'";
                ResultSet resEtud = dataBase.selectRequest(requestEtud);

                if(resEtud.next()){
                        /*String s = resUser.getString("DateN");
                        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/YYYY");
                        System.out.println(df);
                        LocalDate date = LocalDate.parse(s, df);*/
                    LocalDate date = LocalDate.now();

                    Apprenant app = new Apprenant(userNameS, resUser.getString("password"),
                            resUser.getString("email"),Langue.valueOf(resUser.getString("langue"))
                            ,resUser.getString("nom"), resUser.getString("prenom"),
                            resEtud.getInt("matriculeEtud"),date,
                            resEtud.getString("specialite"), resEtud.getString("anneeCour"));

                    list.add(app);
                }
            }
            return list;
        }catch (SQLException e){ return null;}
    }

    public static String getUserWithMat(int matricule) throws SQLException{
        String request = "select userName From Etudiant where matriculeEtud = " + matricule;
        ResultSet res = dataBase.selectRequest(request);
        if(res.next()){
            return res.getString("userName");
        }
        return null;
    }

    public static Apprenant SignUp(String userNameS, String passwordS, String emailS, String nomS, String prenomS,
                                   int matriculeS, LocalDate dateNaissanceS, String specialiteS,
                                   String anneeEtudeS){

        if(!UserNameExist(userNameS) && !emailExist(emailS)){
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

    private static boolean ajoutApprenant(String userNameS, int matriculeS, String specialiteS, String anneeEtudeS){

        String requete = "insert into Etudiant(matriculeEtud ,userName, specialite, anneeCour) values(" + matriculeS +
                ",'" + userNameS + "','" + specialiteS + "','" + anneeEtudeS + "')" ;
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
        String request = "select max(matriculeEtud) from Etudiant";
        ResultSet res = dataBase.selectRequest(request);
        try{
            if(res.next()){
                return res.getInt("max(matriculeEtud)")+1;
            }
            return 0;
        }catch (SQLException e){return -1;}
    }
}
