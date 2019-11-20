package userInterface;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class LogSignScene {
    private Stage window;

    private Scene loginScene;

    private GridPane loginGrid;
    private GridPane signInGrid;

    private TextField userName;
    private PasswordField userPassword;

    private PasswordField userPasswordText;
    private TextField lastNameText, firstNameText, emailText, matriculeText, userNameText;
    private TextField speText, yearText, moduleText, gradeText;

    private DatePicker dateNaissance;

    private ComboBox<String> typeChoice;


    public LogSignScene(Stage primaryStage) {
        this.window = primaryStage;
        this.createLoginScene();
    }


    public void createLoginScene(){

        loginGrid = DefaultFct.defaultGrid();

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 0, 3);
        loginButton.setOnAction(e -> login());

        Button signinButton = new Button("Sign in");
        GridPane.setConstraints(signinButton, 1, 3);
        signinButton.setOnAction(e -> createSignInScene());

        userName = new TextField();
        userPassword = new PasswordField();
        GridPane.setConstraints(userName, 1, 1);
        GridPane.setConstraints(userPassword, 1, 2);

        Label userNameLabel = new Label("User Name");
        Label userPasswordLabel = new Label("Password");
        GridPane.setConstraints(userNameLabel, 0, 1);
        GridPane.setConstraints(userPasswordLabel, 0, 2);

        loginGrid.getChildren().addAll(userNameLabel, userName, userPasswordLabel, userPassword, loginButton, signinButton);
        loginScene = new Scene(loginGrid, 300, 250);
    }

    public void moveToLoginScene(){
        window.setScene(loginScene);
    }

    public void login(){

    }

    public void createSignInScene(){
        signInGrid = DefaultFct.defaultGrid();

        Label typeChoiceLabel = new Label("Vous etes un : ");
        Label userNameLabel = new Label("User Name");
        Label userPasswordLabel = new Label("Password");
        Label emailLabel = new Label("Email");
        Label lastNameLabel = new Label("Nom");
        Label matriculeLabel = new Label("Matricule");
        Label firstNameLabel = new Label("Prenom");
        Label dateNaissanceLabel = new Label("Date de  Naissance");

        userNameText = new TextField();
        lastNameText = new TextField();
        firstNameText = new TextField();
        matriculeText = new TextField();
        dateNaissance = new DatePicker(LocalDate.now());
        userPasswordText = new PasswordField();
        emailText = new TextField();

        typeChoice = new ComboBox<>();
        typeChoice.getItems().addAll("Etudiant", "Enseignant");
        typeChoice.setValue("Etudiant");

        GridPane.setConstraints(typeChoiceLabel, 0, 0);
        GridPane.setConstraints(typeChoice, 1, 0);
        GridPane.setConstraints(lastNameLabel, 0, 1);
        GridPane.setConstraints(lastNameText, 1, 1);
        GridPane.setConstraints(firstNameLabel, 2, 1);
        GridPane.setConstraints(firstNameText, 3, 1);
        GridPane.setConstraints(matriculeLabel, 0, 2);
        GridPane.setConstraints(matriculeText, 1, 2);
        GridPane.setConstraints(dateNaissanceLabel, 2, 2);
        GridPane.setConstraints(dateNaissance, 3,2);
        GridPane.setConstraints(emailLabel, 0, 4);
        GridPane.setConstraints(emailText, 1, 4);
        GridPane.setConstraints(userNameLabel, 0, 5);
        GridPane.setConstraints(userNameText, 1, 5);
        GridPane.setConstraints(userPasswordLabel, 0, 6);
        GridPane.setConstraints(userPasswordText, 1, 6);

        Button signinButton = new Button("Sign in");
        GridPane.setConstraints(signinButton, 2, 8);
        //signinButton.setOnAction(e -> );

        Button returnButton = new Button("Retour");
        GridPane.setConstraints(returnButton, 0, 8);
        returnButton.setOnAction(e -> moveToLoginScene());

        signInGrid.getChildren().addAll(typeChoiceLabel, matriculeLabel, lastNameLabel, firstNameLabel, emailLabel,
                dateNaissanceLabel, userNameLabel, userPasswordLabel, matriculeText, lastNameText, firstNameText,
                dateNaissance, emailText, typeChoice, userNameText, userPasswordText, signinButton, returnButton);

        Scene signinScene = new Scene(signInGrid, 800, 500);
        window.setScene(signinScene);
    }



}
