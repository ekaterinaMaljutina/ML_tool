package ru.spbau.mit;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

public class IntupListenerProcess extends Thread {
    private final InputStream is;
    private final JTextArea out;

    public IntupListenerProcess(InputStream s, JTextArea textArea) {
        is = s;
        out = textArea;
    }

    @Override
    public void run() {

        try {
            int value = -1;
            while ((value = is.read()) != -1) {
                out.append(String.valueOf((char) value));
                out.setCaretPosition(out.getDocument().getLength());
            }
        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }
}
