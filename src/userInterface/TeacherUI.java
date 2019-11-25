package userInterface;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sources.Instructeur;

import java.time.format.DateTimeFormatter;

public class TeacherUI extends UserUI{

    private Instructeur inst;

    private Scene teacherUIScene;

    public TeacherUI(Stage parentStage, Instructeur teacher) {
        inst = teacher;
        window = parentStage;
        createHomeTeacher();
    }

    public void createHomeTeacher(){
        initialisation();
        loadInfo();
        formationInnit();

        Label welcome= new Label("Espace Enseignant");
        welcome.setFont(new Font(50));
        welcome.setStyle("-fx-text-fill: #ff8e47");

        firstLevelBox.getChildren().addAll(welcome,secondLevelBox);

        BorderPane.setMargin(firstLevelBox, new Insets(12,12,12,12));
        userIntBorder.setCenter(firstLevelBox);

        teacherUIScene = new Scene(userIntBorder,600,500);

        window.setScene(teacherUIScene);
    }

    private void loadInfo(){
        matriculeLabel.setText("Matricule: " + inst.getMatricule());
        userNameLabel.setText("User Name: " + inst.getUserName());
        emailLabel.setText("Email: " + inst.getEmail());
        lastNameLabel.setText("Nom: " + inst.getNom());
        firstNameLabel.setText("Prenom: " + inst.getPrenom());
        DateNLabel.setText("Date de Naissance: " + inst.getDateNaissance().format(DateTimeFormatter.ISO_DATE));
    }

    private void formationInnit(){
        Label formationLabel = new Label("FORMATIONS");
        formationLabel.setFont(new Font(20));
        GridPane.setConstraints(formationLabel, 0, 0, 2, 1);

        Button myFormsButton = new Button("Mes Formations");
        GridPane.setConstraints(myFormsButton, 0, 2);
        //myFormsButton.setOnAction(e->);

        Button addTesDevButton = new Button("Ajouter Tests/Devoir");
        GridPane.setConstraints(addTesDevButton, 1, 3);
        //addTesDevButton.setOnAction(e->);

        Button addCourButton = new Button("Ajouter Cour");
        GridPane.setConstraints(addCourButton, 0, 3);
        //addCourButton.setOnAction(e->);

        Button addFormButton = new Button("Ajouter Formations");
        GridPane.setConstraints(addFormButton, 1, 2);
        //addFormButton.setOnAction(e->);

        Button addAppButton = new Button("Ajouter des Apprenants");
        GridPane.setConstraints(addAppButton, 0, 4);
        //addAppButton.setOnAction(e->);

        formationGrid.getChildren().addAll(formationLabel, myFormsButton, addFormButton ,addCourButton ,addTesDevButton
            ,addAppButton);
    }

}
