package userInterface;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sources.Apprenant;

public class StudentUI {
    /*
    * All the Attributes will be set when the scene is created, its given :Apprenant object as entry
    * */
    ImageView pfpView;
    //Labels
    Label welcome= new Label("Espace Etudiant");
    Label userName = new Label();
    Label firstName = new Label();
    Label lastName = new Label();
    Label matricule = new Label();
    Label email = new Label();
    VBox profileVbox = new VBox();

    Label form_coursLabel = new Label("Formations et Cours: ");
    Label quizLabel = new Label("Quizs: ");
    Label devoirsLabel = new Label("Devoirs: ");
    Label pollsLabel = new Label("Sondages: ");
    VBox labelVbox = new VBox();

    //Buttons
    Button editProfile = new Button("Edit Profiel");
    Button cours = new Button("Cours");  // the name may change down the line
    Button quiz = new Button("Quiz");
    Button devoirs = new Button("Devoirs");
    Button polls = new Button("Sondages");
    VBox buttonVbox = new VBox();

    //add this comme entree de constructeur Apprenant student
    public StudentUI(Stage parentStage, Apprenant student) {

        //Image pfp = new Image("C:/Users/abdou/Pictures/Discord shit/mecgacucu.png"); //this will be replaces with the path for the pfp later which is fetched from the database
        pfpView = new ImageView();
        pfpView.setFitHeight(50);
        pfpView.setPreserveRatio(true);
        pfpView.setStyle("-fx-background-color: red");
        loadInfo(student);
        profileVbox.getChildren().addAll(pfpView, userName, firstName, lastName, matricule, email, editProfile);
        profileVbox.setSpacing(15);


        labelVbox.getChildren().addAll(form_coursLabel, quizLabel, devoirsLabel, pollsLabel);
        labelVbox.setPadding(new Insets(20,0,0,35));
        labelVbox.setSpacing(25);

        buttonVbox.getChildren().addAll(cours, quiz, devoirs, polls);
        buttonVbox.setPadding(new Insets(20,0,0,0));
        buttonVbox.setSpacing(15);
        //making buttons work
        //cours.setOnAction(e -> new Formation());

        HBox studentHbox = new HBox();
        studentHbox.getChildren().addAll(profileVbox, labelVbox, buttonVbox);
        studentHbox.setPadding(new Insets(30));

        welcome.setFont(new Font(20));
        welcome.setPadding(new Insets(0,0,0,110));
        welcome.setStyle("-fx-text-fill: #ff8e47");
        VBox finalVbox = new VBox();


        finalVbox.getChildren().addAll(welcome, studentHbox);
        finalVbox.setSpacing(20);
        finalVbox.setStyle("-fx-background-color: #b9d8ff");

        Scene studentHomeScene = new Scene(finalVbox,450,340);

        parentStage.setScene(studentHomeScene);

    }

    private void loadInfo(Apprenant student){
        matricule.setText("Matricule: " + student.getMatricule());
        userName.setText("Pseudo: " + student.getUserName());
        email.setText("Email: " + student.getEmail());
        lastName.setText("Nom: " + student.getNom());
        firstName.setText("Prenom: " + student.getPrenom());
    }
}
