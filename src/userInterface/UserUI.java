package userInterface;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UserUI {

    protected Stage window;

    // Layout
    protected BorderPane userIntBorder;
    protected VBox firstLevelBox;
    protected HBox secondLevelBox;
    protected VBox thirdLevelBox;
    protected HBox topBorder;
    protected VBox profileVbox;
    protected GridPane formationGrid;
    protected GridPane sondageGrid;
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

    protected void initialisation(){
        userIntBorder = new BorderPane();

        // first the hbox in the top of the window

        topBorder = DefaultFct.defaultHbox(Pos.TOP_RIGHT);

        Button optionButton = new Button("Options");
        //optionButton.setOnAction(e->);

        Button disconnectButton = new Button("Se Deconnecter");
        //disconnectButton.setOnAction(e->);

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
        sondageInnit();
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

    protected void sondageInnit(){
        Label sondageLabel = new Label("SONDAGES");
        sondageLabel.setFont(new Font(20));
        GridPane.setConstraints(sondageLabel, 0, 0, 2, 1);

        Button everyPollsButton = new Button("Tous les Sondages");
        GridPane.setConstraints(everyPollsButton, 0, 2);
        //everyPollsButton.setOnAction(e->);

        Button myPollsButton = new Button("Mes Sondages");
        GridPane.setConstraints(myPollsButton, 0, 3);
        //myPollsButton.setOnAction(e->);

        Button answeredPollsButton = new Button("Sondages Repondu");
        GridPane.setConstraints(answeredPollsButton, 1, 2);
        //answredPollsButton.setOnAction(e->);

        Button addPollsButton = new Button("Ajouter un Sondages");
        GridPane.setConstraints(addPollsButton, 1, 3);
        //addPollsButton.setOnAction(e->);

        sondageGrid.getChildren().addAll(sondageLabel, everyPollsButton, myPollsButton, answeredPollsButton, addPollsButton);

        thirdLevelBox.getChildren().addAll(formationGrid,sondageGrid);

        secondLevelBox.getChildren().add(thirdLevelBox);
    }

}