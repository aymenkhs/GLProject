package userInterface;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sources.Apprenant;
import sources.Cour;

public class StudentUI extends UserUI{

    /*
     * All the Attributes will be set when the scene is created, its given :Apprenant object as entry
     * */
    /*
    I'mdoing somme change to optimise it... aymen
     */

    private Apprenant app;

    private Scene studentUIScene;

    private TestDevStudUI testUI;

    //add this comme entree de constructeur Apprenant student

    public StudentUI(Stage parentStage, LogSignScene lgs, Apprenant student) {
        super(parentStage, lgs);
        app = student;
        createHomeStudent();
    }

    public void createHomeStudent(){
        initialisation(app);
        loadInfo();
        formationInnit();

        Label welcome= new Label("Espace Etudiant");
        welcome.setFont(new Font(50));
        welcome.setStyle("-fx-text-fill: #ff8e47");

        firstLevelBox.getChildren().addAll(welcome,secondLevelBox);

        BorderPane.setMargin(firstLevelBox, new Insets(12,12,12,12));
        userIntBorder.setCenter(firstLevelBox);

        studentUIScene = new Scene(userIntBorder,800,750);
        studentUIScene.getStylesheets().add(UserUI.class.getResource("../style.css").toExternalForm());
        window.setScene(studentUIScene);
    }

    private void loadInfo(){
        matriculeLabel.setText("Matricule: " + app.getMatricule());
        userNameLabel.setText("User Name: " + app.getUserName());
        emailLabel.setText("Email: " + app.getEmail());
        lastNameLabel.setText("Nom: " + app.getNom());
        firstNameLabel.setText("Prenom: " + app.getPrenom());
        DateNLabel.setText("Date de Naissance: " + app.getDateNaissance());
    }

    private void formationInnit(){
        Label formationLabel = new Label("FORMATIONS");
        formationLabel.setFont(new Font(20));
        GridPane.setConstraints(formationLabel, 0, 0, 2, 1);

        Button everyFormsButton = new Button("Toutes les Formations");
        GridPane.setConstraints(everyFormsButton, 0, 2);
        everyFormsButton.setOnAction(e-> allFormsAction());

        Button myFormsButton = new Button("Mes Formations");
        GridPane.setConstraints(myFormsButton, 1, 2);
        myFormsButton.setOnAction(e-> myFormsAction());

        Button tesDevButton = new Button("Tests/Devoirs");
        GridPane.setConstraints(tesDevButton, 0, 3);
        //tesDevButton.setOnAction(e->);

        formationGrid.getChildren().addAll(formationLabel, everyFormsButton, myFormsButton, tesDevButton);
    }

    // FORMATION
    private void setTabForm(int type){
        FormationView fv = new FormationView(app.getMatricule(), type);
        tabForm = fv.getTable();
    }



    private void allFormsAction(){
        initBorderForm();
        setTabForm(0);
        formBorder.setCenter(tabForm);

        formStage = DefaultFct.defaultStage("FORMATION", formScene);
        formStage.showAndWait();
    }

    private void myFormsAction(){
        initBorderForm();
        setTabForm(2);
        formBorder.setCenter(tabForm);

        formStage = DefaultFct.defaultStage("FORMATION", formScene);
        formStage.showAndWait();
    }

    protected void consulterFormAction(){
        listFormSel = tabForm.getSelectionModel().getSelectedItems();
        if(verifOneLineForm()){
            form = tabForm.getSelectionModel().getSelectedItem();

            testUI = new TestDevStudUI(formStage, form, form.loadApprenant(app.getMatricule()));

            rightFormBorder.setVisible(false);

            GridPane infoFormGrid = initConsulterForm();

            Button testsButton = new Button("Tests");
            testsButton.setOnAction(e->testUI.listTest(formStage.getScene()));
            Button coursButton = new Button("Cours");
            coursButton.setOnAction(e->listCourAction());
            GridPane.setConstraints(coursButton, 0, 3);
            GridPane.setConstraints(testsButton, 1, 3);
            infoFormGrid.getChildren().addAll(coursButton, testsButton);

            formBorder.setCenter(infoFormGrid);
        }
    }

    protected void listCourAction(){

        Scene listCourScene;

        BorderPane formationInfo = DefaultFct.defaultBorder();
        Text title = new Text("Titre de formation: " + form.getNomFormation());
        Text description = new Text("Description de formation: " + form.getDescription());
        VBox formStuff = DefaultFct.defaultVbox();
        formStuff.getChildren().addAll(title, description);

        ListView<String> coursList = genViews.getCours(form);

        VBox buttons = DefaultFct.defaultVbox();
        Button openCours = new Button("Ouvrir");
        openCours.setOnAction(e -> {
            Cour c = Cour.getCours(form, coursList.getSelectionModel().getSelectedItem());
            CourUI.openCours(c);
        });


        buttons.getChildren().addAll(openCours);

        formationInfo.setTop(formStuff);
        formationInfo.setCenter(coursList);
        formationInfo.setRight(buttons);

        listCourScene = new Scene(formationInfo, 600, 600);
        listCourScene.getStylesheets().add(UserUI.class.getResource("../style.css").toExternalForm());
        formStage.setScene(listCourScene);
    }
}