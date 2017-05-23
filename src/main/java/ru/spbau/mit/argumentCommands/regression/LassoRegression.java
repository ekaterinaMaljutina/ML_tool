package ru.spbau.mit.argumentCommands.regression;


import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class LassoRegression extends ScriptArgsRegressionAbstractClass {

    private JSpinner alphaSpinner = new JSpinner(new SpinnerNumberModel(0.01, 1e-10, 1.0, 1e-9));
    private JLabel alphaLabel = new JLabel("alpha = ", JLabel.LEFT);

    private JSpinner iterSpinner = new JSpinner(new SpinnerNumberModel(100, 10, 1e10, 100));
    private JLabel iterLabel = new JLabel("iter = ", JLabel.LEFT);

    {
        alphaSpinner.setName("alpha");
        iterSpinner.setName("iter");
    }

//    @Override
//    public @NotNull String getValueArg(@NotNull String key) {
//        List<Component> components = Arrays.asList(argPanel.getComponents());
//
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

        constraints.gridx = zeroGridX();
        constraints.gridy = incGridY();
        argPanel.add(iterLabel, constraints);
        constraints.gridx = incGridX();
        argPanel.add(iterSpinner, constraints);

        panel.add(argPanel);
        return panel;
    }
}
