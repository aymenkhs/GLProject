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
    static GridPane coursGrid;

    private static void setUpPopUp() {
        addCoursPopUp = new Stage();

        //title and title label
        coursTitleLabel = new Label("Titre de cours: ");
        coursTitle = new TextField();
        //hbox for the title and title label
        coursTitleHbox = DefaultFct.defaultHbox();
        coursTitleHbox.getChildren().addAll(coursTitleLabel, coursTitle);

        coursContentLabel = new Label("Contenu de cours:");
        coursContent = new TextArea();

        closePopPup = new Button("Fermer");
        closePopPup.setOnAction(e -> addCoursPopUp.close());

        buttonHBox = DefaultFct.defaultHbox();

        coursGrid = DefaultFct.defaultGrid();
        coursGrid.setConstraints(coursTitleHbox,0,0);
        coursGrid.setConstraints(coursContentLabel, 0,1);
        coursGrid.setConstraints(coursContent, 0,2);

        coursGrid.getChildren().addAll(coursTitleHbox, coursContentLabel, coursContent);
    }

    public static void addCours(Formation form) {
        setUpPopUp();
        Scene addCoursScene;
        //add button
        addCours = new Button("Ajouter Cours");
        addCours.setOnAction(e -> {
            if(addCoursAction(form)) {
                addCoursPopUp.close();
            }
        });

        //Hbox for the Fermer and Ajouter cours buttons
        buttonHBox.getChildren().addAll(addCours, closePopPup);

        coursGrid.setConstraints(buttonHBox, 0, 4);

        coursGrid.getChildren().addAll(coursTitleHbox, coursContentLabel, coursContent, buttonHBox);

        addCoursScene = new Scene(coursGrid, 640,390);

        addCoursPopUp.setScene(addCoursScene);
        addCoursPopUp.showAndWait();

    }

    public static void openCours(Cour cours) {
        setUpPopUp();
        Scene openCoursScene;

        coursTitle.setDisable(true);
        coursContent.setDisable(true);
        buttonHBox.getChildren().addAll(closePopPup);
        coursGrid.setConstraints(buttonHBox, 0, 4);
        coursGrid.getChildren().add(buttonHBox);

        openCoursScene = new Scene(coursGrid, 640, 390);
        addCoursPopUp.showAndWait();
    }

    public static void modifierCours(Cour cours) {
        setUpPopUp();
        Scene modifierCoursScene;
        Button valider = new Button("Valider");
        coursTitle.setDisable(true);
        coursContent.setText(cours.chargerCour());

        buttonHBox.getChildren().addAll(valider, closePopPup);
        coursGrid.setConstraints(buttonHBox, 0, 4);
        coursGrid.getChildren().add(buttonHBox);
        valider.setOnAction(e -> cours.sauvgCour(coursContent.getText()));
        modifierCoursScene = new Scene(coursGrid, 640,390);
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

