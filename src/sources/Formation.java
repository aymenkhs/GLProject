package sources;

import dataBase.Jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Formation {
    private static Jdbc dataBase;
    private static int cmpt = 0;

    private int numFormation;
    private String nomFormation, description;
    private String instName;
    private Instructeur inst;
    private ArrayList<Cour> listCours;

    public Formation(int numFormation, String nomFormation, String description, Instructeur inst) {
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
                    Instructeur.LoadInstructeur(Instructeur.getUserWithMat(res.getString("matriculeEns"))));
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
            Instructeur inst =  Instructeur.LoadInstructeur(Instructeur.getUserWithMat(String.valueOf(res.getInt("matriculeEns"))));
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

                Instructeur inst =Instructeur.LoadInstructeur(Instructeur.getUserWithMat(res.getString("matriculeEns")));
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

    public static void addFormation(){

    }
}
