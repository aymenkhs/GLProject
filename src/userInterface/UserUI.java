package userInterface;

import dataBase.Jdbc;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sources.Formation;
import sources.Personne;
import sources.Sondage;

public abstract class UserUI {
    // Jdbc
    Jdbc db;
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

    UserUI(Stage parentStage, LogSignScene lgs){
        this.window = parentStage;
        this.lgs = lgs;
    }

    // HOME

    protected void initialisation(Personne per){
        userIntBorder = new BorderPane();
        // first the hbox in the top of the window
        db = new Jdbc();
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
//        quit.setOnAction(e -> Stage.close());
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


    // SONDAGE


    public static void showPolls(int option, String userName){

        SondageView view = new SondageView(option, userName);

        TableView<Sondage> sondageTable = view.getSondageTable();
        String text = "";
        switch (option) {
            case 0:
                text = "Tous les sondages";
                break;
            case 1:
                text = "Mes Sondages";
                break;
            case 2:
                text = "Sondages repondus";
                break;
        }

        Text title =  new Text(text);
        title.setFont(new Font(20));
        title.setStyle("-fx-text-fill: #ff8e47");

        HBox buttonHbox = new HBox();

        BorderPane sondages = new BorderPane();
        sondages.setStyle("-fx-background-color: #b9d8ff");

        sondages.setTop(title);
        sondages.setCenter(sondageTable);
        sondages.setBottom(buttonHbox);

        Scene sondScene = new Scene(sondages, 400,400);
        Stage sondStage = new Stage();
        sondStage.setScene(sondScene);
        sondStage.showAndWait();
    }



}