package ru.spbau.mit.argumentCommands;


import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

abstract public class ScriptArgsAbstractClass {

    private static final String FORMAT = "0.000000";
    private JSpinner lerningRate = new JSpinner(new SpinnerNumberModel(0.001, 0.00001,
            1.0, 0.00001));
    protected JPanel argPanel;
    private JLabel label = new JLabel("lr = ", JLabel.CENTER);

    private JLabel epochLabel = new JLabel("epoch = ", JLabel.CENTER);
    private JSpinner epochSpinner = new JSpinner(new SpinnerNumberModel(100, 1, 1000000, 10));

    private JLabel batchSizeLabel = new JLabel("batch_size = ", JLabel.CENTER);
    private JSpinner batchSizeSpinner = new JSpinner(new SpinnerNumberModel(32, 4, 512, 4));

    private JLabel snapshotLabel = new JLabel("snapshot = ", JLabel.CENTER);
    private JSpinner snapshotSpinner = new JSpinner(new SpinnerNumberModel(20, 1, 100000, 5));

    private int gridX = 0;
    private int gridY = 0;

    protected GridBagConstraints constraints = new GridBagConstraints();

    {
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        argPanel = new JPanel();
        argPanel.setLayout(new GridBagLayout());
        lerningRate.setEditor(new JSpinner.NumberEditor(lerningRate, FORMAT));
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.5;
    }

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

    public void init() {
        //epoch
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.fill = GridBagConstraints.PAGE_START;
        argPanel.add(epochLabel, constraints);
        constraints.gridx = incGridX();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        argPanel.add(epochSpinner, constraints);

        // batch size
        constraints.gridx = zeroGridX();
        constraints.gridy = incGridY();
        constraints.fill = GridBagConstraints.LINE_START;
        argPanel.add(batchSizeLabel, constraints);
        constraints.gridx = incGridX();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        argPanel.add(batchSizeSpinner, constraints);

        //snapshot
        constraints.gridx = zeroGridX();
        constraints.gridy = incGridY();
        argPanel.add(snapshotLabel, constraints);
        constraints.gridx = incGridX();
        argPanel.add(snapshotSpinner, constraints);

        // lerning rate
        constraints.gridx = zeroGridX();
        constraints.gridy = incGridY();
        constraints.fill = GridBagConstraints.LINE_START;
        argPanel.add(label, constraints);
        constraints.gridx = incGridX();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        argPanel.add(lerningRate, constraints);
    }

    @NotNull
    abstract public String getValueArg(@NotNull final String key);

    @NotNull
    abstract public JPanel drawComponents(@NotNull JPanel panel);
}
