package userInterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sources.Formation;
import sources.Question;
import sources.Test;

import java.util.ArrayList;

public class TestDevEnsUI extends TestDevUI{

    private ListView<Question> listViewQst;

    private Stage qstStage;
    private VBox choiceVbox;
    private ArrayList<HBox> choiceListHBox;

    private TableView<temp_Choice> choicesTable;
    private int cmptChoix;

    TextField answer;
    CheckBox isTrue;


    private TextArea enocerText;

    public TestDevEnsUI(Stage window, Formation form) {
        super(window, form);
    }

    void addTest(Scene scene){
        BorderPane addBorder = DefaultFct.defaultBorder();
        int numTest = form.nbTestsCree();

        HBox topBorder = DefaultFct.defaultHbox(Pos.CENTER);
        Label nFormLabel = new Label("Formation n° : " + form.getNumFormation());
        Label nTestLabel = new Label("Test n° : " + numTest);
        topBorder.getChildren().addAll(nFormLabel, nTestLabel);

        Test test = form.addTest(numTest);

        VBox centerBorder = DefaultFct.defaultVbox(Pos.CENTER, 30);
        Button addButton = new Button("Ajouter une Question");
        addButton.setOnAction(e -> {
            if(test.nbQuestions()<40){
                addQuestion(test);
            }else{
                AlertBox.displayError("Vous ne pouvez pas cree plus de 40 questions par test");
            }
        });
        listViewQst = new ListView<>();

        centerBorder.getChildren().addAll(addButton, listViewQst);

        HBox bottomBorder = DefaultFct.defaultHbox();
        Button confirmerButton = new Button("Finaliser Creation du Test");
        confirmerButton.setOnAction(e->{
            if(test.validerTest()){
                window.setScene(scene);
            }else {
                AlertBox.displayError("Vous ne Pouvez Valider La creation d'un test qu'avec 5, 10, 20 ou 40 Questions");
            }
        });
        Button returnButton = new Button("Retour");
        returnButton.setOnAction(e->{
            window.setScene(scene);
        });
        bottomBorder.getChildren().addAll(returnButton, confirmerButton);

        addBorder.setTop(topBorder);
        addBorder.setCenter(centerBorder);
        addBorder.setBottom(bottomBorder);

        Scene sc = new Scene(addBorder);
        window.setScene(sc);
    }

    void continueCreatingTest(){

    }

    void modifyTest(){

    }

    private void addQuestion(Test test){
        int numQuestion = test.nbQuestions() + 1;
        cmptChoix = 0;
        Label nQstLabel = new Label("Question n° : " + numQuestion);

        VBox firstLevel = DefaultFct.defaultVbox(Pos.CENTER, 30);
        HBox secondLevel = DefaultFct.defaultHbox(Pos.CENTER);
        Label enoncerLabel = new Label("Enoncer de la question : ");
        enocerText = new TextArea();
        secondLevel.getChildren().addAll(enoncerLabel, enocerText);

        TableColumn<temp_Choice, Integer> numCol = new TableColumn<>("Num Choix");
        numCol.setMinWidth(100);
        numCol.setCellValueFactory(new PropertyValueFactory<>("numUI"));

        TableColumn<temp_Choice, String> contCol = new TableColumn<>("reponse choix");
        contCol.setMinWidth(250);
        contCol.setCellValueFactory(new PropertyValueFactory<>("contenue"));

        TableColumn<temp_Choice, Boolean> blnCol = new TableColumn<>("est Vrai");
        blnCol.setMinWidth(100);
        blnCol.setCellValueFactory(new PropertyValueFactory<>("right"));

        choicesTable = new TableView<>();
        choicesTable.getColumns().addAll(numCol, contCol, blnCol);

        answer = new TextField();
        isTrue = new CheckBox("is true");
        HBox hb = DefaultFct.defaultHbox();
        hb.getChildren().addAll(answer, isTrue);

        HBox btnHBox = DefaultFct.defaultHbox();
        Button add = new Button("add");
        add.setOnAction(e->addChoice());
        Button delete = new Button("delete");
        delete.setOnAction(e-> {

        });
        Button modify = new Button("modify");
        modify.setOnAction(e->{
            if(!DefaultFct.emptyField(DefaultFct.redPromptText, answer)){

            }
        });
        btnHBox.getChildren().addAll(add, delete);

        firstLevel.getChildren().addAll(secondLevel, hb, btnHBox, choicesTable);

        Button confirm = new Button("Confirmer");
        confirm.setOnAction(e->confirmAddQst(test, numQuestion));

        questionStuff(test, nQstLabel, confirm, firstLevel);
    }

