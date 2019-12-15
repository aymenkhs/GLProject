package userInterface;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import sources.*;

import java.util.ArrayList;

public class genViews {

    public static ListView<String> getCours(Formation form) {

        ListView<String> cours;
        ObservableList<String> obsCours = FXCollections.observableArrayList();

        ArrayList<Cour> coursArray = form.LoadCours();

        if(!coursArray.isEmpty()) {
            for(Cour c : coursArray) {
                obsCours.add(c.toString());
            }
        }

        cours = new ListView<>(obsCours);

        return cours;
    }

    public static ListView<Test> getTests(Formation form) {

        ListView<Test> tests;
        ObservableList<Test> obsTests = FXCollections.observableArrayList();

        ArrayList<Test> quizArray = form.LoadTests();

        if(!quizArray.isEmpty()) {
            for(Test t : quizArray) {
                obsTests.add(t);
            }
        }

        tests = new ListView<>(obsTests);

        return tests;
    }

    public static ListView<Test> getValidateTests(Formation form) {

        ListView<Test> tests;
        ObservableList<Test> obsTests = FXCollections.observableArrayList();

        ArrayList<Test> quizArray = form.LoadDisponibleTests();

        if(!quizArray.isEmpty()) {
            for(Test t : quizArray) {
                obsTests.add(t);
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

    public static ListView<Apprenant> getApprenant() {

        ListView<Apprenant> apps;
        ObservableList<Apprenant> obsApps = FXCollections.observableArrayList();

        ArrayList<Apprenant> appArray = Apprenant.loadAllApprenant();

        if(!appArray.isEmpty()) {
            for(Apprenant app : appArray) {
                obsApps.add(app);
            }
        }

        apps = new ListView<>(obsApps);
        apps.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE);
        return apps;
    }

    public static ListView<Historique> getMembre(Formation form) {

        ListView<Historique> membres;
        ObservableList<Historique> obsMembres = FXCollections.observableArrayList();

        ArrayList<Historique> appArray = form.loadApprenants();

        if(!appArray.isEmpty()) {
            for(Historique m : appArray) {
                obsMembres.add(m);
            }
        }

        membres = new ListView<>(obsMembres);

        return membres;
    }

}
