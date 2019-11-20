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
    private Instructeur inst;

    public Formation(int numFormation, String nomFormation, String description, Instructeur inst) {
        this.numFormation = numFormation;
        this.nomFormation = nomFormation;
        this.description = description;
        this.inst = inst;
    }

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
        }
        return list;
    }
}
