package userInterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sources.Sondage;

import java.util.ArrayList;

public class SondageView {

    TableView<Sondage> sondageTable;

    SondageView() {

        TableColumn<Sondage, Integer> numSCol = new TableColumn<>("Num Sondage");
        numSCol.setMinWidth(150);
        numSCol.setCellValueFactory(new PropertyValueFactory<>("numSondage"));

        TableColumn<Sondage, String> nomSCol = new TableColumn<>("Description de Sondage");
        nomSCol.setMinWidth(150);
        nomSCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Sondage, String> creatCol = new TableColumn<>("Nom Createur");
        creatCol.setMinWidth(150);
        creatCol.setCellValueFactory(new PropertyValueFactory<>("nomCreateur"));

        sondageTable = new TableView<>(getAllSondages());

        sondageTable.getColumns().addAll(numSCol, nomSCol, creatCol);

    }

    public ObservableList<Sondage> getAllSondages(){

        ObservableList<Sondage> sondageList = FXCollections.observableArrayList();

        ArrayList<Sondage> allSondages = null;
        allSondages = Sondage.loadAllSondages();

        if(allSondages == null) {
            return sondageList;
        } else {
            for(Sondage s : allSondages) {
                sondageList.add(s);
            }
        }
        return sondageList;
    }

    public ObservableList<Sondage> getMySondages(String userName) {

        ObservableList<Sondage> sondageList = FXCollections.observableArrayList();

        ArrayList<Sondage> mySondages = null;
        mySondages = Sondage.loadMySondages(userName);

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
