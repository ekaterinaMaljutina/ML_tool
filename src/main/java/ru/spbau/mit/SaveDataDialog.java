package ru.spbau.mit;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveDataDialog extends JFrame
        implements ActionListener{

    private JFileChooser fileChooser = new JFileChooser();

    private JTextField filename = new JTextField();
    private JTextField dir = new JTextField();


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        fileChooser.setApproveButtonText("save");
        int rVal = fileChooser.showOpenDialog(this);
        if (rVal == JFileChooser.APPROVE_OPTION) {

            filename.setText(fileChooser.getSelectedFile().getName());
            System.out.println(filename.getText());
            dir.setText(fileChooser.getCurrentDirectory().toString());
        }
        if (rVal == JFileChooser.CANCEL_OPTION) {
            filename.setText("You pressed cancel");
            dir.setText("");
        }
    }

}
