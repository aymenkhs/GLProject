package userInterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sources.Sondage;

import java.util.ArrayList;

public class SondageView {

    private TableView<Sondage> sondageTable;
    private String userName;
    SondageView(int option, String userName) { // option {0 : all, 1 : created, 2 : answered}

        this.userName = userName;

        TableColumn<Sondage, Integer> numSCol = new TableColumn<>("Num Sondage");
        numSCol.setMinWidth(150);
        numSCol.setCellValueFactory(new PropertyValueFactory<>("numSondage"));

        TableColumn<Sondage, String> nomSCol = new TableColumn<>("Description de Sondage");
        nomSCol.setMinWidth(150);
        nomSCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Sondage, String> creatCol = new TableColumn<>("Nom Createur");
        creatCol.setMinWidth(150);
        creatCol.setCellValueFactory(new PropertyValueFactory<>("nomCreateur"));

        switch (option) {
            case 0:
                sondageTable = new TableView<>(getAllSondages());
                break;
            case 1:
                sondageTable = new TableView<>(getMySondages());
                break;
            case 2:
                sondageTable = new TableView<>(getSondagesPart());
                break;
        }

        sondageTable.getColumns().addAll(numSCol, nomSCol, creatCol);

    }

    public TableView<Sondage> getSondageTable() {
        return sondageTable;
    }

    private ObservableList<Sondage> getAllSondages(){

        ObservableList<Sondage> sondageList = FXCollections.observableArrayList();

        ArrayList<Sondage> allSondages = Sondage.loadAllSondages();

        if(allSondages == null) {
            return sondageList;
        } else {
            for(Sondage s : allSondages) {
                sondageList.add(s);
            }
        }
        return sondageList;
    }

    private ObservableList<Sondage> getMySondages() {

        ObservableList<Sondage> sondageList = FXCollections.observableArrayList();

        ArrayList<Sondage> mySondages = Sondage.loadSondagesPerCreateur(this.userName);

        if(mySondages.isEmpty()) {
            return sondageList;
        } else {
            for(Sondage s : mySondages) {
                sondageList.add(s);
            }
        }

        return sondageList;
    }

    private ObservableList<Sondage> getSondagesPart() {

        ObservableList<Sondage> sondageList = FXCollections.observableArrayList();

        ArrayList<Sondage> mySondages = Sondage.loadSondagesParticiper(userName);

        if(mySondages.isEmpty()) {
            return sondageList;
        } else {
            for(Sondage s : mySondages) {
                sondageList.add(s);
            }
        }

        return sondageList;
    }

}
