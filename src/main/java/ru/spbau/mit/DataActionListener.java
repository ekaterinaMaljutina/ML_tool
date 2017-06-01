package ru.spbau.mit;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DataActionListener implements ActionListener {

    private JFrame jFrame = new JFrame();
    private JFileChooser fileChooser = new JFileChooser();
    private String fileName = null;

    @Nullable
    public String getChoosedFileName() {
        return fileName;
    }

    public boolean isFileNull() {
        return fileName == null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e);
        fileChooser.showDialog(jFrame, "choose file");
        fileName = fileChooser.getSelectedFile().getAbsolutePath();
    }


}
