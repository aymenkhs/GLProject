package userInterface;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    private int nform;
    private Formation form;
    private TextArea descriptionFormText;
    private boolean bln;


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
//        addCourButton.setOnAction(e->);

        Button addFormButton = new Button("Ajouter Formations");
        GridPane.setConstraints(addFormButton, 1, 2);
        addFormButton.setOnAction(e->ajoutFormAction());

        Button addAppButton = new Button("Ajouter des Apprenants");
        GridPane.setConstraints(addAppButton, 0, 4);
        //addAppButton.setOnAction(e->);

        formationGrid.getChildren().addAll(formationLabel, myFormsButton, addFormButton ,addCourButton ,addTesDevButton
                ,addAppButton);
    }


    // Formation

    protected void initBorderForm(){
        super.initBorderForm();
        ajouterFormButton = new Button("Ajouter");
        supprimmerFormButton = new Button("Supprimmer");
        modifierFormButton = new Button("Modifier");

        ajouterFormButton.setOnAction(e->ajoutFormAction());

        rightFormBorder.getChildren().addAll(modifierFormButton, ajouterFormButton, supprimmerFormButton);
        //consulterButton.setOnAction(e->consulterFormAction());
    }

    private void myFormsAction(){
        initBorderForm();
        setTabForm(1);
        formBorder.setCenter(tabForm);
        tabForm.getSelectionModel().selectedItemProperty().addListener(observable -> System.out.println("Valeur sélectionnée: " + tabForm.getSelectionModel().getSelectedItem().getNomFormation()));
//        tabForm.getSelectionModel().getSelectedItems().addListener((InvalidationListener) observable -> consulterFormAction());
        supprimmerFormButton.setOnAction(e -> {
//            if(verifOneLineForm()) {
            deleteSelected(tabForm.getSelectionModel().getSelectedItem());
//            }

        });

        formStage = DefaultFct.defaultStage("FORMATION", formScene);
        formStage.showAndWait();
    }

    private void setTabForm(int type){
        FormationView fv = new FormationView(inst.getMatricule(), type);
        tabForm = fv.getTable();
    }

    private void ajoutFormAction(){
        Stage formAjoutStage;

        bln = false;

        BorderPane addFormBorder = new BorderPane();

        GridPane addFormGrid = DefaultFct.defaultGrid();

        Label numFormLabel = new Label("ID formation");
        nform = Formation.nbFormation();
        numFormText = new Label( nform+ "#");

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
        Button finishButton = new Button("Finish");
        Button nextButton = new Button("Next");
        bottomAddFormBorder.getChildren().addAll(nextButton , finishButton, quitButton);
        addFormBorder.setBottom(bottomAddFormBorder);

        addFormScene = new Scene(addFormBorder);

        formAjoutStage = DefaultFct.defaultStage("FORMATION", addFormScene);

        quitButton.setOnAction(e->formAjoutStage.close());
        finishButton.setOnAction(e->{
            if (bln) {
                formAjoutStage.close();
            }else{
                confirmerAjout();
                if(bln){
                    formAjoutStage.close();
                }
            }
        });
        nextButton.setOnAction(e->{
            confirmerAjout();
            if(bln){
                addFormBorder.setCenter(optionalAddForm());
                nextButton.setVisible(false);
            }
        });

        formAjoutStage.showAndWait();
    }

    private void confirmerAjout(){
        form = inst.creeFormation(nform, nomFormText.getText(), descriptionFormText.getText());

        if(form != null){
            bln = true;
        }else{
            AlertBox.displayError("Creation de La formation a echouer");
        }
    }

    private VBox optionalAddForm(){
        VBox vb = DefaultFct.defaultVbox();

        Label optAjoutCourLabel = new Label("Ajouter un cour a cette Formation : ");
        Label optAjoutAppLabel = new Label("Ajouter un apprenant a cette Formation");

        Button optAjoutCourButton = new Button("Ajouter un cour");
        Button optAjoutAppButton = new Button("Ajouter un apprenant");

        optAjoutCourButton.setOnAction(e -> CourUI.addCours(form));
        optAjoutAppButton.setOnAction(e -> System.out.println("hello there"));
        vb.getChildren().addAll(optAjoutCourLabel, optAjoutCourButton, optAjoutAppLabel,optAjoutAppButton);
        return vb;
    }

    protected void consulterFormAction(){
        listFormSel = tabForm.getSelectionModel().getSelectedItems();
        if(verifOneLineForm()){
            Formation form = tabForm.getSelectionModel().getSelectedItem();

            BorderPane formationInfo = DefaultFct.defaultBorder();
            Text title = new Text("Titre de formation: " + form.getNomFormation());
            Text description = new Text("Description de formation: " + form.getDescription());
            VBox formStuff = DefaultFct.defaultVbox();
            formStuff.getChildren().addAll(title, description);

            ListView<String> coursList = genViews.getCours(form);

            VBox buttons = DefaultFct.defaultVbox();
            Button openCours = new Button("Ouvrir");
            openCours.setOnAction(e -> openCoursAction(form, coursList.getSelectionModel().getSelectedItem()));
            Button deleteCours = new Button("Supprimer");
            deleteCours.setOnAction(e -> modifierCoursAction(form, coursList.getSelectionModel().getSelectedItem()));
            Button modifierCours = new Button("Modifier");
            modifierCours.setOnAction(e -> deleteCoursAction(form, coursList.getSelectionModel().getSelectedItem()));

            buttons.getChildren().addAll(openCours,modifierCours,deleteCours);

            formationInfo.setTop(formStuff);
            formationInfo.setCenter(coursList);
            formationInfo.setRight(buttons);
        }
    }

    private void openCoursAction(Formation form, String nomCour) {

    }

    private void modifierCoursAction(Formation form, String nomCour) {

    }

    private void deleteCoursAction(Formation form, String nomCour) {

    }

//    private void formAddCour(){
//        super.initBorderForm();
//        setTabForm(1);
//        formBorder.setCenter(tabForm);
//
//
//
//        bottomFormBorder.getChildren().addAll();
//
//        formStage = DefaultFct.defaultStage("FORMATION", formScene);
//        formStage.showAndWait();
//    }

    private void deleteSelected(Formation form) {

    }

}