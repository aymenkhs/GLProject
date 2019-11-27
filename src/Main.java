import dataBase.Jdbc;
import javafx.application.Application;
import javafx.stage.Stage;
import sources.Formation;
import sources.Sondage;
import sources.User;
import userInterface.LogSignScene;

public class Main extends Application {

    Jdbc db;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Computer Science Departement Social Learning Platform ");

        db = new Jdbc(); // creation de la base de donnes
        Formation.setDataBase(db);
        User.setDataBase(db);
        Sondage.setDataBase(db);

        LogSignScene ls = new LogSignScene(primaryStage);
        ls.moveToLoginScene();

        primaryStage.show();
    }
}
