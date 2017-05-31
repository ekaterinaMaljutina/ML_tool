package ru.spbau.mit.startScreen;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class ChooseFileFX extends Application {

    private static final StartApplication START_APPLICATION = new StartApplication();
    private static final TaskScreen TASK_SCREEN = new TaskScreen();

    private static Button next;
    private static Button prev;
    private static GridPane gridPane;
    private static Label text;
    private static Scene startScreen;
    private static Text fileChoose;
    private static Stage currentStage;
    private static FileChooser fileChooser;
    private static EventHandler<MouseEvent> fileChooserEventHandler;
    private static String currentFileChooder = null;

    static {
        initFileChooserEventHandler();
    }


    @Nullable
    public static String getChooseFile() {
        return currentFileChooder;
    }

    @Override
    public void start(Stage stage) {
        currentStage = stage;
        initGrid();
        startScreen = new Scene(gridPane);
        currentStage.setScene(startScreen);
        currentStage.show();
    }


    private static void initFileChooser() {
        fileChoose = new Text();
        fileChoose = Utils.initTextBlend(fileChoose, 25);
        fileChoose.setText("Choose your file");
        fileChoose.addEventFilter(MouseEvent.MOUSE_CLICKED, fileChooserEventHandler);
        if (currentFileChooder == null) {
            return;
        }

        text = new Label();
        text.setText("your choose : " + currentFileChooder);
        text.setWrapText(true);

    }

    private static void initFileChooserEventHandler() {
        fileChooserEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                fileChooser = new FileChooser();
                configureFileChooser(fileChooser);
                File file = fileChooser.showOpenDialog(currentStage);
                if (file != null) {
                    currentFileChooder = file.getPath();
                    remoteEvent();
                    fileChoose.setText("\tGood\t");
                    Label text = new Label();
                    text.setText("file: " + file);
                    text.setWrapText(true);
                    gridPane.add(text, 1, 2);

                }
            }
        };
    }

    private static void remoteEvent() {
        fileChoose.removeEventFilter(MouseEvent.MOUSE_CLICKED, fileChooserEventHandler);
    }

    private static void initGrid() {
        initChild();
        initFileChooser();
        gridPane = Utils.initGridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(50);
        gridPane.setHgap(10);

        gridPane.add(fileChoose, 1, 1);
        gridPane.add(prev, 0, 2);
        GridPane.setHalignment(prev, HPos.LEFT);
        gridPane.add(next, 2, 2);

        if (currentFileChooder != null) {
            gridPane.add(text, 1, 2);
        }
        GridPane.setHalignment(next, HPos.RIGHT);
    }

    private static void initChild() {
        next = new Button();
        next.setText("next");

        prev = new Button();
        prev.setText("prev");

        EventHandler<MouseEvent> prevEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                START_APPLICATION.start(currentStage);
            }
        };

        EventHandler<MouseEvent> nextEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (currentFileChooder == null) {
                    return;
                }
                TASK_SCREEN.start(currentStage);
            }
        };

        prev.addEventFilter(MouseEvent.MOUSE_CLICKED, prevEventHandler);
        next.addEventFilter(MouseEvent.MOUSE_CLICKED, nextEventHandler);

        prev = Utils.setStyle(prev);
        next = Utils.setStyle(next);
    }


    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
    }
}