    private void modifyQuestion(Test test, Question qst){
        Label nQstLabel = new Label("Question n° : " + qst.getNumQuestion());

        VBox firstLevel = DefaultFct.defaultVbox(Pos.CENTER, 30);
        HBox secondLevel = DefaultFct.defaultHbox(Pos.CENTER);
        Label enoncerLabel = new Label("Enoncer de la question : ");
        enocerText = new TextArea();
        enocerText.setText(qst.getEnoncerQuestion());
        secondLevel.getChildren().addAll(enoncerLabel, enocerText);

        choiceListHBox = new ArrayList<>();

        choiceVbox = DefaultFct.defaultVbox();

        HBox btnHBox = DefaultFct.defaultHbox();
        Button add = new Button("add");
        add.setOnAction(e->addChoice());
        Button delete = new Button("delete");
        //delete.setOnAction();
        btnHBox.getChildren().addAll(add, delete);

        firstLevel.getChildren().addAll(secondLevel, btnHBox, choiceVbox);


        Button confirm = new Button("Confirmer");


        questionStuff(test ,nQstLabel, confirm, firstLevel);
    }

    // Load the choice with modifQuestion
    public ObservableList<temp_Choice> getChoices(){
        ObservableList<temp_Choice> choices = FXCollections.observableArrayList();

        return choices;
    }

    private void addChoice(){
        if(!DefaultFct.emptyField(DefaultFct.redPromptText, answer)){
            cmptChoix++;
            choicesTable.getItems().add(new temp_Choice(answer.getText(),cmptChoix, isTrue.isSelected()));
            isTrue.setSelected(false);
            answer.clear();
        }
    }

    private void questionStuff(Test test, Label nQstLabel, Button confirm, VBox centerBorder){
        BorderPane qstBorder = DefaultFct.defaultBorder();

        HBox topBorder = DefaultFct.defaultHbox(Pos.CENTER);
        Label nFormLabel = new Label("Formation n° : " + form.getNumFormation());
        Label nTestLabel = new Label("Test n° : " + test.getNumTest());

        topBorder.getChildren().addAll(nFormLabel, nTestLabel, nQstLabel);


        HBox bottomBorder = DefaultFct.defaultHbox(Pos.BOTTOM_RIGHT);
        Button cancel = new Button("Annuler");

        bottomBorder.getChildren().addAll(cancel, confirm);

        qstBorder.setCenter(centerBorder);
        qstBorder.setTop(topBorder);
        qstBorder.setBottom(bottomBorder);

        Scene qstScene = new Scene(qstBorder);
        qstStage = DefaultFct.defaultStage("Question",qstScene);
        cancel.setOnAction(e -> qstStage.close());
        qstStage.showAndWait();
    }

    private void confirmAddQst(Test test, int numQ){
        Question qst = test.addQuestion(numQ, enocerText.getText());
        ArrayList<temp_Choice> listchoice = new ArrayList<>(choicesTable.getItems());

        for(temp_Choice temp :listchoice){
            qst.addChoice(temp.getNumUI(), temp.getContenue(), temp.isRight());
        }
        listViewQst.getItems().add(qst);
        qstStage.close();
    }

    private void confirmModifyQst(){

    }

    void deleteTest(){

    }

    void addDevoir(){

    }

    void modifyDevoir(){

    }

    void deleteDevoir(){

    }

    void listTest(Scene scene){

        Scene listTestScene;
        BorderPane listTestBorder = DefaultFct.defaultBorder();

        ListView<Test> testsList = genViews.getTests(form);

        VBox buttons = DefaultFct.defaultVbox();
        Button opentests = new Button("Ouvrir");
        Button deletetests = new Button("Supprimer");
        Button modifiertests = new Button("Modifier");
        buttons.getChildren().addAll(opentests,modifiertests,deletetests);

        HBox bottomBorder = DefaultFct.defaultHbox(Pos.BOTTOM_LEFT);
        Button returnButton = new Button("Retour");
        returnButton.setOnAction(e-> window.setScene(scene));
        bottomBorder.getChildren().add(returnButton);

        listTestBorder.setCenter(testsList);
        listTestBorder.setRight(buttons);
        listTestBorder.setBottom(bottomBorder);

        opentests.setOnAction(e -> {

        });
        modifiertests.setOnAction(e -> {

        });
        deletetests.setOnAction(e -> {
            //deleteTest();
        });

        listTestScene = new Scene(listTestBorder);
        window.setScene(listTestScene);
    }

}
