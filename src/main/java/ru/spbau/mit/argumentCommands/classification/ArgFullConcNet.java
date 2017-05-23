package ru.spbau.mit.argumentCommands.classification;


import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class ArgFullConcNet extends ScriptArgsClassificationAbstractClass {


    private static final String FORMAT = "0.000000";

    private static JComboBox func_act = new JComboBox() {{
        addItem("tanh");
        addItem("relu");
        addItem("sigmoid");
    }};

    {
        func_act.setName("func_act");
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
