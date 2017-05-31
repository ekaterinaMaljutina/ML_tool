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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RegressionMethodScreen extends Application {
    private static final String LINEAR_REGRESSION = "Linear Regression";
    private static final String POLY_REGRESSION = "Polynomial Regression";
    private static final String LASSO_REGRESSION = "Lasso Regression";

    private static final RegressionParams REGRESSION_PARAMS = new RegressionParams();

    private Stage currentStage;
    private Scene startScreen;

    private Text linearRegression;
    private Text polynRegression;
    private Text lassoRegression;

    private static GridPane gridPane;

    private Button next;
    private Button prev;


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

        gridPane.add(linearRegression, 1, 1);
        gridPane.add(polynRegression, 1, 2);
        gridPane.add(lassoRegression, 1, 3);
        gridPane.add(prev, 0, 6);
        GridPane.setHalignment(prev, HPos.LEFT);
        gridPane.add(next, 2, 6);
        GridPane.setHalignment(next, HPos.RIGHT);

    }

    private void initChild() {
        initButton();

        linearRegression = initText(linearRegression, LINEAR_REGRESSION);
        polynRegression = initText(polynRegression, POLY_REGRESSION);
        lassoRegression = initText(lassoRegression, LASSO_REGRESSION);
    }

    private void initButton() {
        next = new Button();
        next.setText("next");

        prev = new Button();
        prev.setText("prev");

        EventHandler<MouseEvent> nextEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                RegressionParams.setRegressionType(RegressionType.LinearRegression);
                REGRESSION_PARAMS.start(currentStage);
            }
        };

        next.addEventFilter(MouseEvent.MOUSE_CLICKED, nextEventHandler);

        prev = Utils.setStyle(prev);
        next = Utils.setStyle(next);
    }

    private Text initText(@Nullable Text text, @NotNull final String value) {
        text = new Text();
        text = Utils.initTextBlend(text, 20);
        text.setText(value);
        return text;
    }

    public static void main(String args[]) {
        launch(args);
    }

}
