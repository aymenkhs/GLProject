package userInterface;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class DefaultFct {

    public static GridPane defaultGrid(){
        GridPane newGrid = new GridPane();
        newGrid.setPadding(new Insets(10,10,10,10));
        newGrid.setVgap(10);
        newGrid.setHgap(10);
        newGrid.setAlignment(Pos.CENTER);
        return newGrid;
    }
}
