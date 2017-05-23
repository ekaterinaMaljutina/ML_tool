package ru.spbau.mit.argumentCommands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public abstract class ArgsAbstactClass {

    private int gridX = 0;
    private int gridY = 0;

    protected int zeroGridX() {
        gridX = 0;
        return gridX;
    }

    protected int zeroGridY() {
        gridY = 0;
        return gridY;
    }
    protected int incGridX() {
        return ++gridX;
    }

    protected int incGridY() {
        return ++gridY;
    }

    abstract public @Nullable String getValueArg(@NotNull final String key);

    abstract public @NotNull JPanel drawComponents(@NotNull JPanel panel);
}
