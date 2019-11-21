import dataBase.Jdbc;
import javafx.application.Application;
import javafx.stage.Stage;
import sources.User;
import userInterface.LogSignScene;

import java.sql.SQLException;

public class Main extends Application {

    Jdbc db;

    public static void main(String[] args) {
            launch(args);
        }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Computer Science Departement Social Learning Platform ");

        try {
            db = new Jdbc(); // creation de la base de donnes
            User.setDataBase(db);
            //System.out.println(User.UserNameExist("truc"));
        } catch (SQLException e) {
            //System.out.println(e);
            System.out.println("erreur de chargement de la base de donnes");
        }

        LogSignScene ls = new LogSignScene(primaryStage);
        ls.moveToLoginScene();


        primaryStage.show();
    }
}
