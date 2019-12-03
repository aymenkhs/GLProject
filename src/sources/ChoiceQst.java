package sources;

public class ChoiceQst {


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

    @Override
    public String toString() {
        return textChoice;
    }
}
