package userInterface;

import sources.Devoir;
import sources.Test;

public class MembrePassation {

    private Test test;
    private Devoir dev;
    private double note;

    public MembrePassation(Test test, double note) {
        this.test = test;
        this.note = note;
    }

    public MembrePassation(Devoir dev, double note) {
        this.dev = dev;
        this.note = note;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Devoir getDev() {
        return dev;
    }

    public void setDev(Devoir dev) {
        this.dev = dev;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }
}
