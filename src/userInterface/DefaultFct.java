package userInterface;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DefaultFct {

    public static GridPane defaultGrid(){
        GridPane newGrid = new GridPane();
        newGrid.setPadding(new Insets(10,10,10,10));
        newGrid.setVgap(10);
        newGrid.setHgap(10);
        newGrid.setAlignment(Pos.CENTER);
        return newGrid;
    }

    public static HBox defaultHbox(Pos p){
        HBox root = new HBox();
        root.setSpacing(10);
        root.setAlignment(p);
        return root;
    }

    public static HBox defaultHbox(){
        return defaultHbox(Pos.CENTER);
    }


    public static VBox defaultVbox(Pos p , int s){
        VBox root = new VBox();
        root.setSpacing(s);
        root.setAlignment(p);
        return root;
    }

    public static VBox defaultVbox(int s){
        return defaultVbox(Pos.CENTER, s);
    }

    public static VBox defaultVbox(){
        return defaultVbox(Pos.CENTER, 10);
    }

    public static Stage defaultStage(String title, Scene sc){
        Stage myStage = new Stage();
        myStage.setTitle(title);
        myStage.setScene(sc);
        return myStage;
    }

    public static BorderPane defaultBorder() {

        BorderPane border = new BorderPane();

        border.setStyle("-fx-background-color: #b9d8ff");
        border.setPadding(new Insets(12,12,12,12));

        return border;
    }
}
