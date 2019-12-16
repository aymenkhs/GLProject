package userInterface;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sources.*;

import java.util.ArrayList;
import java.util.HashMap;

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

    static ListView<Question> getQuestions(Test test) {
        ListView<Question> questions;
        ObservableList<Question> obsQst = FXCollections.observableArrayList();

        ArrayList<Question> qstArray = test.getListQst();

        if(!qstArray.isEmpty()) {
            for(Question m : qstArray) {
                obsQst.add(m);
            }
        }

        questions = new ListView<>(obsQst);
        return questions;
    }

    static ListView<String> getHistorique(Historique hist) {
        ListView<String> actions;
        ObservableList<String> obsQst = FXCollections.observableArrayList();

        ArrayList<String> histArray = hist.loadActions();

        if(!histArray.isEmpty()) {
            for(String s : histArray) {
                obsQst.add(s);
            }
        }

        actions = new ListView<>(obsQst);
        return actions;
    }

    static TableView<MembrePassation> getTestPasser(Historique hist) {

        TableColumn<MembrePassation, Test> testCol = new TableColumn<>("Test");
        testCol.setMinWidth(100);
        testCol.setCellValueFactory(new PropertyValueFactory<>("test"));

        TableColumn<MembrePassation, Double> noteCol = new TableColumn<>("Note");
        noteCol.setMinWidth(250);
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));


        TableView<MembrePassation> testsPassation = new TableView<>(genObsTests(hist));
        testsPassation.getColumns().addAll(testCol, noteCol);
        return testsPassation;
    }

    private static ObservableList<MembrePassation> genObsTests(Historique hist){
        HashMap<Test, Double> dict = hist.loadTestsPasser();
        ArrayList<Test> tests = hist.getForm().LoadTests();
        ObservableList<MembrePassation> obs = FXCollections.observableArrayList();

        for(Test t:tests){
            if(dict.containsKey(t)){
                obs.add(new MembrePassation(t, dict.get(t)));
            }
        }

        return obs;
    }

    static TableView<MembrePassation> getDevPasser(Historique hist) {

        TableColumn<MembrePassation, Test> devCol = new TableColumn<>("Devoir");
        devCol.setMinWidth(100);
        devCol.setCellValueFactory(new PropertyValueFactory<>("dev"));

        TableColumn<MembrePassation, Double> noteCol = new TableColumn<>("Note");
        noteCol.setMinWidth(250);
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));


        TableView<MembrePassation> testsPassation = new TableView<>(genObsDev(hist));
        testsPassation.getColumns().addAll(devCol, noteCol);
        return testsPassation;
    }

    private static ObservableList<MembrePassation> genObsDev(Historique hist){
        HashMap<Devoir, Double> dict = hist.loadDevoirsPasser();
        ArrayList<Devoir> devs = hist.getForm().LoadDevoirs();
        ObservableList<MembrePassation> obs = FXCollections.observableArrayList();

        for(Devoir d:devs){
            if(dict.containsKey(d)){
                obs.add(new MembrePassation(d, dict.get(d)));
            }
        }

        return obs;
    }

}
