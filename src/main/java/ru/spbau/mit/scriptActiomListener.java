package ru.spbau.mit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public final class scriptActiomListener implements ActionListener {
    private static final String PYTHON2 = "python";

    private RunnerScript runnerScript;

    private final Map<String, Script> nameToScropt;
    private final List<String> datasetNames;
    private final Map<String, String> nameToPath;
    private final List<String> scriptNames;

    private JComboBox scriptChooser;
    private JComboBox datasetChooser;
    private JTextArea textArea;

    scriptActiomListener(Map<String, Script> nameToScropt, List<String> datasetNames, Map<String, String> nameToPath,
                         JComboBox datasetChooser, JTextArea textArea, List<String> scriptNames, JComboBox scriptChooser) {
        this.scriptNames = scriptNames;
        this.nameToPath = nameToPath;
        this.nameToScropt = nameToScropt;
        this.datasetNames = datasetNames;
        this.datasetChooser = datasetChooser;
        this.textArea = textArea;
        this.scriptChooser = scriptChooser;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        runnerScript = new RunnerScript();
        runnerScript.start();

    }

    private class RunnerScript extends Thread {
        @Override
        public void run() {
            Script currentScript = nameToScropt.get(scriptNames.get(scriptChooser.getSelectedIndex()));
            String scriptPath = currentScript.fullPath();
            String nameData = datasetNames.get(datasetChooser.getSelectedIndex());
            String pathToData = nameToPath.get(nameData);
            currentScript.getArgValue("data", pathToData+nameData);


            Runtime r = Runtime.getRuntime();
            ProcessBuilder processBuilder = new ProcessBuilder()
                    .command(PYTHON2, scriptPath, "--data", pathToData + nameData); //currentScript.returnArgScript());

            Process proc;
            processBuilder.command().stream().forEach(s -> System.out.print(s));
            System.out.println("");

            try {
                processBuilder.redirectErrorStream(true);
                proc = processBuilder.start();
                InputStream stdout = proc.getInputStream();

                IntupListenerProcess outListener = new IntupListenerProcess(stdout, textArea);
                outListener.start();
                outListener.join();

                int returnCode = proc.waitFor();
                System.out.println("myProcess: return code : " + returnCode);
                outListener.wait();
                proc.destroy();
                runnerScript.interrupt();
                interrupt();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                interrupt();
            }
        }
    }
}
