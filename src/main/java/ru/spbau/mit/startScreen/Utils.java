package ru.spbau.mit.startScreen;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

/**
 * Created by kate on 5/25/17.
 */
public class Utils {
    public static Text initTextBlend(@NotNull final Text text, final int frontSize) {
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, frontSize));

        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY);

        DropShadow ds = new DropShadow();
        ds.setColor(Color.rgb(254, 235, 66, 0.3));
        ds.setOffsetX(5);
        ds.setOffsetY(5);
        ds.setRadius(5);
        ds.setSpread(0.2);

        blend.setBottomInput(ds);

        DropShadow ds1 = new DropShadow();
        ds1.setColor(Color.web("#f13a00"));
        ds1.setRadius(20);
        ds1.setSpread(0.2);

        Blend blend2 = new Blend();
        blend2.setMode(BlendMode.MULTIPLY);

        InnerShadow is = new InnerShadow();
        is.setColor(Color.web("#feeb42"));
        is.setRadius(9);
        is.setChoke(0.8);
        blend2.setBottomInput(is);

        InnerShadow is1 = new InnerShadow();
        is1.setColor(Color.web("#f13a00"));
        is1.setRadius(5);
        is1.setChoke(0.4);
        blend2.setTopInput(is1);

        Blend blend1 = new Blend();
        blend1.setMode(BlendMode.MULTIPLY);
        blend1.setBottomInput(ds1);
        blend1.setTopInput(blend2);

        blend.setTopInput(blend1);

        text.setEffect(blend);
        return text;
    }

    public static GridPane initGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setMaxSize(400, 200);
        gridPane.setMinSize(400, 200);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: BEIGE;");
        return gridPane;
    }

    public static Button setStyle(@NotNull final Button button) {
        button.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        return button;
    }
}
