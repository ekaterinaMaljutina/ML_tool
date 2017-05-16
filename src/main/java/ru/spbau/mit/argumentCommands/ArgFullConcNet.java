package ru.spbau.mit.argumentCommands;


import javax.swing.*;
import java.awt.*;

public class ArgFullConcNet extends ScriptArgs {

    private static final String FORMAT = "0.000000";
    public static JSpinner lerningRate = new JSpinner(new SpinnerNumberModel(0.001, 0.00001,
            1.0, 0.00001));
    public static JPanel argPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
    public static JLabel label = new JLabel("lr = ", JLabel.TRAILING);
    

    @Override
    public JPanel drawComponents(JPanel panel) {
        return null;
    }
}
