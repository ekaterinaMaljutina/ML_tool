package ru.spbau.mit.argumentCommands.regression;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class LinRegression extends ScriptArgsRegressionAbstractClass {

    @Override
    public @NotNull String getValueArg(@NotNull String key) {
        return null;
    }

    @Override
    public @NotNull JPanel drawComponents(@NotNull JPanel panel) {
        init();
        panel.add(argPanel);
        return panel;
    }
}
