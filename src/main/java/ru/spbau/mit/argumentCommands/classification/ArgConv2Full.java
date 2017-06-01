package ru.spbau.mit.argumentCommands.classification;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ArgConv2Full extends ScriptArgsClassificationAbstractClass {

    @Override
    public @NotNull JPanel drawComponents(@NotNull JPanel panel) {
        init();
        panel.add(argPanel);
        return panel;
    }
}
