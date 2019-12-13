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
import javafx.stage.Stage;
import sources.Formation;
import sources.Instructeur;

import java.time.format.DateTimeFormatter;

public class TeacherUI extends UserUI{

    private Instructeur inst;

    private TestDevEnsUI testUI;

    private Scene teacherUIScene;


    //Formations

    //creation Formation
    private Scene addFormScene;
    private TextField nomFormText;
    private Label numFormText;
    private int nform;
    private TextArea descriptionFormText;
    private boolean bln;

    //menu Formation
    private ComboBox<String> inFormCombo, actionFormCombo; //Cours, Apprenants, Tests, Devoirs


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
            form = tabForm.getSelectionModel().getSelectedItem();

            testUI = new TestDevEnsUI(window, form);

            rightFormBorder.setVisible(false);

            VBox topLevel = DefaultFct.defaultVbox();
            GridPane infoFormGrid = initConsulterForm();

            Button supprimerButton = new Button("Supprimer Formation");
            Button modifierButton = new Button("Modifier Formation");
            GridPane.setConstraints(supprimerButton, 0, 3);
            GridPane.setConstraints(modifierButton, 1, 3);
            infoFormGrid.getChildren().addAll(supprimerButton, modifierButton);

            GridPane formAction = DefaultFct.defaultGrid();
            Label lab1 = new Label("Action :");
            actionFormCombo = new ComboBox<>();
            actionFormCombo.getItems().addAll("Ajouter", "Afficher Liste", "Supprimer");
            actionFormCombo.setValue("Afficher Liste");
            Label lab2 = new Label("Sur : ");
            inFormCombo = new ComboBox<>();
            inFormCombo.getItems().addAll("Apprenants", "Cours", "Devoirs", "Tests");
            inFormCombo.setValue("Cours");
            Button actionBtn = new Button("Go");
            actionBtn.setOnAction(e->actionRapConsulter());

            GridPane.setConstraints(lab1,0,0);
            GridPane.setConstraints(actionFormCombo,1,0);
            GridPane.setConstraints(lab2,0,1);
            GridPane.setConstraints(inFormCombo,1,1);
            GridPane.setConstraints(actionBtn,0,2);
            formAction.getChildren().addAll(lab1, actionFormCombo ,lab2 ,inFormCombo, actionBtn);

            topLevel.getChildren().addAll(infoFormGrid, formAction);
            formBorder.setCenter(topLevel);
        }
    }

    private void actionRapConsulter(){
        switch (inFormCombo.getValue()){
            case "Apprenants":
                switch (actionFormCombo.getValue()){
                    case "Ajouter":
                        break;
                    case "Afficher Liste":
                        break;
                    case "Supprimer":
                        System.out.println("nothing for now");
                        break;
                }
                break;
            case "Cours":
                switch (actionFormCombo.getValue()){
                    case "Ajouter":
                        CourUI.addCours(form);
                        break;
                    case "Afficher Liste":
                        listCourAction();
                        break;
                    case "Supprimer":
                        System.out.println("nothing for now");
                        break;
                }
                break;
            case "Tests":
                switch (actionFormCombo.getValue()){
                    case "Ajouter":
                        testUI.addTest(formStage.getScene());
                        break;
                    case "Afficher Liste":
                        testUI.listTest(formStage.getScene());
                        break;
                    case "Supprimer":
                        System.out.println("nothing for now");
                        break;
                }
                break;
            case "Devoirs":
                System.out.println("nothing for now");
                break;
        }
    }

    protected void listCourAction(){

        Scene listCourScene;
        BorderPane listCourBorder = DefaultFct.defaultBorder();

        coursList = genViews.getCours(form);

        VBox buttons = DefaultFct.defaultVbox();
        Button openCours = new Button("Ouvrir");
        Button deleteCours = new Button("Supprimer");
        Button modifierCours = new Button("Modifier");
        buttons.getChildren().addAll(openCours,modifierCours,deleteCours);

        listCourBorder.setCenter(coursList);
        listCourBorder.setRight(buttons);

        openCours.setOnAction(e -> {
            listCourSel = coursList.getSelectionModel().getSelectedItems();
            if (verifOneLineCour()){
                CourUI.openCours(listCourSel.get(0));
            }
        });
        modifierCours.setOnAction(e -> {
            listCourSel = coursList.getSelectionModel().getSelectedItems();
            if (verifOneLineCour()){
                CourUI.modifierCours(listCourSel.get(0));
            }
        });
        deleteCours.setOnAction(e -> {

        });

        listCourScene = new Scene(listCourBorder, 500, 500);
        formStage.setScene(listCourScene);
    }


    private void deleteSelected(Formation form) {

    }

}