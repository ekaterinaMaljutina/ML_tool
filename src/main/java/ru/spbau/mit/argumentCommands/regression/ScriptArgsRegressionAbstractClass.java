package ru.spbau.mit.argumentCommands.regression;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.mit.argumentCommands.ArgsAbstactClass;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public abstract class ScriptArgsRegressionAbstractClass extends ArgsAbstactClass {

    protected JPanel argPanel;

    private JLabel degreeLabel = new JLabel("degree = ", JLabel.CENTER);
    private JSpinner degree = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));

    private JLabel splitLabel = new JLabel("split = ", JLabel.CENTER);
    private JSpinner splitSpinner = new JSpinner(new SpinnerNumberModel(0.1, 0.1, 0.9, 0.05));

    protected GridBagConstraints constraints = new GridBagConstraints();

    {
        degree.setName("degree");
        splitSpinner.setName("split");
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        argPanel = new JPanel();
        argPanel.setLayout(new GridBagLayout());
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.5;
    }

    @Override
    public @Nullable String getValueArg(@NotNull String key) {
        java.util.List<Component> components = Arrays.asList(argPanel.getComponents());
        for (Component component : components) {
            if (component.getName() != null && component.getName().equals(key)) {
                JSpinner spinner = (JSpinner)component;
                System.out.println(component.getName() + " " + key + " " + key.equals(component.getName()) +
                        " " + spinner.getValue().toString());
                return spinner.getValue().toString();
            }
        }
        return null;
    }


    protected void init() {
        //degree
        constraints.gridx = zeroGridX();
        constraints.gridy = zeroGridY();
        constraints.fill = GridBagConstraints.PAGE_START;
        argPanel.add(degreeLabel, constraints);
        constraints.gridx = incGridX();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        argPanel.add(degree, constraints);

        // split
        constraints.gridx = zeroGridX();
        constraints.gridy = incGridY();
        constraints.fill = GridBagConstraints.LINE_START;
        argPanel.add(splitLabel, constraints);
        constraints.gridx = incGridX();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        argPanel.add(splitSpinner, constraints);

    }
}
