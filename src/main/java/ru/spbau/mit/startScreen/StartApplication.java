package ru.spbau.mit.startScreen;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.spbau.mit.startScreen.utilStyle.Utils;

public class StartApplication extends Application {

    private static final ChooseFileFX CHOOSE_FILE_FX = new ChooseFileFX();

    private static Text text;
    private static Button next;
    private static Button close;
    private static GridPane gridPane;
    private static Stage currentStage;
    private static Scene startScreen;

    @Override
    public void start(Stage stage) {
        initGrid();
        currentStage = stage;
        startScreen = new Scene(gridPane);
        currentStage.setScene(startScreen);
        currentStage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }

    private static void initChild() {
        text = new Text();
        text = Utils.initTextBlend(text, 28);
        text.setText("Welcome to ML \n" +
                     "      tool box   ");

        next = new Button();
        next.setText("next");

        close = new Button();
        close.setText("close");

        EventHandler<MouseEvent> closeEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                currentStage.close();
            }
        };

        EventHandler<MouseEvent> nextEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                currentStage.close();
                CHOOSE_FILE_FX.start(currentStage);
            }
        };

        close.addEventFilter(MouseEvent.MOUSE_CLICKED, closeEventHandler);
        next.addEventFilter(MouseEvent.MOUSE_CLICKED, nextEventHandler);

        close = Utils.setStyle(close);
        next = Utils.setStyle(next);
    }

    private static void initGrid() {
        initChild();
        gridPane = Utils.initGridPane();
        gridPane.setVgap(50);
        gridPane.setHgap(20);

        gridPane.add(text, 1, 1);
        gridPane.add(close, 0, 2);
        GridPane.setHalignment(close, HPos.LEFT);
        gridPane.add(next, 2, 2);
        GridPane.setHalignment(next, HPos.RIGHT);
    }
}

