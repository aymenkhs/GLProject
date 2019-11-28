package userInterface;

import dataBase.Jdbc;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sources.Apprenant;
import sources.Formation;

import java.time.format.DateTimeFormatter;

public class StudentUI extends UserUI{

    /*
     * All the Attributes will be set when the scene is created, its given :Apprenant object as entry
     * */
    /*
    I'mdoing somme change to optimise it... aymen
     */

    private Apprenant app;

    private Scene studentUIScene;

    //add this comme entree de constructeur Apprenant student

    public StudentUI(Stage parentStage, Apprenant student) {
        window = parentStage;
        app = student;
        createHomeStudent();
    }

    public void createHomeStudent(){
        initialisation();
        loadInfo();
        formationInnit();

        Label welcome= new Label("Espace Etudiant");
        welcome.setFont(new Font(50));
        welcome.setStyle("-fx-text-fill: #ff8e47");

        firstLevelBox.getChildren().addAll(welcome,secondLevelBox);

        BorderPane.setMargin(firstLevelBox, new Insets(12,12,12,12));
        userIntBorder.setCenter(firstLevelBox);

        studentUIScene = new Scene(userIntBorder,600,500);

        window.setScene(studentUIScene);
    }

    private void loadInfo(){
        matriculeLabel.setText("Matricule: " + app.getMatricule());
        userNameLabel.setText("User Name: " + app.getUserName());
        emailLabel.setText("Email: " + app.getEmail());
        lastNameLabel.setText("Nom: " + app.getNom());
        firstNameLabel.setText("Prenom: " + app.getPrenom());
        DateNLabel.setText("Date de Naissance: " + app.getDateNaissance().format(DateTimeFormatter.ISO_DATE));
        Formation.setDataBase(new Jdbc());
    }

    private void formationInnit(){
        Label formationLabel = new Label("FORMATIONS");
        formationLabel.setFont(new Font(20));
        GridPane.setConstraints(formationLabel, 0, 0, 2, 1);

        Button everyFormsButton = new Button("Toutes les Formations");
        GridPane.setConstraints(everyFormsButton, 0, 2);
        everyFormsButton.setOnAction(e-> FormationUI.allFormsAction());

        Button myFormsButton = new Button("Mes Formations");
        GridPane.setConstraints(myFormsButton, 1, 2);
        myFormsButton.setOnAction(e-> FormationUI.myFormsAction(app));

        Button tesDevButton = new Button("Tests/Devoirs");
        GridPane.setConstraints(tesDevButton, 0, 3);
        //tesDevButton.setOnAction(e->);

        formationGrid.getChildren().addAll(formationLabel, everyFormsButton, myFormsButton, tesDevButton);
    }


}