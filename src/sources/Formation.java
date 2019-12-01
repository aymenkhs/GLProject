package sources;

import dataBase.Jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Formation {
    private static Jdbc dataBase;

    private int numFormation;
    private String nomFormation, description;
    private String instName;
    private Instructeur inst;
    private ArrayList<Cour> listCours;

    Formation(int numFormation, String nomFormation, String description, Instructeur inst) {
        this.numFormation = numFormation;
        this.nomFormation = nomFormation;
        this.description = description;
        this.inst = inst;
        this.instName = (inst.getPrenom() + "\t" + inst.getNom());
    }

    public int getNumFormation() {
        return numFormation;
    }

    public String getNomFormation() {
        return nomFormation;
    }

    public String getInstName() {
        return instName;
    }

    // Apprenant
    public boolean addApprenant(Apprenant app){
        if(!dataBase.keyExist("MembreFormation","numFormation = " + numFormation + " and matriculeEtud = " +
                app.getMatricule())){
            String requete = "insert into MembreFormation(numFormation , matriculeEtud) values(" + numFormation + "," +
                    app.getMatricule() + ")";
            if (dataBase.insertRequest(requete) != 0){
                return true;
            }
        }
        return false;
    }


    // Cour
    public ArrayList<Cour> LoadCours(){
        ArrayList<Cour> list = new ArrayList<>();
        String request = "select * from Cour where numFormation = '"+ numFormation +"'";
        ResultSet res = dataBase.selectRequest(request);
        try{
            while(res.next()){
                Cour cour = new Cour(res.getString("pathContenue"), this, res.getString("nomCour"));
                list.add(cour);
            }
        }catch (SQLException e){}
        return list;
    }

    public Cour addCour(String nomCour, String contenuCour){
        if(!dataBase.keyExist("Cour","numFormation = " + numFormation + " and nomCour = " + nomCour)){
            String requete = "insert into Cour(numFormation , nomCour) values(" + numFormation + ",'" + nomCour + "')";
            if (dataBase.insertRequest(requete) != 0){
                return new Cour(this, nomCour, contenuCour);
            }
        }
        return null;
    }


    // Tests
    public ArrayList<Test> LoadTests(){
        ArrayList<Test> list = new ArrayList<>();
        String request = "select * from Test where numFormation = '"+ numFormation +"'";
        ResultSet res = dataBase.selectRequest(request);
        try{
            while(res.next()){
                Test test = new Test(this, res.getInt("numTest"));
                list.add(test);
            }
        }catch (SQLException e){}
        return list;
    }

    public Test addTest(int numTest){
        String requete = "insert into Test(numFormation ,numTest) values(" + numFormation + "," + numTest + ")";
        if (dataBase.insertRequest(requete) != 0){
            return new Test( this, numTest);
        }
        return null;
    }

    public int nbTestsCree(){
        String request = "select max(numTest) from Test where numFormation = " + numFormation;
        ResultSet res = dataBase.selectRequest(request);
        try{
            if(res.next()){
                return res.getInt("max(numTest)")+1;
            }
            return 0;
        }catch (SQLException e){return -1;}
    }


    //Devoirs
    public ArrayList<Devoir> LoadDevoirs(){
        ArrayList<Devoir> list = new ArrayList<>();
        String request = "select * from Devoir where numFormation = '"+ numFormation +"'";
        ResultSet res = dataBase.selectRequest(request);
        try{
            while(res.next()){
                Devoir dev = new Devoir(this, res.getInt("numDevoir"),
                        res.getString("enoncer"));
                list.add(dev);
            }
        }catch (SQLException e){}
        return list;
    }

    public Devoir addDevoir(int numDevoir, String enoncer){
        String requete = "insert into Devoir(numFormation ,numDevoir, enoncer) values(" + numFormation + "," + numDevoir
                + ",'" + enoncer +"')";
        if (dataBase.insertRequest(requete) != 0){
            return new Devoir( this, numDevoir, enoncer);
        }
        return null;
    }

    public int nbDevoirCree(){
        String request = "select max(numDevoir) from Devoir where numFormation = " + numFormation;
        ResultSet res = dataBase.selectRequest(request);
        try{
            if(res.next()){
                return res.getInt("max(numDevoir)")+1;
            }
            return 0;
        }catch (SQLException e){return -1;}
    }





    // Methodes Static
    public static void setDataBase(Jdbc db){
        dataBase = db;
    }

    public static Formation loadFormation(int numFormation) throws SQLException {
        String request = "select * from Formation where numFormation = '"+ numFormation +"'";
        ResultSet res = dataBase.selectRequest(request);
        if(res.next()){
            Formation form = new Formation(numFormation, res.getString("nomFormation"),
                    res.getString("description"),
                    Instructeur.LoadInstructeur(Instructeur.getUserWithMat(res.getInt("matriculeEns"))));
            return form;
        }
        return null;
    }

    public static ArrayList<Formation> loadFormation(Instructeur inst)throws SQLException{
        ArrayList<Formation> list = new ArrayList<>();
        String request = "select * from Formation where matriculeEns = '"+ inst.getMatricule() +"'";
        ResultSet res = dataBase.selectRequest(request);
        while(res.next()){
            Formation form = new Formation(res.getInt("numFormation"), res.getString("nomFormation"),
                    res.getString("description"), inst);
            list.add(form);
        }
        return list;
    }

    public static ArrayList<Formation> loadFormationAppr(Apprenant appr)throws SQLException{
        ArrayList<Formation> list = new ArrayList<>();
        String request = "select * from Formation where numFormation IN (Select numFormation From MembreFormation " +
                "Where matriculeEtud = "+ appr.getMatricule() +")";
        ResultSet res = dataBase.selectRequest(request);

        while(res.next()){
            Instructeur inst =  Instructeur.LoadInstructeur(Instructeur.getUserWithMat(res.getInt("matriculeEns")));
            Formation form = new Formation(res.getInt("numFormation"), res.getString("nomFormation"),
                    res.getString("description"), inst);
            list.add(form);
        }
        return list;
    }


    public static ArrayList<Formation> loadAllFormations() {
        ArrayList<Formation> list = new ArrayList<>();

        String request = "SELECT * FROM Formation";
        ResultSet res = dataBase.selectRequest(request);

        try
        {
            while (res.next()) {

                Instructeur inst =Instructeur.LoadInstructeur(Instructeur.getUserWithMat(res.getInt("matriculeEns")));
                Formation form = new Formation(res.getInt("numFormation"), res.getString("nomFormation"),
                        res.getString("description"), inst);
                list.add(form);

            }
            return list;
        }catch (SQLException e){ return null;}

    }

    public static int nbFormation(){
        String request = "select max(numFormation) from Formation";
        ResultSet res = dataBase.selectRequest(request);
        try{
            if(res.next()){
                return res.getInt("max(numFormation)")+1;
            }
            return 0;
        }catch (SQLException e){return -1;}
    }

    static Formation createFormation(int numFormation,String nomFormation, Instructeur inst, String description){
        if(!dataBase.keyExist("Formation", "numFormation = " + numFormation)){
            if(addFormation(numFormation, nomFormation, inst.getMatricule(), description)){
                return new Formation(numFormation, nomFormation, description, inst);
            }
        }
        return null;
    }

    private static boolean addFormation(int numFormation,String nomFormation, int matricule, String description){
        String requete = "insert into Formation(numFormation ,nomFormation, matriculeEns, description) values(" +
                numFormation + ",'" + nomFormation + "'," + matricule + ",'" + description + "')" ;

        int cp = dataBase.insertRequest(requete);

        if (cp == 0){
            return false;
        }
        return true;
    }
}