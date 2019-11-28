package userInterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sources.Personne;
import sources.Sondage;
import sources.User;

import java.util.ArrayList;

public class SondageView {

    private TableView<Sondage> sondageTable;
    private User user;

    SondageView(User user , int whatSondage) { // whatSondage (0 : all, 1 : sondage cree, 2 : sondage participer

        this.user = user;

        TableColumn<Sondage, Integer> numSCol = new TableColumn<>("Num Sondage");
        numSCol.setMinWidth(150);
        numSCol.setCellValueFactory(new PropertyValueFactory<>("numSondage"));

        TableColumn<Sondage, String> nomSCol = new TableColumn<>("Description de Sondage");
        nomSCol.setMinWidth(150);
        nomSCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Sondage, String> creatCol = new TableColumn<>("Nom Createur");
        creatCol.setMinWidth(150);
        creatCol.setCellValueFactory(new PropertyValueFactory<>("nomCreateur"));

        if(whatSondage == 0){
            sondageTable = new TableView<>(getAllSondages());
        }else if (whatSondage == 1){
            sondageTable = new TableView<>(getMySondages());
        }else{
            sondageTable = new TableView<>(getSondagesPart());
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

        ArrayList<Sondage> mySondages = user.listSondagesCree();

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

        Personne per = (Personne) user;
        ArrayList<Sondage> mySondages = per.listSondagesParticiper();

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
