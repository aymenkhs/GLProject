package sources;

import dataBase.Jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Test {
    private static Jdbc dataBase;

    private Formation form;
    private int numTest;
    private boolean disponible;

    private ArrayList<Question> listQst;

    Test(Formation form, int numTest) {
        this.form = form;
        this.numTest = numTest;
        this.listQst = new ArrayList<>();
    }

    Test(Formation form, int numTest, boolean disponible) {
        this.form = form;
        this.numTest = numTest;
        this.disponible = disponible;
        this.listQst = new ArrayList<>();
        LoadQuestions();
    }

    public int getNumTest() {
        return numTest;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public ArrayList<Question> getListQst() {
        return listQst;
    }

    @Override
    public String toString() {
        return "Test numero : " + numTest;
    }

    /*
        Work on "Question"
          Load them
          and Load Their Choices
          so a class "Question" it's a good idea...
        Work on "Note"

    */

    public boolean validerTest(){
        if(isTestValide()){
            disponible = true;
            return true;
        }
        return false;
    }

    private boolean isTestValide(){
        switch (listQst.size()){
            case 5:
                return true;
            case 10:
                return true;
            case 20:
                return true;
            case 40:
                return true;
        }
        return false;
    }

    public void calculerBareme(){

    }

    // Question

    public void LoadQuestions(){
        String request = "select * from Question where numFormation = "+ form.getNumFormation() +" and numTest = " + numTest;
        ResultSet res = dataBase.selectRequest(request);
        try{
            while(res.next()){
                listQst.add( new Question(form.getNumFormation(), numTest, res.getInt("numQusetion"),
                        res.getString("enoncerQuestion")));
            }
        }catch (SQLException e){}
    }

    public Question addQuestion(int numQst, String enoncerQst){
        String requete = "insert into Question(numFormation , numTest, numQusetion, enoncerQuestion) values(" +
                form.getNumFormation() + "," + numTest + "," + numQst + ",'" + enoncerQst + "')";
        if (dataBase.insertRequest(requete) != 0){
            Question qst = new Question(form.getNumFormation(), numTest, numQst, enoncerQst);
            listQst.add(qst);

        }
        return null;
    }

    // Methodes Static
    public static void setDataBase(Jdbc db){
        dataBase = db;
    }


}
