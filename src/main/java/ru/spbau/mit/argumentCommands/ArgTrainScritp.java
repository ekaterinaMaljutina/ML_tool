package ru.spbau.mit.argumentCommands;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class ArgTrainScritp extends ScriptArgsAbstractClass {

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
