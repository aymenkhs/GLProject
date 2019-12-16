package userInterface;

public class temp_Choice {

    private String contenue;
    private int numUI;
    private boolean right, areadyExist;
    private int action; // a modifier = 0 / a supprimmer = 1 / rien faire = -1

    public temp_Choice(String contenue, int numUI, boolean right) {
        this.contenue = contenue;
        this.numUI = numUI;
        this.right = right;
    }

    public temp_Choice(String contenue, int numUI , int action) {
        this.contenue = contenue;
        this.numUI = numUI;
        this.right = false;
    }

    public String getContenue() {
        return contenue;
    }

    public int getNumUI() {
        return numUI;
    }

    public void setContenue(String contenue) {
        this.contenue = contenue;
    }

    public void setNumUI(int numUI) {
        this.numUI = numUI;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}
