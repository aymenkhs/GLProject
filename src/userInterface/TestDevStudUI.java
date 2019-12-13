package userInterface;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sources.Formation;
import sources.Test;

public class TestDevStudUI extends TestDevUI {

    TestDevStudUI(Stage window, Formation form) {
        super(window, form);
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

    void passeTest(){


    }
}
