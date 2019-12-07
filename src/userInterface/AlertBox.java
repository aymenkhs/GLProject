package userInterface;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlertBox {

    private static Stage window;

    public static void display(String title, String message){
        window = new Stage();
        window.setTitle(title);

        Label mes = new Label(message);
        Button btn = new Button("ok");
        btn.setOnAction(e -> window.close());

        VBox layout = DefaultFct.defaultVbox();
        VBox.setMargin(layout,new Insets(12,12,12,12));

        layout.getChildren().addAll(mes, btn);

        window.setScene(new Scene(layout));
        window.showAndWait();
    }

    public static void displayError(String message){
        display("Erreur", message);
    }
}
