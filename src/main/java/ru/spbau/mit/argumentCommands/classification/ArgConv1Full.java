package ru.spbau.mit.argumentCommands.classification;


import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class ArgConv1Full extends ScriptArgsClassificationAbstractClass {
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

        constraints.gridx = zeroGridX();
        constraints.gridy = incGridY();
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        argPanel.add(func_act, constraints);
        panel.add(argPanel);
        return panel;
    }
}
