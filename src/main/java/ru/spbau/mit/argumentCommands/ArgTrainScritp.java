package ru.spbau.mit.argumentCommands;

import javax.swing.*;
import java.awt.*;

public class ArgTrainScritp {

    private static final String FORMAT = "0.000000";
    public static JSpinner lerningRate = new JSpinner(new SpinnerNumberModel(0.001, 0.00001, 1.0, 0.00001));
    public static JPanel argPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
    public static JLabel label = new JLabel("lr = ", JLabel.TRAILING);

    static {
        lerningRate.setEditor(new JSpinner.NumberEditor(lerningRate, FORMAT));
    }

    public static JPanel drawComponents(JPanel panel) {
        argPanel.add(label, BorderLayout.LINE_START);
        argPanel.add(lerningRate);
        argPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        panel.add(argPanel);
        return panel;
    }

}
