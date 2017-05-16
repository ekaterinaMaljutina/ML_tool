package ru.spbau.mit.argumentCommands;


import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class ArgConv1Full extends ScriptArgsAbstractClass {
    private JComboBox func_act = new JComboBox() {{
        addItem("tanh");
        addItem("relu");
        addItem("sigmoid");
    }};


    @Override
    public @NotNull String getValueArg(@NotNull String key) {
        return null;
    }


    @Override
    public @NotNull JPanel drawComponents(@NotNull JPanel panel) {
        init();

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0.0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        argPanel.add(func_act, constraints);
        panel.add(argPanel);
        return panel;
    }
}
