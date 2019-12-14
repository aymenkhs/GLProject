package userInterface;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sources.Formation;
import sources.Historique;
import sources.Test;

public class TestDevStudUI extends TestDevUI {

    private Stage passerTestStage;

    private Test test;
    private Historique membre;

    TestDevStudUI(Stage window, Formation form, Historique hs) {
        super(window, form);
        this.membre = hs;
    }

    void listTest(Scene scene){

        Scene listTestScene;
        BorderPane listTestBorder = DefaultFct.defaultBorder();

        ListView<Test> testsList = genViews.getTests(form);

        HBox bottomBorder = DefaultFct.defaultHbox(Pos.BOTTOM_LEFT);
        Button returnButton = new Button("Retour");
        Button passerTest = new Button("Passer Test");
        returnButton.setOnAction(e-> window.setScene(scene));
        bottomBorder.getChildren().add(returnButton);

        listTestBorder.setCenter(testsList);
        listTestBorder.setBottom(bottomBorder);

        listTestScene = new Scene(listTestBorder);
        window.setScene(listTestScene);
    }

    void passeTest(Test t){
        test = t;
        int etat = membre.isHePassedIt(test);
        if(etat == 1){
            AlertBox.displayError("Vous avez deja valider se Test");
        }else if(etat == 0){
            // started the tes but did'nt complete it
        }else {
            GridPane grid = DefaultFct.defaultGrid();

            Label nForm = new Label("Formation n° " + form.getNumFormation());
            Label nTest = new Label("Test n° " + test.getNumTest());
            Label message = new Label("Voullez vous vraiment commencer se test");
            Label warning = new Label("Attention : ceci est une action irreversible une fois le test commencer vous ne" +
                    " pourrez que le valider \n(ou le metre en pause pour le continuer plus tard) mais en aucun cas le repasser");
            Button conf = new Button("Passer test");

            GridPane.setConstraints(nForm, 0,0);
            GridPane.setConstraints(nTest, 1,0);
            GridPane.setConstraints(message, 0,1, 2,1);
            GridPane.setConstraints(warning, 0,2, 3, 1);
            GridPane.setConstraints(conf, 1,3);

            grid.getChildren().addAll(nForm,nTest,message,warning,conf);

            Scene sc = new Scene(grid); // color and size please
            Stage stg = DefaultFct.defaultStage("Confirmation Test",sc);

            conf.setOnAction(e->{
                stg.close();
                membre.passerTest(test);
                // start doing the test
            });

            stg.show();
        }
    }

    private void initQuestion(){

    }
}
