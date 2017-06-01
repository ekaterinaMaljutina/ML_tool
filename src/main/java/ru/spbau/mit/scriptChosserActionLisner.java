package ru.spbau.mit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.mit.argumentCommands.ArgsAbstactClass;
import ru.spbau.mit.argumentCommands.FactoryArgScript;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class scriptChosserActionLisner implements ActionListener {

    private static final String FORMAT = "0.000000";

    private static JPanel panel;
    private static ArgsAbstactClass currentScript;

    private Frame currenrFrame;
    private static JProgressBar progressBar = new JProgressBar();
    private static int valueProgressBar = 0;

    public scriptChosserActionLisner(@NotNull Frame frame,
                                     @NotNull final String initNameScript) {
        currenrFrame = frame;
        panel = new JPanel();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        panel.add(progressBar);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        currenrFrame.add(panel, BorderLayout.WEST);
        currenrFrame.repaint();
        factoryTaskChooserArgs(GUI.currentTask(), initNameScript);

    }

    public static void updateProgressBar(int value) {
        valueProgressBar += value;
        progressBar.setValue(valueProgressBar);
    }

    public static void zerosProgressBar() {
        valueProgressBar = 0;
        progressBar.setValue(valueProgressBar);
    }

    public static @Nullable String getPanelValue(@NotNull final String name) {
        if (currentScript != null) {
            return currentScript.getValueArg(name);
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        panel.removeAll();

        zerosProgressBar();
        panel.add(progressBar);
        List<String[]> getSelectItem = Arrays.stream(e.getSource()
                .toString().split(","))
                .filter(s -> s.split("=").length == 2)
                .map(s -> s.split("="))
                .collect(Collectors.groupingBy(o -> o[0]))
                .get("selectedItemReminder");
        if (getSelectItem.size() != 1 && getSelectItem.get(0).length != 2) {
            return;
        }
        String currentScript = getSelectItem.get(0)[1].replace("]", "");

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        factoryTaskChooserArgs(GUI.currentTask(), currentScript);
        panel.repaint();
        panel.revalidate();
    }

    private void factoryTaskChooserArgs(@NotNull final String task, @NotNull final String scriptName) {
        currentScript = null;
        switch (task) {
            case "classification":
                currentScript = FactoryArgScript.getArgsClassificationScriptByName(scriptName);
                break;
            case "regression":
                currentScript = FactoryArgScript.getArgsRegressionScriptByName(scriptName);
                break;
        }
        if (currentScript != null) {
            currentScript.drawComponents(panel);
        }
    }
}
