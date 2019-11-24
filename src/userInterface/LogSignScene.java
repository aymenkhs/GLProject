package userInterface;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sources.*;

import java.time.LocalDate;

public class LogSignScene {
    private Stage window;

    private Scene loginScene;

    private GridPane loginGrid;
    private GridPane signInGrid;

    private TextField userName;
    private PasswordField userPassword;

    private PasswordField userPasswordText;
    private TextField lastNameText, firstNameText, emailText, userNameText;
    private TextField speText, yearText, moduleText, gradeText;

    private Label matriculeText;
    private int matriculeInt;

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
        loginButton.setOnAction(e -> loginAction());

        Button signinButton = new Button("Sign up");
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

    private void loginAction(){
        if(verifeLogin()){
            if(User.login(userName.getText(), userPassword.getText())){
                String type = User.userType(userName.getText());
                if(type.toLowerCase().equals("etudiant")){
                    Apprenant app = Apprenant.LoadApprenant(userName.getText());
                    launchHomeScreen(app);
                }else if(type.toLowerCase().equals("enseignant")){
                    Instructeur inst = Instructeur.LoadInstructeur(userName.getText());
                    launchHomeScreen(inst);
                }else{ //admin
                    // call loaddmin when it writen
                }
            }
        }
    }

    private boolean verifeLogin() {
        String redPromptText = "-fx-prompt-text-fill: red";
        if (emptyField(redPromptText, userName)) return false;
        if (emptyField(redPromptText, userPassword)) return false;
        return true;
    }

    public void createSignInScene(){
        signInGrid = DefaultFct.defaultGrid();

        Label typeChoiceLabel = new Label("Vous etes un : ");
        Label userNameLabel = new Label("User Name *");
        Label userPasswordLabel = new Label("Password *");
        Label emailLabel = new Label("Email *");
        Label lastNameLabel = new Label("Nom *");
        Label matriculeLabel = new Label("Matricule");
        Label firstNameLabel = new Label("Prenom *");
        Label dateNaissanceLabel = new Label("Date de  Naissance");

        userNameText = new TextField();
        lastNameText = new TextField();
        firstNameText = new TextField();
        dateNaissance = new DatePicker(LocalDate.now());
        userPasswordText = new PasswordField();
        emailText = new TextField();

        typeChoice = new ComboBox<>();
        typeChoice.getItems().addAll("Etudiant", "Enseignant");
        typeChoice.setOnAction(e -> changerType());
        typeChoice.setValue("Etudiant");

        matriculeText = new Label();
        changerType();

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

        Button signinButton = new Button("Sign Up");
        GridPane.setConstraints(signinButton, 2, 8);
        signinButton.setOnAction(e ->signAction());

        Button returnButton = new Button("Retour");
        GridPane.setConstraints(returnButton, 0, 8);
        returnButton.setOnAction(e -> moveToLoginScene());

        signInGrid.getChildren().addAll(typeChoiceLabel, matriculeLabel, lastNameLabel, firstNameLabel, emailLabel,
                dateNaissanceLabel, userNameLabel, userPasswordLabel, matriculeText, lastNameText, firstNameText,
                dateNaissance, emailText, typeChoice, userNameText, userPasswordText, signinButton, returnButton);

        signInGrid.setStyle("-fx-background-color: #b9d8ff");
        Scene signinScene = new Scene(signInGrid, 600, 333);
        window.setScene(signinScene);
    }

    private void changerType(){
        String lastMatricule = matriculeText.getText();

        updateMat(typeChoice.getValue());
        if(userNameText.getText().isEmpty() || userNameText.getText().equals(lastMatricule)){
            userNameText.setText(matriculeText.getText());
        }
    }

    private void updateMat(String type){
        if(type.equals("Etudiant")){
            matriculeInt = Apprenant.nbPersonne();
            matriculeText.setText("Etud"+matriculeInt+"#");
        }else{
            matriculeInt = Instructeur.nbPersonne();
            matriculeText.setText("ENS"+matriculeInt+"#");
        }
    }

    private void signAction(){
        if(verife()){
            if(typeChoice.getValue().equals("Etudiant")){
                Apprenant app = Apprenant.SignUp(userNameText.getText(), userPasswordText.getText(), emailText.getText(),
                        firstNameText.getText(), lastNameText.getText(), matriculeInt, dateNaissance.getValue(),
                        "isil", "L3");
                if (app != null) {
                    launchHomeScreen(app);
                }else{
                    AlertBox.displayError("Le UserName ou l'email que vous avez entrer existe deja");
                }
            }else{
                Instructeur inst = Instructeur.SignUp(userNameText.getText(), userPasswordText.getText(), emailText.getText(),
                        firstNameText.getText(), lastNameText.getText(), matriculeInt, dateNaissance.getValue(),
                        "grd", "dom");
                if (inst != null) {
                    launchHomeScreen(inst);
                }else{
                    AlertBox.displayError("Le UserName ou l'email que vous avez entrer existe deja");
                }
            }
        }
    }

    private boolean verife() {
        String redPromptText = "-fx-prompt-text-fill: red";

        if (emptyField(redPromptText, lastNameText)) return false;
        if (emptyField(redPromptText, firstNameText)) return false;
        if (emptyField(redPromptText, emailText)) return false;
        if (emptyField(redPromptText, userNameText)) return false;
        if (emptyField(redPromptText, userPasswordText)) return false;
        return true;
    }

    private boolean emptyField(String redPromptText, TextField textField) {
        if(textField.getText().isBlank()) {
            textField.setPromptText("Ce champ doit etre remplis");
            textField.setStyle(redPromptText);
            return true;
        }
        return false;
    }

    private void launchHomeScreen(Apprenant app){
        StudentUI s = new StudentUI(window, app);
    }

    private void launchHomeScreen(Instructeur inst){

    }

    private void launchHomeScreen(Admin adm){

    }
}
