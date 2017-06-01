package ru.spbau.mit;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntupListenerProcess extends Thread {
    private final BufferedReader is;
    private final JTextArea out;

    public IntupListenerProcess(InputStream s, @Nullable JTextArea textArea) {
        is = new BufferedReader(new InputStreamReader(s));
        out = textArea;
    }

    @Override
    public void run() {
        double sum = 0;
        try {
            String value = null;
            while ((value = is.readLine()) != null) {
                if (out == null) {
                    System.out.println(value);
                } else {
                    out.append(value + "\n");
                    out.setCaretPosition(out.getDocument().getLength());
                }
                if (GUI.isClassification()) {
                    if (value.contains("time")) {
                        Pattern pattern = Pattern.compile("time:(.*?)s");
                        Matcher matcher = pattern.matcher(value);
                        if (matcher.find()) {
                            double val = Double.parseDouble(matcher.group(1));
                            sum += 1.0 / (20.0) * 100 / 100; // 100 % / count epoch
                            System.out.println(sum);
                            if (sum > 1) {
                                scriptChosserActionLisner.updateProgressBar(1);
                                sum = 0;
                            }

                        }
                    }
                } else {
                    scriptChosserActionLisner.updateProgressBar(value.length());
                }
            }
        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }
}
