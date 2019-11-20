package sources;

import java.util.Date;

public abstract class Personne extends User {

    protected String nom, prenom, matricule;
    protected Date dateNaissance;

    public Personne(String userName, String password, String email,Langue lang, String nom, String prenom,
                    String matricule, Date dateNaissance) {
        super(userName, password, email,lang);
        this.nom = nom;
        this.prenom = prenom;
        this.matricule = matricule;
        this.dateNaissance = dateNaissance;
    }

    protected static boolean ajoutPersonne(String userNameS, String passwordS, String emailS, String nomS, String prenomS,
                                            Date dateNaissanceS, String type, Langue langue){

        String requete = "insert into Personne(userName, nom, prenom, DateN, email, password, langue, type) values('" +
                userNameS + "','" + nomS + "'," + prenomS+ ",'" + dateNaissanceS + "','" + emailS + "','" + passwordS
                + langue.name() + "','" + type +")" ;
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
