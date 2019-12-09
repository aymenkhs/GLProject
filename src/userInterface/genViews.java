package userInterface;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import sources.Cour;
import sources.Devoir;
import sources.Formation;
import sources.Test;

import java.util.ArrayList;

public class genViews {

    public static ListView<Cour> getCours(Formation form) {

        ListView<Cour> cours;
        ObservableList<Cour> obsCours = FXCollections.observableArrayList();

        ArrayList<Cour> coursArray = form.LoadCours();

        if(!coursArray.isEmpty()) {
            for(Cour c : coursArray) {
                obsCours.add(c);
            }
        }

        cours = new ListView<Cour>(obsCours);

        return cours;
    }

    public static ListView<String> getTests(Formation form) {

        ListView<String> tests;
        ObservableList<String> obsTests = FXCollections.observableArrayList();

        ArrayList<Test> quizArray = form.LoadTests();

        if(!quizArray.isEmpty()) {
            for(Test t : quizArray) {
                obsTests.add(t.toString());
            }
        }

        tests = new ListView<>(obsTests);

        return tests;
    }

    public static ListView<String> getDevoirs(Formation form) {

        ListView<String> devoirs;
        ObservableList<String> obsDevoirs = FXCollections.observableArrayList();

        ArrayList<Devoir> devoirArray = form.LoadDevoirs();

        if(!devoirArray.isEmpty()) {
            for(Devoir d : devoirArray) {
                obsDevoirs.add(d.toString());
            }
        }

        devoirs = new ListView<>(obsDevoirs);

        return devoirs;
    }

}
