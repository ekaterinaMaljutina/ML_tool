package ru.spbau.mit.startScreen;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.ProcessBuider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class RegressionParams extends Application {

    private static final String PATH_TO_REGRESSION = "src/main/resources/regression";
    private static final String PATH_TO_ARGS = PATH_TO_REGRESSION + "/ArgsFile";

    private static RegressionType type = RegressionType.None;

    private String nameScript;
    private String[] args;
    private String[] initValue;

    private Text[] text;
    private Spinner<String>[] spinners;
    private Button run;

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

    public static void setRegressionType(RegressionType t) {
        type = t;
    }

    private <T> void commitEditorText(Spinner<T> spinner, int idx) {
        if (!spinner.isEditable()) return;
        String text = spinner.getEditor().getText();
        initValue[idx] = text;
        SpinnerValueFactory<T> valueFactory = spinner.getValueFactory();
        if (valueFactory != null) {
            StringConverter<T> converter = valueFactory.getConverter();
            if (converter != null) {
                T value = converter.fromString(text);
                valueFactory.setValue(value);
            }
        }
    }


    private void initGrid() {
        initChild();

        gridPane = Utils.initGridPane();

        for (int i = 0; i < text.length; i++) {
            SpinnerValueFactory<String> valueFactory;
            final int idx = i;
            valueFactory =
                    new SpinnerValueFactory.ListSpinnerValueFactory<String>(
                            FXCollections.observableArrayList(initValue[i]));
            spinners[i].setValueFactory(valueFactory);
            spinners[i].setEditable(true);

            spinners[idx].focusedProperty().addListener((s, oldValue, newValue) -> {
                if (newValue) return;
//                System.out.println(String.format("%s old value, %s new value, %s idx, %s initValue",
//                        oldValue, newValue, idx, initValue[idx]));
                commitEditorText(spinners[idx], idx);
            });
            gridPane.add(text[i], 0, i);
            gridPane.add(spinners[i], 1, i);
        }
        gridPane.add(run, 2, text.length);
        gridPane.setVgap(5);
        gridPane.setHgap(10);
    }

    private void initChild() {
        initRunButton();

        String nameMethod = type.name();
        getArgument(nameMethod);

        text = new Text[args.length];
        spinners = new Spinner[args.length];
        for (int i = 0; i < args.length; i++) {
            text[i] = new Text();
            spinners[i] = new Spinner<>();
            text[i] = Utils.initTextBlend(text[i], 14);
            text[i].setText(args[i]);
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

                ProcessBuider processBuider =  new ProcessBuider(createListArgsForProcessBuilder());
                processBuider.start();
            }
        };

        run.addEventFilter(MouseEvent.MOUSE_CLICKED, runEventHandler);
    }

    private void findNameMethod(@NotNull String line, @NotNull final String method) {
        String[] nameAndArgs = line.split(":");
        String label = nameAndArgs[0].replaceAll(" ", "");

        if (label.equals(method)) {
            initArgs(nameAndArgs[1].replaceAll(" ", "").split(","));
        }
    }

    private void initArgs(String[] params) {
        nameScript = params[0];
        args = new String[params.length - 1];
        initValue = new String[params.length - 1];

        for (int i = 0; i < args.length; i++) {
            String[] value = params[i + 1].split("#");
            args[i] = value[0];
            if (value.length == 1) { // only data with length = 1
                initValue[i] = ChooseFileFX.getChooseFile();
            } else {
                initValue[i] = value[1];
            }
        }
    }

    private void getArgument(@NotNull final String method) {
        try (Stream<String> stream = Files.lines(Paths.get(PATH_TO_ARGS))) {
            stream.forEach(line -> findNameMethod(line, method));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private List<String> createListArgsForProcessBuilder() {
        List<String> res = new ArrayList<>();
        res.add("python");
        res.add(PATH_TO_REGRESSION + "/" + nameScript + ".py");
        for (int i = 0; i < args.length; i++) {
            res.add("--" + args[i]);
            res.add(initValue[i]);
        }
        return res;
    }
}
