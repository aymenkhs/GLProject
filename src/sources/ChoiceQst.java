package sources;

import dataBase.Jdbc;

public class ChoiceQst {
    private static Jdbc dataBase;

    private int numQuestion, numTest, numForm, numChoice;
    private String textChoice;
    private boolean choixJuste;

    public ChoiceQst(int numQuestion, int numTest, int numForm, int numChoice, String textChoice, boolean choixJuste) {
        this.numQuestion = numQuestion;
        this.numTest = numTest;
        this.numForm = numForm;
        this.numChoice = numChoice;
        this.textChoice = textChoice;
        this.choixJuste = choixJuste;
    }

    public boolean isChoixJuste() {
        return choixJuste;
    }

    public String getTextChoice() {
        return textChoice;
    }

    public int getNumChoice() {
        return numChoice;
    }

    public int getNumQuestion() {
        return numQuestion;
    }

    public int getNumTest() {
        return numTest;
    }

    @Override
    public String toString() {
        return textChoice;
    }

    protected boolean delete(){
        String requete = "delete from ChoixQuestion where numFormation=" + numForm + " and numTest=" + numTest +
                " and numQusetion=" + numQuestion + "and numChoixQ=" + numChoice;
        return dataBase.deleteRequest(requete)!=0;
    }

    // Methodes Static
    public static void setDataBase(Jdbc db){
        dataBase = db;
    }
}
