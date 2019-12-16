package sources;

import dataBase.Jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Question{
    private static Jdbc dataBase;

    private int numQuestion, numTest, numForm;
    private boolean valider;
    private String enoncerQuestion;
    private ArrayList<ChoiceQst> correctChoices, wrongChoices, randomChoice;

    Question(int numForm, int numTest, int numQuestion, String enoncerQuestion){
        this.numForm = numForm;
        this.numTest = numTest;
        this.numQuestion = numQuestion;
        this.enoncerQuestion = enoncerQuestion;
        correctChoices = new ArrayList<>();
        wrongChoices = new ArrayList<>();
        LoadChoices();
    }

    public int getNumQuestion() {
        return numQuestion;
    }

    public boolean isValider() {
        return valider;
    }

    public String getEnoncerQuestion() {
        return enoncerQuestion;
    }

    public ArrayList<ChoiceQst> getRandomChoice() {
        return randomChoice;
    }

    public int getNumTest() {
        return numTest;
    }

    @Override
    public String toString() {
        return numQuestion + ". " + enoncerQuestion;
    }

    public ArrayList<ChoiceQst> genRandomChoices(){
        randomChoice = new ArrayList<>();

        Random rand = new Random();

        if(wrongChoices.size() <= 3){
            randomChoice.addAll(wrongChoices);
        }else{
            // use random to select 3 wrong choice
            int[] tab = new int[3];

            tab[0] = rand.nextInt(wrongChoices.size());

            do{
                tab[1] = rand.nextInt(wrongChoices.size());
            }while(tab[1] == tab[0]);

            do{
                tab[2] = rand.nextInt(wrongChoices.size());
            }while(tab[2] == tab[0] || tab[2] == tab[1]);

            randomChoice.add(wrongChoices.get(tab[0]));
            randomChoice.add(wrongChoices.get(tab[1]));
            randomChoice.add(wrongChoices.get(tab[2]));
        }

        if(correctChoices.size() == 1){
            randomChoice.addAll(correctChoices);
        }else{
            // use random to select one correct choice
            int i = rand.nextInt(correctChoices.size());
            randomChoice.add(rand.nextInt(4) ,correctChoices.get(i));
        }

        return randomChoice;
    }

    public ArrayList<ChoiceQst> genRandomChoices(ChoiceQst choice){
        randomChoice = new ArrayList<>();

        Random rand = new Random();

        if(wrongChoices.size() <= 2){
            randomChoice.addAll(wrongChoices);
        }else{
            // use random to select 3 wrong choice
            int[] tab = new int[3];

            tab[0] = rand.nextInt(wrongChoices.size());

            do{
                tab[1] = rand.nextInt(wrongChoices.size());
            }while(tab[1] == tab[0]);

            do{
                tab[2] = rand.nextInt(wrongChoices.size());
            }while(tab[2] == tab[0] || tab[2] == tab[1]);

            randomChoice.add(wrongChoices.get(tab[0]));
            randomChoice.add(wrongChoices.get(tab[1]));
            randomChoice.add(wrongChoices.get(tab[2]));
        }

        randomChoice.add(rand.nextInt(3) ,choice);

        if(correctChoices.size() == 1){
            randomChoice.addAll(correctChoices);
        }else{
            // use random to select one correct choice
            int i = rand.nextInt(correctChoices.size());
            randomChoice.add(rand.nextInt(4) ,correctChoices.get(i));
        }



        return randomChoice;
    }

    public boolean validerQuestion(){
        if(isQuestionValide()){
            valider = true;
            return true;
        }
        return false;
    }

    private boolean isQuestionValide(){
        return (correctChoices.size() >= 1 && wrongChoices.size() >= 1);
    }

    public boolean isRepTrue(int choix){
        return getChoice(choix).isChoixJuste();
    }



    // Choices
    public void LoadChoices(){
        String request = "select * from ChoixQuestion where numFormation = "+ numForm +" and numTest = " + numTest + " and " +
                "numQusetion = " + numQuestion;
        ResultSet res = dataBase.selectRequest(request);
        try{
            while(res.next()){
                boolean bln = res.getInt("isTrue") == 1;
                initChoice(res.getInt("numChoixQ"), res.getString("contenueReponse"), bln);
            }
        }catch (SQLException e){}
    }

    private void initChoice(int numChoix, String contenue, boolean isTrue){

        ChoiceQst choice = new ChoiceQst(numForm, numTest, numQuestion, numChoix, contenue, isTrue);

        if(isTrue){
            correctChoices.add(choice);
        }else {
            wrongChoices.add(choice);
        }
    }

    public void addChoice(int numChoix, String contenue, boolean isTrue){

        int val = isTrue ? 1 : 0;
        String requete = "insert into ChoixQuestion(numFormation , numTest, numQusetion, numChoixQ, contenueReponse, isTrue)" +
                " values(" + numForm + "," + numTest + "," + numQuestion + "," + numChoix + ",'" + contenue + "'," + val + ")";
        if (dataBase.insertRequest(requete) != 0){
            initChoice(numChoix, contenue, isTrue);
        }
    }

    public ChoiceQst getChoice(int nchoix){
        for(ChoiceQst ch:correctChoices){
            if(ch.getNumChoice() == nchoix){
                return ch;
            }
        }

        for(ChoiceQst ch:wrongChoices){
            if(ch.getNumChoice() == nchoix){
                return ch;
            }
        }

        return null;
    }

    private boolean delete(){
        String requete = "delete from Question where numFormation=" + numForm + " and numTest=" + numTest +
                " and numQusetion=" + numQuestion;
        return dataBase.deleteRequest(requete)!=0;
    }

    public int nbChoices(){
        return correctChoices.size() + wrongChoices.size();
    }

    // Methodes Static
    public static void setDataBase(Jdbc db){
        dataBase = db;
    }
}