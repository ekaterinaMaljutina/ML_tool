package ru.spbau.mit.argumentCommands.classification;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.mit.argumentCommands.ArgsAbstactClass;

import javax.swing.*;
import java.awt.*;

abstract public class ScriptArgsClassificationAbstractClass extends ArgsAbstactClass {

    private static final String FORMAT = "0.000000";

    protected JPanel argPanel;

    private JLabel label = new JLabel("lr = ", JLabel.CENTER);
    private JSpinner lerningRate = new JSpinner(new SpinnerNumberModel(0.001, 0.00001, 1.0, 0.00001));

    private JLabel epochLabel = new JLabel("epoch = ", JLabel.CENTER);
    private JSpinner epochSpinner = new JSpinner(new SpinnerNumberModel(100, 1, 1000000, 10));

    private JLabel batchSizeLabel = new JLabel("batch_size = ", JLabel.CENTER);
    private JSpinner batchSizeSpinner = new JSpinner(new SpinnerNumberModel(32, 4, 512, 4));

    private JLabel snapshotLabel = new JLabel("snapshot = ", JLabel.CENTER);
    private JSpinner snapshotSpinner = new JSpinner(new SpinnerNumberModel(20, 1, 100000, 5));


    protected GridBagConstraints constraints = new GridBagConstraints();

    {
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        argPanel = new JPanel();
        argPanel.setLayout(new GridBagLayout());
        lerningRate.setEditor(new JSpinner.NumberEditor(lerningRate, FORMAT));
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.5;
    }

    @Override
    public @Nullable String getValueArg(@NotNull String key) {
        return null;
    }

    protected void init() {
        //epoch
        constraints.gridx = zeroGridX();
        constraints.gridy = zeroGridY();
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
        constraints.fill = GridBagConstraints.LINE_START;
        argPanel.add(snapshotLabel, constraints);
        constraints.gridx = incGridX();
        constraints.fill = GridBagConstraints.HORIZONTAL;
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

}
