package sources;

import dataBase.Jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Historique {

    static Jdbc dataBase;

    private Apprenant app;
    private Formation form;
    private int nbAction;


    public Historique(Apprenant app, Formation form) {
        this.app = app;
        this.form = form;
        nbActions();
    }

    public Apprenant getApp() {
        return app;
    }

    public Formation getForm() {
        return form;
    }

    public void InscriptionAppreant(){
        addAction("L'etudiant ces inscrit a cette formation");
    }

    public int isHePassedIt(Test test){
        /*
            return 1 if the student alredy completed this test and 0 if he started it and did'nt complete it and -1 if
            he did'nt started it at all
        */
        String request = "Select noteT from PasseTest where numFormation = " + form.getNumFormation() + " and matriculeEtud = "
                + app.getMatricule() + " and numTest = " + test.getNumTest();
        ResultSet res = dataBase.selectRequest(request);

        try{
            if(res.next()){
                if(res.getObject("noteT") != null){
                    return 1;
                }
                return 0;
            }
            return -1;
        }catch (Exception e){
            return -1;
        }
    }


    public void passerTest(Test test){
        String request = "insert into PasseTest (numTest, numFormation, matriculeEtud) values(" + test.getNumTest() +
                "," + form.getNumFormation() + "," + app.getMatricule() + ")";

        if(dataBase.insertRequest(request) != 0){
            addAction("L etudiant a commencer le test n°" + test.getNumTest());
        }
    }

    public ArrayList<Integer> etatPassationTest(Test test){
        String request = "Select numQusetion, numChoixQ From ReponseQuestion where numFormation = " + form.getNumFormation() +
                " and matriculeEtud = " + app.getMatricule() + " and numTest = " + test.getNumTest();
        ResultSet res = dataBase.selectRequest(request);


        ArrayList<Integer> qstRepondu = new ArrayList<>();

        try{
            while(res.next()){
                qstRepondu.add(res.getInt("numQusetion"));
            }
        }catch (SQLException e){System.out.println(e);}
        return qstRepondu;
    }

    public double finaliserTest(Test test){
        String request = "Select numQusetion, numChoixQ From ReponseQuestion where numFormation = " + form.getNumFormation() +
                " and matriculeEtud = " + app.getMatricule() + " and numTest = " + test.getNumTest();
        ResultSet res = dataBase.selectRequest(request);

        HashMap<Integer, Integer> reponses = new HashMap<>();

        try{
            while(res.next()){
                reponses.put(res.getInt("numQusetion"), res.getInt("numChoixQ"));
            }
        }catch (SQLException e){System.out.println(e);}

        addAction("L etudiant a finit le test n°" + test.getNumTest());

        double note = test.calculerNote(reponses);

        String upRequest = "update PasseTest set noteT = " + note + " where numFormation = " +
                form.getNumFormation() + " and matriculeEtud = " + app.getMatricule() + " and numTest = " + test.getNumTest();
        dataBase.updateRequest(upRequest);

        return note;
    }

    public void addRepQuestion(Question qst, ChoiceQst reponse){
        String request = "insert into ReponseQuestion (numTest, numFormation, matriculeEtud, numQusetion, numChoixQ) values("
                + reponse.getNumTest() + "," + form.getNumFormation() + "," + app.getMatricule() + "," + qst.getNumQuestion() + "," + reponse.getNumChoice() + ")";

        if(dataBase.insertRequest(request) != 0){
            addAction("L etudiant a repondu "+ reponse.getTextChoice() +" a La question " + qst.getNumQuestion() +
                    " dans le test n°" + reponse.getNumTest());
        }
    }

    public void alterRepQustion(Question qst, ChoiceQst reponse){

        // penser a verifier que l'etudiant a vraiment repondu une premiere fois a cette question

        String request = "Select contenueReponse from ChoixQuestion where numChoixQ = Select numChoixQ from ReponseQuestion" +
                " where  numFormation = " + form.getNumFormation() + " and matriculeEtud = " + app.getMatricule() +
                " and numTest = " + reponse.getNumTest() + " and numQusetion = " + reponse.getNumQuestion();
        ResultSet res = dataBase.selectRequest(request);

        try{
            if(res.next()){
                String ancienneRep = res.getString(0);
                String request2 = "update ReponseQuestion set numChoixQ  = " + reponse.getNumChoice()+ " where  numFormation = " +
                        form.getNumFormation() + " and matriculeEtud = " + app.getMatricule() + " and numTest = " +
                        reponse.getNumTest() + " and numQusetion = " + reponse.getNumQuestion();

                if(dataBase.updateRequest(request2) != 0){
                    addAction("L etudiant a changer sa reponse de "+ ancienneRep + " à " + reponse.getTextChoice() +
                            " a La question " + qst.getNumQuestion() + " dans le test n°" + reponse.getNumTest());
                }
            }

        }catch (SQLException e){System.out.println(e);}
    }

    public boolean isHePassedIt(Devoir dev){
        return dataBase.keyExist("PasseDevoir", "numFormation = " + form.getNumFormation() + " and matriculeEtud = "
                + app.getMatricule() + " and numDevoir = " + dev.getNumDevoir());
    }

    public void deposerDevoir(){

    }

    public void consulterCour(Cour cours){

    }

    private boolean addAction(String action){
        String request = "insert into Historique values(" + (nbAction+1) + "," + form.getNumFormation() + "," +
                app.getMatricule() + ",'" + (action + " le " + LocalDateTime.now().toString()) + "')";
        nbAction++;
        if(dataBase.insertRequest(request) != 0){
            System.out.println("eeee");
            return true;
        }
        return false;
    }

    public ArrayList<String> loadActions(){
        ArrayList<String> list = new ArrayList<>();
        String request = "select action from Historique where numFormation = "+ form.getNumFormation()+
                " and matriculeEtud = " + app.getMatricule();
        ResultSet res = dataBase.selectRequest(request);
        try{
            while(res.next()){
                list.add(res.getString("action"));
            }
        }catch (SQLException e){}
        return list;
    }

    public HashMap<Test, Double> loadTestsPasser(){
        HashMap<Test, Double> list = new HashMap<>();
        String request = "select * from PasseTest where numFormation = "+ form.getNumFormation()+
                " and matriculeEtud = " + app.getMatricule();
        ResultSet res = dataBase.selectRequest(request);
        try{
            while(res.next()){
                Test test = form.LoadTests(res.getInt("numTest"));
                if(isHePassedIt(test) == 1){
                    list.put(test, res.getDouble("noteT"));
                }
            }
        }catch (SQLException e){}
        return list;
    }

    public HashMap<Devoir, Double> loadDevoirsPasser(){
        HashMap<Devoir, Double> list = new HashMap<>();
        String request = "select * from PasseDevoir where numFormation = "+ form.getNumFormation()+
                " and matriculeEtud = " + app.getMatricule();
        ResultSet res = dataBase.selectRequest(request);
        try{
            while(res.next()){
                Devoir dev = form.LoadDevoirs(res.getInt("numDevoir"));
                list.put(dev, res.getDouble("noteD"));
            }
        }catch (SQLException e){}
        return list;
    }

    private int nbActions(){
        String request = "select max(numAction) from Historique where numFormation = " + form.getNumFormation() +
                " and matriculeEtud = " + app.getMatricule();
        ResultSet res = dataBase.selectRequest(request);
        try{
            if(res.next()){
                nbAction =  res.getInt("max(numAction)")+1;
                return nbAction;
            }
            nbAction = 0;
        }catch (SQLException e){nbAction = -1;}
        return nbAction;
    }

    // Static Methodes
    public static void setDataBase(Jdbc db){
        dataBase = db;
    }
}
