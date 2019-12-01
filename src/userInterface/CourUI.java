package userInterface;


import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sources.Cour;
import sources.Formation;

public class CourUI {

    static Stage addCoursPopUp;
    static Label coursTitleLabel, coursPathLabel, coursContentLabel;
    static TextField coursTitle, coursPath;
    static HBox coursTitleHbox, buttonHBox;
    static TextArea coursContent;
    static Button addCours, closePopPup;
    static GridPane addCoursGrid;
    static Scene addCoursScene;

    public static void addCours(Formation form) {
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
        addCours.setOnAction(e -> {
            if(addCoursAction(form)) {
                addCoursPopUp.close();
            }
        });
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

    public static boolean addCoursAction(Formation form) {
        /*
         ok ok, so we must add an Formation attribut to add a course you know...
         so well i'll do it later
          */


        if(verifieChampsVides()) {
            Cour c = form.addCour(coursTitle.getText(), coursContent.getText());
            if(c == null) {
                AlertBox.displayError("Ce nom de cours existe deja dans cette formation");
                return false;
            }
            return true;
        }
        return false;

    }

    private static boolean verifieChampsVides() {  // 0:error, 1
        String redPromptText = "-fx-prompt-text-fill: red";
        if (emptyField(redPromptText, coursTitle)) return false;
        if (emptyField(redPromptText, coursContent)) return false;
        return true;
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

