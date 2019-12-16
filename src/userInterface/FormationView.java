package userInterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sources.Apprenant;
import sources.Formation;
import sources.Instructeur;

import java.sql.SQLException;
import java.util.ArrayList;

public class FormationView {

    private TableView<Formation> formaTable;

    FormationView(int matricule, int type) { //type {0:ALL, 1:Instructeur, 2:Apprenant}

        TableColumn<Formation, Integer> numFCol = new TableColumn<>("Num Formation");
        numFCol.setMinWidth(150);
        numFCol.setCellValueFactory(new PropertyValueFactory<>("numFormation"));

        TableColumn<Formation, String> nomFCol = new TableColumn<>("Nom Formation");
        nomFCol.setMinWidth(150);
        nomFCol.setCellValueFactory(new PropertyValueFactory<>("nomFormation"));

        TableColumn<Formation, String> instCol = new TableColumn<>("Nom Instructeur");
        instCol.setMinWidth(150);
        instCol.setCellValueFactory(new PropertyValueFactory<>("instName"));

        if(type == 0) {
            formaTable = new TableView<>(getAllFormations());
        }else if (type == 1){
            formaTable = new TableView<>(getInstFormations(matricule));
        }else{
            formaTable = new TableView<>(getApprFormations(matricule));
        }

        formaTable.getColumns().addAll(numFCol, nomFCol, instCol);

    }

    public TableView<Formation> getTable() { return formaTable; }

    private ObservableList<Formation> getAllFormations() {

        ObservableList<Formation> formList = FXCollections.observableArrayList();

        ArrayList<Formation> allForms = null;
        allForms = Formation.loadAllFormations();

        if(allForms == null) {
            return formList;
        }

        for (Formation f : allForms) {
            formList.add(f);
        }

        return formList;
    }


    private ObservableList<Formation> getInstFormations(int matricule) {

        ObservableList<Formation> formList = FXCollections.observableArrayList();

        Instructeur inst = Instructeur.LoadInstructeur(Instructeur.getUserWithMat(matricule));
        if(inst == null) {
            return formList;
        }

        ArrayList<Formation> instForms = null;
        try {
            instForms = Formation.loadFormation(inst);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(instForms == null) {
            return formList;
        }

        for (Formation f : instForms) {
            formList.add(f);
        }
        return formList;
    }

    private ObservableList<Formation> getApprFormations(int matricule) {

        ObservableList<Formation> formList = FXCollections.observableArrayList();

        Apprenant appr = null;
        try {
            appr = Apprenant.LoadApprenant(Apprenant.getUserWithMat(matricule));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<Formation> instForms = null;

        if(appr == null) {
            return formList;
        }

        try {
            instForms = Formation.loadFormationAppr(appr);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(instForms == null) {
            return formList;
        }

        for (Formation f : instForms) {
            formList.add(f);
        }

        return formList;
    }

}
