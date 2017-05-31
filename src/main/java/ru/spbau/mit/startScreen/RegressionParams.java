package ru.spbau.mit.startScreen;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class RegressionParams extends Application {

    private static final String PATH_TO_ARGS = "src/main/resources/regression/ArgsFile";

    private static RegressionType type = RegressionType.None;

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

    private void initGrid() {
        initChild();

        gridPane = Utils.initGridPane();

        for (int i = 0; i < text.length; i++) {
            SpinnerValueFactory<String> valueFactory;
            valueFactory =
                    new SpinnerValueFactory.ListSpinnerValueFactory<String>(
                            FXCollections.observableArrayList(initValue[i]));
            spinners[i].setValueFactory(valueFactory);
            spinners[i].setEditable(true);
            gridPane.add(text[i], 0, i);
            gridPane.add(spinners[i], 1, i);
        }
        gridPane.add(run, 2, text.length);
        gridPane.setVgap(5);
        gridPane.setHgap(10);
    }

    private void initChild() {
        String nameMethod = type.name();
        getArgument(nameMethod);

        run = new Button();
        run.setText("Run");
        run = Utils.setStyle(run);

        text = new Text[args.length];
        spinners = new Spinner[args.length];
        for (int i = 0; i < args.length; i++) {
            text[i] = new Text();
            spinners[i] = new Spinner<>();
            text[i] = Utils.initTextBlend(text[i], 14);
            text[i].setText(args[i]);
        }
    }

    private void initArgs(@NotNull String line, @NotNull final String method) {
        String[] nameAndArgs = line.split(":");
        String label = nameAndArgs[0].replaceAll(" ", "");

        if (label.equals(method)) {
            String[] params = nameAndArgs[1].replaceAll(" ", "").split(",");
            args = new String[params.length - 1];
            initValue = new String[params.length - 1];

            for (int i = 0; i < args.length; i++) {
                String[] value = params[i + 1].split("#");
                args[i] = value[0];
                if (value.length == 1) {
                    initValue[i] = ChooseFileFX.getChooseFile();
                } else {
                    initValue[i] = value[1];
                }
            }
        }
    }

    private void getArgument(@NotNull final String method) {
        try (Stream<String> stream = Files.lines(Paths.get(PATH_TO_ARGS))) {
            stream.forEach(line -> initArgs(line, method));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
