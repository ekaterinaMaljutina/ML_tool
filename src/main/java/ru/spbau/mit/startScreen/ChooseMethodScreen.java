package ru.spbau.mit.startScreen;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.spbau.mit.startScreen.classification.ClassificationType;
import ru.spbau.mit.startScreen.regression.RegressionType;
import ru.spbau.mit.startScreen.utilStyle.Utils;

public class ChooseMethodScreen extends Application {

    private static final ParameterMethodScreen PARAMETER_METHOD_SCREEN = new ParameterMethodScreen();
    private static final TaskScreen TASK_SCREEN = new TaskScreen();

    private Stage currentStage;
    private Scene startScreen;

    private RadioButton[] typeMethodChoose;
    private final ToggleGroup groupMethodChoose = new ToggleGroup();

    private static GridPane gridPane;

    private Button next;
    private Button prev;

    private String selectRaioButton;


    @Override
    public void start(Stage stage) {
        initGrid();
        currentStage = stage;
        startScreen = new Scene(gridPane);
        currentStage.setScene(startScreen);
        currentStage.show();
    }

    private void initGrid() {
        initChild();
        gridPane = new GridPane();

        gridPane = Utils.initGridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(10);

        for (int i = 0; i < typeMethodChoose.length; i++) {
            gridPane.add(typeMethodChoose[i], 1, i);
            GridPane.setHalignment(typeMethodChoose[i], HPos.LEFT);
        }

        gridPane.add(prev, 0, 6);
        GridPane.setHalignment(prev, HPos.LEFT);
        gridPane.add(next, 2, 6);
        GridPane.setHalignment(next, HPos.RIGHT);

    }

    private void initChild() {
        initButton();
        addListenerOfChange();

        if (TaskScreen.isClassification()) {
            ClassificationType[] types = ClassificationType.values();
            typeMethodChoose = new RadioButton[types.length - 1];
            for (int i = 0; i < typeMethodChoose.length; i++) {
                typeMethodChoose[i] = new RadioButton(types[i].name());
                typeMethodChoose[i].setToggleGroup(groupMethodChoose);
            }

        }
        if (TaskScreen.isRegression()) {
            RegressionType[] types = RegressionType.values();
            typeMethodChoose = new RadioButton[types.length - 1];
            for (int i = 0; i < typeMethodChoose.length; i++) {
                typeMethodChoose[i] = new RadioButton(types[i].name());
                typeMethodChoose[i].setToggleGroup(groupMethodChoose);
            }
        }
    }

    private void initButton() {
        next = new Button();
        next.setText("next");

        prev = new Button();
        prev.setText("prev");

        EventHandler<MouseEvent> nextEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (selectRaioButton != null) {
                    ParameterMethodScreen.setRegressionType(selectRaioButton);
                    PARAMETER_METHOD_SCREEN.start(currentStage);
                }
            }
        };

        EventHandler<MouseEvent> prevEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                TASK_SCREEN.start(currentStage);
            }
        };

        prev.addEventFilter(MouseEvent.MOUSE_CLICKED, prevEventHandler);
        next.addEventFilter(MouseEvent.MOUSE_CLICKED, nextEventHandler);

        prev = Utils.setStyle(prev);
        next = Utils.setStyle(next);
    }

    private void addListenerOfChange() {
        groupMethodChoose.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle oldToggle, Toggle newToggle) {
                if (groupMethodChoose.getSelectedToggle() != null) {
                    RadioButton radioButton = (RadioButton) groupMethodChoose.getSelectedToggle();
                    selectRaioButton = radioButton.getText();
                }

            }
        });

    }

    public static void main(String args[]) {
        launch(args);
    }
}
