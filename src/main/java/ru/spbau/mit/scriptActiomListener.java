package ru.spbau.mit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class scriptActiomListener implements ActionListener {
    private static final String PYTHON2 = "python";

    private RunnerScript runnerScript;
    private JTextArea textArea;

    scriptActiomListener(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        runnerScript = new RunnerScript();
        runnerScript.start();
    }

    private class RunnerScript extends Thread {
        @Override
        public void run() {
            scriptChosserActionLisner.zerosProgressBar();
            Script currentScript = GUI.currentScript();
            String scriptPath = currentScript.fullPath();

            currentScript.setArgValue("data", GUI.datasetName());
            currentScript.setOtherArgs();

            Runtime r = Runtime.getRuntime();
            System.out.println(scriptPath);

            List<String> args = new ArrayList<>();
            args.add(0, PYTHON2);
            args.add(1, scriptPath);
            args = currentScript.returnArgScript(args);

            ProcessBuilder processBuilder = new ProcessBuilder()
                    .command(args.toArray(new String[0]));
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
            scriptChosserActionLisner.updateProgressBar(100);
        }
    }
}
