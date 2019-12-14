package userInterface;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sources.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public abstract class UserUI {

    // MAIN STAGE
    private LogSignScene lgs;
    protected Stage window;

    // Layout
    protected BorderPane userIntBorder;
    protected VBox firstLevelBox, thirdLevelBox, profileVbox;
    protected HBox secondLevelBox, topBorder;
    protected GridPane formationGrid, sondageGrid;
    //protected GridPane wikiGrid; !!!???

    // Image that represents pdp
    protected ImageView pfpView;
    // Labels
    protected Label userNameLabel;
    protected Label firstNameLabel;
    protected Label lastNameLabel;
    protected Label matriculeLabel;
    protected Label emailLabel;
    protected Label DateNLabel;
    // buttons
    protected Button editProfilButton;


    // FORMATION STAGE

    protected Stage formStage;
    protected Scene formScene;

    protected TableView<Formation> tabForm;
    protected ObservableList<Formation> listFormSel;

    //layout
    protected BorderPane formBorder;
    protected HBox bottomFormBorder;
    protected VBox rightFormBorder;

    //Buttons
    protected Button consulterFormButton, ajouterFormButton, supprimmerFormButton ,modifierFormButton;

    //Others
    protected Formation form;

    UserUI(Stage parentStage, LogSignScene lgs){
        this.window = parentStage;
        this.lgs = lgs;
    }

    // HOME

    protected void initialisation(Personne per){
        userIntBorder = new BorderPane();

        // first the hbox in the top of the window

        topBorder = DefaultFct.defaultHbox(Pos.TOP_RIGHT);

        Button optionButton = new Button("Options");
        //optionButton.setOnAction(e->);

        Button disconnectButton = new Button("Se Deconnecter");
        disconnectButton.setOnAction(e->{
            lgs.createLoginScene();
            lgs.moveToLoginScene();
        });

        topBorder.getChildren().addAll(optionButton,disconnectButton);
        BorderPane.setMargin(topBorder, new Insets(12,12,12,12));
        userIntBorder.setTop(topBorder);

        // the rest of the initialisations
        firstLevelBox = DefaultFct.defaultVbox();
        secondLevelBox = DefaultFct.defaultHbox();
        profileVbox = DefaultFct.defaultVbox();
        profilInnit();

        thirdLevelBox = DefaultFct.defaultVbox(30);
        formationGrid = DefaultFct.defaultGrid();
        sondageGrid = DefaultFct.defaultGrid();
        sondageInnit(per);
    }

    protected void profilInnit(){
        userNameLabel = new Label();
        firstNameLabel = new Label();
        lastNameLabel = new Label();
        matriculeLabel = new Label();
        emailLabel = new Label();
        DateNLabel = new Label();

        pfpView = new ImageView();
        pfpView.setFitHeight(50);
        pfpView.setPreserveRatio(true);
        pfpView.setStyle("-fx-background-color: red");

        editProfilButton = new Button("Edit");

        profileVbox.getChildren().addAll(pfpView ,userNameLabel, firstNameLabel, lastNameLabel, matriculeLabel,
                emailLabel, DateNLabel, editProfilButton);

        secondLevelBox.getChildren().add(profileVbox);
    }

    protected void sondageInnit(Personne per){
        Label sondageLabel = new Label("SONDAGES");
        sondageLabel.setFont(new Font(20));
        GridPane.setConstraints(sondageLabel, 0, 0, 2, 1);

        Button everyPollsButton = new Button("Tous les Sondages");
        GridPane.setConstraints(everyPollsButton, 0, 2);
        everyPollsButton.setOnAction(e-> showPolls(0, per.getUserName()));

        Button myPollsButton = new Button("Mes Sondages");
        GridPane.setConstraints(myPollsButton, 0, 3);
        myPollsButton.setOnAction(e-> showPolls(1, per.getUserName()));

        Button answeredPollsButton = new Button("Sondages Repondu");
        GridPane.setConstraints(answeredPollsButton, 1, 2);
        answeredPollsButton.setOnAction(e-> showPolls(2, per.getUserName()));

        Button addPollsButton = new Button("Ajouter un Sondages");
        GridPane.setConstraints(addPollsButton, 1, 3);
        //addPollsButton.setOnAction(e->);

        sondageGrid.getChildren().addAll(sondageLabel, everyPollsButton, myPollsButton, answeredPollsButton, addPollsButton);

        thirdLevelBox.getChildren().addAll(formationGrid,sondageGrid);

        secondLevelBox.getChildren().add(thirdLevelBox);
    }


    // FORMATION


    protected void initBorderForm(){
        formBorder = new BorderPane();

        bottomFormBorder = DefaultFct.defaultHbox(Pos.BOTTOM_LEFT);
        Button quit = new Button("Quiter");
        quit.setOnAction(e->formStage.close());
        bottomFormBorder.getChildren().add(quit);
        BorderPane.setMargin(bottomFormBorder, new Insets(12,12,12,12));
        formBorder.setBottom(bottomFormBorder);

        rightFormBorder = DefaultFct.defaultVbox();
        consulterFormButton = new Button("Consulter");
        rightFormBorder.getChildren().add(consulterFormButton);

        BorderPane.setMargin(rightFormBorder, new Insets(12,12,12,12));
        formBorder.setRight(rightFormBorder);

        consulterFormButton.setOnAction(e->consulterFormAction());

        formScene = new Scene(formBorder, 600, 600);
    }


    protected boolean verifOneLineForm(){
        if (listFormSel.size()>1){
            AlertBox.displayError("What make you think you can See or modify multiples Formation????!!!!");
            return false;
        }else if(listFormSel.isEmpty()){
            return false;
        }
        return true;
    }

    protected abstract void consulterFormAction();

    protected GridPane initConsulterForm(){
        // we call this methode to initialise some layouts that all users have in  common

        GridPane infoFormGrid = DefaultFct.defaultGrid();
        Text titleText = new Text("Titre de formation: " + form.getNomFormation());
        Label nomInstLabel = new Label("Createur de La Formation : " + form.getInstName());
        Text descriptionText = new Text("Description de formation: " + form.getDescription());

        GridPane.setConstraints(titleText, 0, 0, 2, 1);
        GridPane.setConstraints(nomInstLabel, 0, 1, 2, 1);
        GridPane.setConstraints(descriptionText, 0, 2, 2, 1);

        infoFormGrid.getChildren().addAll(titleText, descriptionText);
        return infoFormGrid;
    }


    // SONDAGE


    private static void showPolls(int option, String userName){ // 0: everything, 1: mes sondages 2: sondages repondus

        Stage pollStage = new Stage();

        BorderPane sondages = DefaultFct.defaultBorder();
        sondages.setStyle("-fx-background-color: #b9d8ff");

        SondageView view = new SondageView(option, userName);

        TableView<Sondage> sondageTable = view.getSondageTable();
        String text = "";


        Text title =  new Text();
        title.setFont(new Font(20));
        title.setStyle("-fx-text-fill: #ff8e47");

        Button quit = new Button("Quitter");
        quit.setMinWidth(140);
        quit.setOnAction(e -> pollStage.close());

        Button afficherResultat = new Button("Resultat de Sondage");
        afficherResultat.setMinWidth(140);
        afficherResultat.setOnAction(e -> showResults(sondageTable.getSelectionModel().getSelectedItem()));

        Button ajouter = new Button("Ajouter un Sondage");
        ajouter.setMinWidth(140);
        ajouter.setOnAction(e -> {
            addSondage(userName);
//            pollStage.close();
//            showPolls(option, userName);
            System.out.println("Why isnt this working");
        });

        Button repondre = new Button("Repondre Au Sondage");
        repondre.setMinWidth(140);
        repondre.setOnAction(e -> {
            if(sondageTable.getSelectionModel().getSelectedItems().size() == 1) {
                repondreAuSondage(sondageTable.getSelectionModel().getSelectedItem(), userName);
            }
        });

        Button supprimer = new Button("Supprimer le Sondage");
        supprimer.setMinWidth(140);
        supprimer.setOnAction(e -> {
            Sondage s =  sondageTable.getSelectionModel().getSelectedItem();
            if(deleteSondage(s, userName)) {
                try {
                    sondageTable.getItems().remove(s);
                }catch (NoSuchElementException nse) {
                    nse.printStackTrace();
                }
            }else {
                AlertBox.displayError("Vous n'avez pas le droit de supprimer ce sondage.");
            }

        });

        Button unAnswer = new Button("Supprimer reponse");
        unAnswer.setMinWidth(140);
        unAnswer.setOnAction(e -> {
            Sondage s = sondageTable.getSelectionModel().getSelectedItem();
            if(s.checkIfAnswered(userName)) {
                if (s.deleteAnswer(userName)) {
                    AlertBox.display("Supprimé!", "Votre reponse au sondage a été supprimée");
//                    pollStage.close();
//                    showPolls(option, userName);
                }else {
                    AlertBox.displayError("Unkown SQL error");
                }
            }else {
                AlertBox.displayError("Il faut repondre au sondage afin de supprimer la reponse");
            }
        });

        VBox buttonVbox = DefaultFct.defaultVbox();
        buttonVbox.getChildren().addAll(afficherResultat, ajouter, repondre, supprimer, unAnswer, quit);

        switch (option) {
            case 0:
                text = "Tous les sondages";
                break;
            case 1:
                text = "Mes Sondages";
                break;
            case 2:
                text = "Sondages Repondus";
                ajouter.setManaged(false);
                break;
        }
        title.setText(text);


        sondages.setTop(title);
        sondages.setCenter(sondageTable);
        sondages.setRight(buttonVbox);

        Scene pollsScene = new Scene(sondages, 640,400);
        pollStage.setScene(pollsScene);
        pollStage.showAndWait();

    }

    private static void showResults(Sondage s) {
        Stage resultStage = new Stage();
        BorderPane resultPane = DefaultFct.defaultBorder();

        Label descriptionLabel = new Label("Description: ");
        Label description = new Label();
        description.setText(s.getDescription());

        VBox descriptionVbox = DefaultFct.defaultVbox();
        descriptionVbox.getChildren().addAll(descriptionLabel, description);

        Label results = s.choicesInLabel();

        Button quit = new Button("Fermer");
        quit.setOnAction(e -> resultStage.close());

        resultPane.setTop(descriptionVbox);
        resultPane.setCenter(results);
        resultPane.setBottom(quit);

        Scene resultsScene = new Scene(resultPane, 300,300);
        resultStage.setScene(resultsScene);
        resultStage.showAndWait();

    }

    private static void addSondage(String userName) {
        Stage addSondageStage = new Stage();

        BorderPane addSondagePane = DefaultFct.defaultBorder();

        Label descriptionLabel = new Label("Description: ");
        TextField description = new TextField();
        description.setMinHeight(40);
        description.setPromptText("Description de sondage");

        VBox descriptionVbox = DefaultFct.defaultVbox();
        descriptionVbox.getChildren().addAll(descriptionLabel, description);

        Label option1Label = new Label("Option 1:");
        TextField option1 = new TextField();
        option1.setMinWidth(200);
        HBox hbox1 = DefaultFct.defaultHbox();
        hbox1.getChildren().addAll(option1Label, option1);

        Label option2Label = new Label("Option 2:");
        TextField option2 = new TextField();
        option2.setMinWidth(200);
        HBox hbox2 = DefaultFct.defaultHbox();
        hbox2.getChildren().addAll(option2Label, option2);

        Label option3Label = new Label("Option 3:");
        TextField option3 = new TextField();
        option3.setMinWidth(200);
        HBox hbox3 = DefaultFct.defaultHbox();
        hbox3.getChildren().addAll(option3Label, option3);

        Label option4Label = new Label("Option 4:");
        TextField option4 = new TextField();
        option4.setMinWidth(200);
        HBox hbox4 = DefaultFct.defaultHbox();
        hbox4.getChildren().addAll(option4Label, option4);

        ArrayList<TextField> options= new ArrayList<>();
        options.add(option1);
        options.add(option2);
        options.add(option3);
        options.add(option4);

        VBox sondageVbox = DefaultFct.defaultVbox();
        sondageVbox.getChildren().addAll(descriptionVbox, hbox1, hbox2, hbox3, hbox4);

        Button ajouter = new Button("Ajouter");
        ajouter.setOnAction(e -> {
            if(description.getText().isBlank()) {
                description.setPromptText("Ajouter une description!");
                description.setStyle("-fx-text-fill: red");
            }else {
                int emptyFieldCount = 0;
                ArrayList<String> validOptions = new ArrayList<>();
                for (TextField txt : options) {
                    if (txt.getText().isBlank()) {
                        emptyFieldCount++;
                    }else {
                        validOptions.add(txt.getText());
                    }
                }

                if (emptyFieldCount >= 3) {
                    for (TextField txt : options) {
                        if (txt.getText().isBlank()) {
                            txt.setPromptText("Ajouter au moins 2 options!");
                            txt.setStyle("-fx-text-fill: red");
                        }
                    }
                } else {
                    Sondage sondage = new Sondage(Sondage.nbSondage(),
                            userName, description.getText(), "All");
                    int i = 0;
                    for(String choice : validOptions) {
                        sondage.addChoix(i, choice);
                        i++;
                    }

                    Sondage.createSondage(Sondage.nbSondage(),
                            userName, description.getText(), "All");

                    AlertBox.display("Sondages ajouté", "Le sondage a été ajouté avec succés");
                    addSondageStage.close();
                }
            }
        });

        Button annuler = new Button("Quitter");
        annuler.setOnAction(e -> addSondageStage.close());

        HBox buttonHbox = DefaultFct.defaultHbox();
        buttonHbox.getChildren().addAll(ajouter, annuler);

        Text text = new Text("Ajout de sondage");
        text.setFont(new Font(20));

        addSondagePane.setTop(text);
        addSondagePane.setBottom(buttonHbox);
        addSondagePane.setCenter(sondageVbox);

        Scene addSondageScene = new Scene(addSondagePane,430,300);
        addSondageStage.setScene(addSondageScene);
        addSondageStage.showAndWait();
    }

    private static void repondreAuSondage(Sondage s, String userName) {
        Stage repondSondageStage = new Stage();
        BorderPane repondSondagePane = DefaultFct.defaultBorder();

        Label descriptionLabel = new Label("Description: ");
        Label description = new Label();
        description.setText(s.getDescription());
        VBox descriptionVbox = DefaultFct.defaultVbox();
        descriptionVbox.getChildren().addAll(descriptionLabel, description);

        Label choix = new Label("Liste des choix: ");
        ChoiceBox<String> choices = new ChoiceBox<>();
        choices.getItems().add("");
        choices.setMinWidth(150);

        ArrayList<String> listChoix = s.loadChoix();
        for(String str : listChoix) {
            choices.getItems().add(str);
        }

        HBox choiceHbox = DefaultFct.defaultHbox();
        choiceHbox.getChildren().addAll(choix, choices);

        Label results = s.choicesInLabel();

        VBox infoVbox = DefaultFct.defaultVbox();
        infoVbox.getChildren().addAll(results, choiceHbox);

        Button answer = new Button("Repondre");
        answer.setOnAction(e -> {
            if(!choices.getValue().isBlank()) {
                if (s.checkIfAnswered(userName)) {
                    s.deleteAnswer(userName);
                }

                s.answer(userName, choices.getValue());
                AlertBox.display("Sondage Repondu", "     Reponse enregistré     ");
                repondSondageStage.close();
            }
        });
        Button quit = new Button("Quitter");
        quit.setOnAction(e -> repondSondageStage.close());

        HBox buttonHbox = DefaultFct.defaultHbox();
        buttonHbox.getChildren().addAll(answer, quit);

        repondSondagePane.setTop(descriptionVbox);
        repondSondagePane.setCenter(infoVbox);
        repondSondagePane.setBottom(buttonHbox);

        Scene repondSondageScene = new Scene(repondSondagePane, 400, 270);
        repondSondageStage.setScene(repondSondageScene);
        repondSondageStage.showAndWait();
    }

    private static boolean deleteSondage(Sondage s, String userName) {
        String type = User.userType(userName);
        String nom = "";
        switch (type.toLowerCase()) {
            case "etudiant":
                Apprenant app = Apprenant.LoadApprenant(userName);
                nom = app.getNom() + " " +app.getPrenom();
                break;
            case "enseignant":
                Instructeur inst = Instructeur.LoadInstructeur(userName);
                nom = inst.getNom() + " " +inst.getPrenom();
                break;
        }
        if(!s.getNomCreateur().equalsIgnoreCase(nom)) {
            System.out.println(s.getNomCreateur());
            System.out.println(nom);
            return false;
        }
        return s.delete();
    }
}