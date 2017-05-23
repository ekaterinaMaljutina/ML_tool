package ru.spbau.mit.argumentCommands.classification;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ArgTrainScritp extends ScriptArgsClassificationAbstractClass {

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
