package sources;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Personne extends User {

    protected String nom, prenom, matricule;
    protected LocalDate dateNaissance;

    public Personne(String userName, String password, String email,Langue lang, String nom, String prenom,
                    String matricule, LocalDate dateNaissance) {
        super(userName, password, email,lang);
        this.nom = nom;
        this.prenom = prenom;
        this.matricule = matricule;
        this.dateNaissance = dateNaissance;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMatricule() {
        return matricule;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    protected static boolean ajoutPersonne(String userNameS, String passwordS, String emailS, String nomS, String prenomS,
                                           LocalDate dateNaissanceS, String type, Langue langue){

        String requete = "insert into Personne(userName, nom, prenom, DateN, email, password, langue, type) values('" +
                userNameS + "','" + nomS + "','" + prenomS+ "','" + dateNaissanceS.format(DateTimeFormatter.ofPattern("d/MM/uuuu"))
                + "','" + emailS + "','" + passwordS+ "','" + langue.name() + "','" + type +"')" ;
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
