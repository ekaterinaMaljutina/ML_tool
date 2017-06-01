package ru.spbau.mit.startScreen;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.spbau.mit.ProcessBuider;
import ru.spbau.mit.startScreen.regression.RegressionType;
import ru.spbau.mit.startScreen.utilStyle.Utils;
import ru.spbau.mit.utilScript.LoadScript;

import java.util.ArrayList;
import java.util.List;

public class ParameterMethodScreen extends Application {

    private static final String PATH = "src/main/resources/";
    private static final String ARGS = "/ArgsFile";

    private static final ChooseMethodScreen CHOOSE_METHOD_SCREEN = new ChooseMethodScreen();

    private static String type = RegressionType.None.name();

    private Text[] text;
    private Spinner<String>[] spinners;
    private Button run;
    private Button prev;

    private Stage currentStage;
    private Scene startScreen;
    private GridPane gridPane;

    @Override
    public void start(Stage stage) {
        currentStage = stage;
        initGrid();
        startScreen = new Scene(gridPane);
        currentStage.setScene(startScreen);
        currentStage.show();

    }

    public static void setRegressionType(String t) {
        type = t;
    }

    private <T> void commitEditorText(Spinner<T> spinner, int idx) {
        if (!spinner.isEditable()) return;
        String value = spinner.getEditor().getText();
        LoadScript.setValue(type, text[idx].getText(), value);
    }


    private void initGrid() {
        initChild();

        gridPane = Utils.initGridPane();
        if (text == null) {
            return;
        }
        for (int i = 0; i < text.length; i++) {
            SpinnerValueFactory<String> valueFactory;
            final int idx = i;
            valueFactory =
                    new SpinnerValueFactory.ListSpinnerValueFactory<String>(
                            FXCollections.observableArrayList(LoadScript.getInitArgsValueByLabel(type,
                                    text[i].getText())));
            spinners[i].setValueFactory(valueFactory);
            spinners[i].setEditable(true);

            spinners[idx].focusedProperty().addListener((s, oldValue, newValue) -> {
                if (newValue) return;
//                System.out.println(String.format("%s old value, %s new value, %s idx, %s initValue",
//                        oldValue, newValue, idx, initValue[idx]));
                commitEditorText(spinners[idx], idx);
            });
            gridPane.add(text[i], 1, i);
            gridPane.add(spinners[i], 2, i);
        }
        gridPane.add(prev, 0, text.length);
        GridPane.setHalignment(prev, HPos.LEFT);
        gridPane.add(run, 3, text.length);
        GridPane.setHalignment(run, HPos.RIGHT);
        gridPane.setVgap(2);
        gridPane.setHgap(5);
    }

    private void initChild() {
        initRunButton();
        initPrevButton();

        loadScripts();

        text = new Text[LoadScript.getSizeArgByLabel(type)];
        spinners = new Spinner[text.length];
        int i = 0;
        for (String value : LoadScript.getSetArgByLabel(type)) {
            text[i] = new Text();
            spinners[i] = new Spinner<>();
            text[i] = Utils.initTextBlend(text[i], 14);
            text[i].setText(value);
            i++;
        }
    }

    private void initRunButton() {

        run = new Button();
        run.setText("Run");
        run = Utils.setStyle(run);

        EventHandler<MouseEvent> runEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                System.out.println(createListArgsForProcessBuilder());
                System.out.println("Run script");

                ProcessBuider processBuider = new ProcessBuider(createListArgsForProcessBuilder());
                processBuider.start();
            }
        };

        run.addEventFilter(MouseEvent.MOUSE_CLICKED, runEventHandler);
    }

    private void initPrevButton() {
        prev = new Button();
        prev.setText("prev");
        prev = Utils.setStyle(prev);

        EventHandler<MouseEvent> prevEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                CHOOSE_METHOD_SCREEN.start(currentStage);
            }
        };

        prev.addEventFilter(MouseEvent.MOUSE_CLICKED, prevEventHandler);
    }

    private void loadScripts() {
        String path = PATH;
        if (TaskScreen.isClassification()) {
            path += TaskScreen.CLASSIFICATION_TEXT;
        }
        if (TaskScreen.isRegression()) {
            path += TaskScreen.REGRESSION_TEXT;
        }
        LoadScript.LoadArgs(path + ARGS, path);
        if (ChooseFileFX.getChooseFile() != null) {
            LoadScript.setValue(type, "data", ChooseFileFX.getChooseFile());
        }
    }

    private List<String> createListArgsForProcessBuilder() {
        List<String> res = new ArrayList<>();
        res.add("python");
        res = LoadScript.getArgsListWithValueByLabel(type, res);
        return res;
    }
}
