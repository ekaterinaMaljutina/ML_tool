package ru.spbau.mit;


import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProcessBuider extends Thread {
    private static final String PYTHON2 = "python";
    List<String> currentArgs;

    public ProcessBuider(@NotNull final List<String> args) { // data  - path, arg - value ...
        currentArgs = args;
    }

    @Override
    public void run() {
        Runtime r = Runtime.getRuntime();

        ProcessBuilder processBuilder = new ProcessBuilder()
                .command(currentArgs);
        Process proc;
        processBuilder.command().forEach(s -> System.out.print(s));
        System.out.println("");

        try {
            processBuilder.redirectErrorStream(true);
            proc = processBuilder.start();
            InputStream stdout = proc.getInputStream();

            IntupListenerProcess outListener = new IntupListenerProcess(stdout, null);
            outListener.start();
            outListener.join();
            int returnCode = proc.waitFor();
            System.out.println("myProcess: return code : " + returnCode);
            outListener.wait();
            proc.destroy();
            interrupt();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            interrupt();
        }
//        scriptChosserActionLisner.updateProgressBar(100);
    }
}
