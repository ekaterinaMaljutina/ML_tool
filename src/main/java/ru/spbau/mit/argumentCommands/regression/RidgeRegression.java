package ru.spbau.mit.argumentCommands.regression;


import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RidgeRegression extends ScriptArgsRegressionAbstractClass {

    private JSpinner alphaSpinner = new JSpinner(new SpinnerNumberModel(0.01, 1e-15,
            1.0, 1e-14));
    private JLabel alphaLabel = new JLabel("alpha = ", JLabel.LEFT);

    {
        alphaSpinner.setName("alpha");
    }

//    @Override
//    public @NotNull String getValueArg(@NotNull String key) {
//        return null;
//    }

    @Override
    public @NotNull JPanel drawComponents(@NotNull JPanel panel) {
        init();

        constraints.gridx = zeroGridX();
        constraints.gridy = incGridY();
        argPanel.add(alphaLabel, constraints);
        constraints.gridx = incGridX();
        argPanel.add(alphaSpinner, constraints);

        panel.add(argPanel);
        return panel;
    }
}
