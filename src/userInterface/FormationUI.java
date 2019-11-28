package userInterface;

import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sources.Apprenant;
import sources.Formation;

public class FormationUI {

    public static void allFormsAction(){
        FormationView view = new FormationView(0, 0);
        TableView<Formation> formaTable = view.getTable();
        Text title =  new Text("Tous les formations");
        title.setFont(new Font(20));
        title.setStyle("-fx-text-fill: #ff8e47");

        HBox buttonHbox = new HBox();

        BorderPane formations = new BorderPane();
        formations.setStyle("-fx-background-color: #b9d8ff");

        formations.setTop(title);
        formations.setCenter(formaTable);
        formations.setBottom(buttonHbox);

        Scene formScene = new Scene(formations, 400,400);
        Stage formStage = new Stage();
        formStage.setScene(formScene);
        formStage.showAndWait();
    }

    public static void myFormsAction(Apprenant app){
        FormationView view = new FormationView(app.getMatricule(), 2);
        TableView<Formation> formaTable = view.getTable();
        Text title =  new Text("Mes formations");
        title.setFont(new Font(20));
        title.setStyle("-fx-text-fill: #ff8e47");

        BorderPane formations = new BorderPane();
        formations.setStyle("-fx-background-color: #b9d8ff");

        formations.setTop(title);
        formations.setCenter(formaTable);

        Scene formScene = new Scene(formations, 450,450);
        Stage formStage = new Stage();
        formStage.setScene(formScene);
        formStage.showAndWait();
    }

}
