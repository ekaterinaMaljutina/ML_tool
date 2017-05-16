package ru.spbau.mit.argumentCommands;


import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

abstract public class ScriptArgsAbstractClass {

    private static final String FORMAT = "0.000000";
    private JSpinner lerningRate = new JSpinner(new SpinnerNumberModel(0.001, 0.00001,
            1.0, 0.00001));
    protected JPanel argPanel;
    private JLabel label = new JLabel("lr = ", JLabel.LEFT);
    protected GridBagConstraints constraints = new GridBagConstraints();

    {
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        argPanel = new JPanel();
        argPanel.setLayout(new GridBagLayout());
        lerningRate.setEditor(new JSpinner.NumberEditor(lerningRate, FORMAT));
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.5;
    }

    public void init() {
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.fill = GridBagConstraints.PAGE_START;
        argPanel.add(label, constraints);
        constraints.gridx = 1;
        constraints.weightx = 1.5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        argPanel.add(lerningRate, constraints);
    }

    @NotNull
    abstract public String getValueArg(@NotNull final String key);

    @NotNull
    abstract public JPanel drawComponents(@NotNull JPanel panel);
}
