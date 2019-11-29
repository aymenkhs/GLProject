package userInterface;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sources.Formation;
import sources.Instructeur;

import java.time.format.DateTimeFormatter;

public class TeacherUI extends UserUI{

    private Instructeur inst;

    private Scene teacherUIScene;


    //Formations
    //creation Formation
    private Scene addFormScene;
    private TextField nomFormText;
    private Label numFormText;
    private TextArea descriptionFormText;

    public TeacherUI(Stage parentStage, LogSignScene lgs,Instructeur teacher) {
        super(parentStage, lgs);
        inst = teacher;
        createHomeTeacher();
    }

    public void createHomeTeacher(){
        initialisation(inst);
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
        myFormsButton.setOnAction(e->myFormsAction());

        Button addTesDevButton = new Button("Ajouter Tests/Devoir");
        GridPane.setConstraints(addTesDevButton, 1, 3);
        //addTesDevButton.setOnAction(e->);

        Button addCourButton = new Button("Ajouter Cour");
        GridPane.setConstraints(addCourButton, 0, 3);
        //addCourButton.setOnAction(e->);

        Button addFormButton = new Button("Ajouter Formations");
        GridPane.setConstraints(addFormButton, 1, 2);
        addFormButton.setOnAction(e->AjoutFormAction());

        Button addAppButton = new Button("Ajouter des Apprenants");
        GridPane.setConstraints(addAppButton, 0, 4);
        //addAppButton.setOnAction(e->);

        formationGrid.getChildren().addAll(formationLabel, myFormsButton, addFormButton ,addCourButton ,addTesDevButton
            ,addAppButton);
    }

    protected void initBorderForm(){
        super.initBorderForm();
        ajouterFormButton = new Button("Ajouter");
        supprimmerFormButton = new Button("Supprimmer");
        modifierFormButton = new Button("Modifier");

        rightFormBorder.getChildren().addAll(modifierFormButton, ajouterFormButton, supprimmerFormButton);
        //consulterButton.setOnAction(e->consulterFormAction());
    }

    private void setTabForm(int type){
        FormationView fv = new FormationView(inst.getMatricule(), type);
        tabForm = fv.getTable();
    }

    private void AjoutFormAction(){
        BorderPane addFormBorder = new BorderPane();

        GridPane addFormGrid = DefaultFct.defaultGrid();

        Label numFormLabel = new Label("ID formation");
        numFormText = new Label(Formation.nbFormation() + "#");

        Label nomFormLabel = new Label("Nom de La formation");
        nomFormText = new TextField();

        Label descriptionLabel = new Label("Description de la formation");
        descriptionFormText = new TextArea();

        GridPane.setConstraints(numFormLabel, 0, 0);
        GridPane.setConstraints(nomFormLabel, 0, 1);
        GridPane.setConstraints(descriptionLabel, 0, 2);
        GridPane.setConstraints(numFormText, 1, 0);
        GridPane.setConstraints(nomFormText, 1, 1);
        GridPane.setConstraints(descriptionFormText, 1, 2);

        addFormGrid.getChildren().addAll(numFormLabel, numFormText, nomFormLabel, nomFormText, descriptionLabel,
                descriptionFormText);

        addFormBorder.setCenter(addFormGrid);

        HBox bottomAddFormBorder = DefaultFct.defaultHbox(Pos.BOTTOM_RIGHT);

        Button quitButton = new Button("Quiter");
        //quit.setOnAction();
        Button finishButton = new Button("Finish");
        Button nextButton = new Button("Next");

        bottomAddFormBorder.getChildren().addAll(nextButton , finishButton, quitButton);
        addFormBorder.setBottom(bottomAddFormBorder);

        addFormScene = new Scene(addFormBorder);

        formStage = DefaultFct.defaultStage("FORMATION", addFormScene);
        formStage.showAndWait();
    }

    private void myFormsAction(){
        initBorderForm();
        setTabForm(1);
        formBorder.setCenter(tabForm);

        formStage = DefaultFct.defaultStage("FORMATION", formScene);
        formStage.showAndWait();
    }

}
