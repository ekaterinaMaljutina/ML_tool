package ru.spbau.mit.argumentCommands.classification;


import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class Conv2Drop extends ScriptArgsClassificationAbstractClass {

    private static final String FORMAT_DROP = "0.0";

    private JSpinner dropValue = new JSpinner(new SpinnerNumberModel(0.1, 0.1,
            1.0, 0.1));
    private JLabel dropLabel = new JLabel("drop = ", JLabel.LEFT);

    {
        dropValue.setEditor(new JSpinner.NumberEditor(dropValue, FORMAT_DROP));
    }


    @Override
    public @NotNull JPanel drawComponents(@NotNull JPanel panel) {
        init();
        constraints.gridx = zeroGridX();
        constraints.gridy = incGridY();
        constraints.fill = GridBagConstraints.PAGE_START;
        argPanel.add(dropLabel, constraints);
        constraints.gridx = incGridX();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        argPanel.add(dropValue, constraints);

        panel.add(argPanel);
        return panel;
    }
}
