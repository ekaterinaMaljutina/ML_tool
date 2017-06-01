package ru.spbau.mit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TFActionLisner implements ActionListener {

    private static final String PATH_TO_SCRIPT = "/home/kate/SE_16-18/ML-TOOL/ML-tool-py/test.py";
    //            "/home/kate/SE_16-18/ML-TOOL/ML-tool-py/CNN/train.py";
    private static final String RUN_SCRIPT = "%s  %s ";// --file %s"; //   --data  %s";
    private static final String PYTHON3 = "python3";
    private JFrame jFrame = new JFrame();

    private DataActionListener dataActionListener = null;
    private SaveDataDialog saveDataDialog = new SaveDataDialog();
    private JTextArea textArea;

    public void setDataActionListener(@NotNull DataActionListener data) {
        dataActionListener = data;
    }

    @Nullable
    public String getPathToDataSet() {
        return dataActionListener.getChoosedFileName();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        jFrame.setSize(300, 300);
        jFrame.setVisible(true);

        textArea = new JTextArea();
        textArea.setSize(300, 300);
        jFrame.add(textArea);

        creadeMenuBar();

        // if (!dataActionListener.isFileNull()) {
        System.out.println(dataActionListener.getChoosedFileName());
        try {


            String file_name_output = "/home/kate/SE_16-18/ML-TOOL/ML-tool/output";
            File output = new File(file_name_output);
            String command = String.format(RUN_SCRIPT, PYTHON3,
                    PATH_TO_SCRIPT, file_name_output); //, dataActionListener.getChoosedFileName());
            System.out.println("run command: " + command);

            Runtime r = Runtime.getRuntime();
            Process proc = r.exec(command);
            proc.waitFor();
            try (BufferedReader bf = new BufferedReader(new
                    FileReader(output))) {

                String s;
                while ((s = bf.readLine()) != null) {
                    System.out.println(s);
                    textArea.append(s + "\n");
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                System.out.println("error with output file");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        //}

    }

    private void creadeMenuBar() {
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;


        menuBar = new JMenuBar();

        menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);
        menuItem = new JMenuItem("Save File");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                JFrame frame = new JFrame();
                JTextField filename = new JTextField();
                JTextField dir = new JTextField();

                System.out.println(e.getActionCommand());
                fileChooser.setApproveButtonText("Save");


                int rVal = fileChooser.showOpenDialog(frame);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    filename.setText(fileChooser.getCurrentDirectory().getName() + "/" + fileChooser.getSelectedFile().getName());
                    System.out.println(textArea.getText());
                    System.out.println("save to file " + filename.getText() + " " + filename.getName());
                    try {
                        Files.write(Paths.get("/home/" + filename.getText()), textArea.getText().getBytes(), StandardOpenOption.CREATE);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }

                    dir.setText(fileChooser.getCurrentDirectory().toString());
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                    filename.setText("You pressed cancel");
                    dir.setText("");
                }
            }
        });
        menu.add(menuItem);


        menuBar.add(menu);
        jFrame.setJMenuBar(menuBar);
    }
}


