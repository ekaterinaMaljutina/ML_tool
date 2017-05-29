package ru.spbau.mit.startScreen;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.spbau.mit.Main;


public class TaskScreen extends Application {

    private static final ChooseFileFX CHOOSE_FILE_FX = new ChooseFileFX();

    private static final String CLASSIFICATION_TEXT = "Classification";
    private static final String REGRESSION_TEXT = "Regression";
    private static final String GOOD_TEXT = "Good";

    private static boolean chooseRegression = false;
    private static boolean chooseClassification = false;

    private static Button next;
    private static Button prev;
    private static GridPane gridPane;
    private static Scene startScreen;
    private static Text regressionTask;
    private static Text classificationTask;
    private static Stage currentStage;

    private static EventHandler<MouseEvent> taskClickEventHandler;

    static {
        initEventHadler();
    }

    @Override
    public void start(Stage stage) {
        currentStage = stage;
        initGrid();
        startScreen = new Scene(gridPane);
        currentStage.setScene(startScreen);
        currentStage.show();
    }
    public static boolean isRegression() {
        return chooseRegression;
    }

    public static boolean isClassification() {
        return chooseClassification;
    }

    private static void initTask() {
        regressionTask = new Text();
        regressionTask = Utils.initTextBlend(regressionTask, 25);
        regressionTask.setText(REGRESSION_TEXT);
        regressionTask.addEventFilter(MouseEvent.MOUSE_CLICKED, taskClickEventHandler);

        classificationTask = new Text();
        classificationTask = Utils.initTextBlend(classificationTask, 25);
        classificationTask.setText(CLASSIFICATION_TEXT);
        classificationTask.addEventFilter(MouseEvent.MOUSE_CLICKED, taskClickEventHandler);
    }

    private static void initEventHadler() {
        taskClickEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Text currentText = (Text) event.getSource();
                switch (currentText.getText()) {
                    case REGRESSION_TEXT:
                        classificationTask.setText("\t\t");
                        regressionTask.setText("\t" + GOOD_TEXT + "\t");
                        chooseRegression = true;
                        break;
                    case CLASSIFICATION_TEXT:
                        classificationTask.setText("\t" + GOOD_TEXT + "\t");
                        regressionTask.setText("\t\t");
                        chooseClassification = true;
                        break;
                }
            }
        };
    }

    private static void initButton() {
        next = new Button();
        next.setText("next");

        prev = new Button();
        prev.setText("prev");

        EventHandler<MouseEvent> prevEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                CHOOSE_FILE_FX.start(currentStage);
            }
        };

        EventHandler<MouseEvent> nextEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                currentStage.close();
                Main.main(null);
            }
        };

        prev.addEventFilter(MouseEvent.MOUSE_CLICKED, prevEventHandler);
        next.addEventFilter(MouseEvent.MOUSE_CLICKED, nextEventHandler);

        prev = Utils.setStyle(prev);
        prev.setAlignment(Pos.CENTER_LEFT);
        next = Utils.setStyle(next);
        next.setAlignment(Pos.CENTER_RIGHT);
    }

    private static void initChild() {
        initTask();
        initButton();
    }

    private static void initGrid() {
        initChild();

        gridPane = Utils.initGridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(50);
        gridPane.setHgap(20);

        gridPane.add(regressionTask, 0, 0);
        gridPane.add(classificationTask, 2, 0);
        gridPane.add(prev, 0, 2);
        GridPane.setHalignment(prev, HPos.LEFT);
        gridPane.add(next, 2, 2);
        GridPane.setHalignment(next, HPos.RIGHT);
    }
}
