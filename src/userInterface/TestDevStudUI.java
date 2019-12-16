package userInterface;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sources.*;

import java.util.ArrayList;
import java.util.HashMap;

public class TestDevStudUI extends TestDevUI {

    private Stage passerTestStage;

    private Test test;
    private Historique membre;

    private Scene listQstScene;

    private ListView<Question> list;

    private HashMap<Question, Scene> listScene;

    private RadioButton choix[];

    TestDevStudUI(Stage window, Formation form, Historique hs) {
        super(window, form);
        this.membre = hs;
    }

    void listTest(Scene scene){

        Scene listTestScene;
        BorderPane listTestBorder = DefaultFct.defaultBorder();

        ListView<Test> testsList = genViews.getValidateTests(form);

        HBox bottomBorder = DefaultFct.defaultHbox(Pos.BOTTOM_LEFT);
        Button returnButton = new Button("Retour");
        Button passerTest = new Button("Passer Test");
        returnButton.setOnAction(e-> window.setScene(scene));
        bottomBorder.getChildren().addAll(returnButton, passerTest);

        listTestBorder.setCenter(testsList);
        listTestBorder.setBottom(bottomBorder);

        passerTest.setOnAction(e->{
            Test t = testsList.getSelectionModel().getSelectedItem();
            if(t != null){
                passeTest(t);
            }
        });

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
                listScene = new HashMap<>();
                listQuestion(test);
            });

            stg.show();
        }
    }

    private void listQuestion(Test t){
        VBox vb = DefaultFct.defaultVbox();
        vb.setPadding(new Insets(15,15,15,15));

        Label rep =  new Label("Choisisser a  quelle Question repondre");
        rep.setFont(new Font(30));
        rep.setStyle("-fx-text-fill: #ff8e47"); // change the color

        list = genViews.getQuestions(t);
        list.setOnMouseClicked(mouseClickedEvent->{
            if(mouseClickedEvent.getButton().equals(MouseButton.PRIMARY) && mouseClickedEvent.getClickCount() == 2){
                goToQuestion();
            }
        });

        Button finishTest = new Button("Deposer le test");
        finishTest.setOnAction(e->{
            finishTest();
        });

        vb.getChildren().addAll(rep,list,finishTest);
        listQstScene = new Scene(vb);
        passerTestStage = DefaultFct.defaultStage("TEST N°" + test.getNumTest(),listQstScene);
        passerTestStage.show();
    }

    private void goToQuestion(){
        Question qst = list.getSelectionModel().getSelectedItem();
        if(listScene.containsKey(qst)){
            passerTestStage.setScene(listScene.get(qst));
        }else{
            if(membre.isHeAnswerTheQuestion(qst)){
                chargerQst(qst, membre.getAnswer(qst));
            }else {
                initQuestion(qst, null);
            }
        }
    }

    private void initQuestion(Question qst , ChoiceQst ch){

        BorderPane borderQuestion = DefaultFct.defaultBorder();

        VBox firstLevel = DefaultFct.defaultVbox(Pos.CENTER, 20);

        Label testLabel = new Label("Test n°"+test.getNumTest());
        testLabel.setFont(new Font(50));
        testLabel.setStyle("-fx-text-fill: #ff8e47"); // change the color

        Label qstLabel = new Label("Question n°"+qst.getNumQuestion());
        qstLabel.setFont(new Font(30));
        qstLabel.setStyle("-fx-text-fill: #ff8e47"); // change the color

        Label enoncerQst = new Label(qst.getEnoncerQuestion());

        VBox choixVBox = DefaultFct.defaultVbox();
        choixVBox.setPadding(new Insets(10,10,10,10));
        ArrayList<ChoiceQst> listChoix;
        if(ch == null){
            listChoix = qst.genRandomChoices();
        }else{
            listChoix = qst.genRandomChoices(ch);
        }
        ToggleGroup toggle = new ToggleGroup();
        choix = new RadioButton[listChoix.size()];

        int i = 0;
        for(ChoiceQst choice:listChoix){
            choix[i] = new RadioButton(choice.toString());
            choix[i].setToggleGroup(toggle);
            choixVBox.getChildren().add(choix[i]);
            i++;
        }

        firstLevel.getChildren().addAll(testLabel, qstLabel, enoncerQst, choixVBox);
        borderQuestion.setCenter(firstLevel);

        HBox bottom = DefaultFct.defaultHbox(Pos.BOTTOM_LEFT);

        Button retour = new Button("Retour a la liste des Question");
        Button next = new Button("Suivant");

        Button precedent = new Button("Precedant");

        Button finish = new Button("Terminer le Test");


        bottom.getChildren().addAll(retour ,precedent, next, finish);

        retour.setOnAction(e->{
            ajoutReponse(qst,listChoix);
            passerTestStage.setScene(listQstScene);
        });
        next.setOnAction(e->{
            ajoutReponse(qst,listChoix);
            nextQst(qst);
        });
        precedent.setOnAction(e->{
            ajoutReponse(qst,listChoix);
            precedentQst(qst);
        });
        finish.setOnAction(e->{
            ajoutReponse(qst,listChoix);
            finishTest();
        });

        borderQuestion.setBottom(bottom);

        Scene myScene = new Scene(borderQuestion);
        listScene.put(qst, myScene);
        passerTestStage.setScene(myScene);
    }

    private void chargerQst(Question qst, ChoiceQst choice){
        initQuestion(qst, choice);
        int i = 0;
        while(i<choix.length){
            if(choix[i].equals(choice)){
                choix[i].setSelected(true);
                return;
            }
        }

    }

    private void nextQst(Question qst){
        int i = list.getItems().indexOf(qst);
        if(i < list.getItems().size()-1){
            list.getSelectionModel().select(i+1);
            goToQuestion();
        }
    }

    private void precedentQst(Question qst){
        int i = list.getItems().indexOf(qst);
        if(i > 0){
            list.getSelectionModel().select(i-1);
            goToQuestion();
        }
    }

    private void ajoutReponse (Question qst, ArrayList<ChoiceQst> listChoix){
        int i = (determinerChoix());

        if(membre.isHeAnswerTheQuestion(qst)){
            if (i >= 0){
                membre.alterRepQustion(qst, listChoix.get(i));
            }
        }else{
            if (i >= 0){
                membre.addRepQuestion(qst, listChoix.get(i));
            }
        }


    }

    private int determinerChoix(){
        int i = 0;
        while (i<choix.length){
            if(choix[i].isSelected()){
                return i;
            }
            i++;
        }
        return -1;
    }

    private void finishTest(){
        double note = membre.finaliserTest(test);

        VBox vb = DefaultFct.defaultVbox(30);

        Label lab = new Label("Test terminer");
        lab.setFont(new Font(70));
        lab.setStyle("-fx-text-fill: #ff8e47"); // change the color
        Label lab2 = new Label("Votre note : " + note + "/20");
        lab2.setFont(new Font(40));

        vb.getChildren().addAll(lab,lab2);

        Scene sc = new Scene(vb);
        passerTestStage.setScene(sc);
    }

}
