package ru.spbau.mit;

import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.argumentCommands.FactoryArgScript;
import ru.spbau.mit.argumentCommands.ScriptArgsAbstractClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class scriptChosserActionLisner implements ActionListener {

    private static final String FORMAT = "0.000000";

    private Frame currenrFrame;
    private JPanel panel;

    public scriptChosserActionLisner(@NotNull Frame frame,
                                     @NotNull final String initNameScript) {
        currenrFrame = frame;
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        currenrFrame.add(panel, BorderLayout.WEST);
        currenrFrame.repaint();
        drawParamForScript(initNameScript);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        panel.removeAll();
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
        drawParamForScript(currentScript);
        panel.repaint();
        panel.revalidate();
    }

    private void drawParamForScript(@NotNull final String scriptName) {
        ScriptArgsAbstractClass currentScript = FactoryArgScript.getArgsScriptByName(scriptName);
        if (currentScript != null) {
            currentScript.drawComponents(panel);
        }
//        switch (scriptName) {
//            case "test":
//                panel = ArgTrainScritp.drawComponents(panel);
//                System.out.println("is test");
//                break;
//            case "train":
////                button2 = new JButton("Button 1 Train");
////                this.panel.add(button2);
////                panel.add(new JSeparator(JSeparator.HORIZONTAL),
////                        BorderLayout.AFTER_LAST_LINE);
////                this.panel.add(new JSeparator(SwingConstants.VERTICAL));
////                button = new JButton("Button 2 Train");
////                panel.add(button);
////                panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
//
//                System.out.println("is train");
//                break;
//            default:
//                throw new UnrecoverableKeyException("not found script with current name");
//        }
    }
}
