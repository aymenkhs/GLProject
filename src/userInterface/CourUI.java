package userInterface;


import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CourUI {

    static Stage addCoursPopUp;
    static Label coursTitleLabel, coursPathLabel, coursContentLabel;
    static TextField coursTitle, coursPath;
    static HBox coursTitleHbox, buttonHBox;
    static TextArea coursContent;
    static Button addCours, closePopPup;
    static GridPane addCoursGrid;
    static Scene addCoursScene;

    public static void addCours() {
        addCoursPopUp = new Stage();

        //title and title label
        coursTitleLabel = new Label("Titre de cours: ");
        coursTitle = new TextField();
        //hbox for the title and title label
        coursTitleHbox = DefaultFct.defaultHbox();
        coursTitleHbox.getChildren().addAll(coursTitleLabel, coursTitle);

        coursPathLabel = new Label("Chemin du cours: ");
        coursPath = new TextField();
        //adding the label and text field to the title Hbox
        coursTitleHbox.getChildren().addAll(coursPathLabel, coursPath);

        coursContentLabel = new Label("Contenu de cours:");
        coursContent = new TextArea();
        //add and close buttons
        addCours = new Button("Ajouter Cours");
        closePopPup = new Button("Fermer");
        addCours.setOnAction(e -> addCoursAction());
        closePopPup.setOnAction(e -> addCoursPopUp.close());
        //Hbox for the Fermer and Ajouter cours buttons
        buttonHBox = DefaultFct.defaultHbox();
        buttonHBox.getChildren().addAll(addCours, closePopPup);


        addCoursGrid = DefaultFct.defaultGrid();
        addCoursGrid.setConstraints(coursTitleHbox,0,0);
        addCoursGrid.setConstraints(coursContentLabel, 0,1);
        addCoursGrid.setConstraints(coursContent, 0,2);
        addCoursGrid.setConstraints(buttonHBox, 0, 4);

        addCoursGrid.getChildren().addAll(coursTitleHbox, coursContentLabel, coursContent, buttonHBox);

        addCoursScene = new Scene(addCoursGrid, 640,390);

        addCoursPopUp.setScene(addCoursScene);
        addCoursPopUp.showAndWait();

    }

    public static void addCoursAction() {
        /*
         ok ok, so we must add an Formation attribut to add a course you know...
         so well i'll do it later
          */


        if(verifieChampsVides() == 0) {
            return;
        }else if(verifieChampsVides() == 1) {
            //Cour c = new Cour();
        }else {
            //Cour c = new Cour();
        }

    }

    private static int verifieChampsVides() {  // 0:error, 1:path vide, 2:all filled
        String redPromptText = "-fx-prompt-text-fill: red";
        if (emptyField(redPromptText, coursTitle)) return 0;
        if (emptyField(redPromptText, coursContent)) return 0;
        if (coursPath.getText().isBlank()) return 1;
        return 2;
    }

    private static boolean emptyField(String redPromptText, TextInputControl text) {
        if(text.getText().isBlank()) {
            text.setPromptText("Ce champ doit etre remplis");
            text.setStyle(redPromptText);
            return true;
        }
        return false;
    }


}

