package ru.spbau.mit;

import com.sun.istack.internal.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;


public class GUI extends JFrame {
    private static final String PATH_TO_RESOURCES = "src/main/resources/";

    private static final String PYTHON2 = "python2";

    private static Map<String, Script> scriptsClassification = new HashMap<>();
    private static Map<String, Script> scriptRegression = new HashMap<>();

    private JToolBar toolbar;
    private JButton datasetButton;
    private DataActionListener datasetListener;
    private JLabel methodLabel;
    private JFileChooser fileChooser;
    private JTextArea textArea;
    private JButton runScript;
    private JLabel taskLabel = new JLabel("Task :");

    private static JComboBox scriptChooser;
    private static JComboBox datasetChooser;
    private static JComboBox taskChooser = new JComboBox() {{
        addItem("regression");
        addItem("classification");
    }};

    private List<String> scriptNames = new ArrayList<>();
    private static List<String> datasetNames = new ArrayList<>(Arrays.asList("notMNIST.pickle", "noisysine.csv"));
    private static Map<String, String> nameToPath = new HashMap<String, String>() {{
        datasetNames.stream().forEach(s -> put(s, PATH_TO_RESOURCES));
    }};

    private scriptChosserActionLisner chosserActionLisner;

    {
        runScript = new JButton("Run");

        fileChooser = new JFileChooser();
        datasetButton = new JButton("Dataset: ");
        datasetListener = new DataActionListener();
        datasetButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fileChooser.showDialog(jFrame, "choose file");
                        if (fileChooser.getSelectedFile() != null) {
                            String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                            Map.Entry<String, String> pair = sptilFileNameOnPathAndName(fileName);
                            addToDatasetList(pair.getKey(), pair.getValue());
                        }
                    }

                    private Map.Entry<String, String> sptilFileNameOnPathAndName(String fileName) {
                        Path path = Paths.get(fileName);
                        String name = path.getFileName().toString();
                        String pathToFile = path.getParent().toString();
                        return new AbstractMap.SimpleEntry<String, String>(name, pathToFile);
                    }

                    private JFrame jFrame = new JFrame();
                }
        );
        taskChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scriptChooser.removeAllItems();
                switch ((String) taskChooser.getSelectedItem()) {
                    case "regression":
                        if (scriptRegression.isEmpty()) {
                            initScripts("regression");
                        } else {
                            scriptRegression.forEach((name, script) -> addToMethodList(name));
                        }
                        System.out.println("regrer");
                        break;
                    case "classification":
                        scriptsClassification.forEach((name, script) -> addToMethodList(name));
                        break;
                }
            }
        });
    }

    public GUI() {

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        add(createToolBar(), BorderLayout.PAGE_START);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel);
        JPanel panelLower = new JPanel();
        panelLower.setLayout(new BoxLayout(panelLower, BoxLayout.X_AXIS));
        textArea = new JTextArea(20, 0);
        textArea.setBounds(200, 100, 100, 100);
        textArea.setSize(400, 600);
        textArea.setLineWrap(true);

        textArea.setCaretPosition(textArea.getDocument().getLength());

        JScrollPane scrollPane = new JScrollPane(textArea);
        panelLower.add(scrollPane);

        add(panelLower);
        pack();
        setVisible(true);

        runScript.addActionListener(new scriptActiomListener(textArea));

    }

    public void addToToolbar(Component component) {
        Dimension d = component.getPreferredSize();
        component.setMaximumSize(d);
        component.setMinimumSize(d);
        component.setPreferredSize(d);
        toolbar.add(component);

    }

    public final JToolBar createToolBar() {
        toolbar = new JToolBar();

        addToToolbar(datasetButton);
        datasetChooser =
                new JComboBox(datasetNames.toArray());
        datasetChooser.setSelectedIndex(0);
        toolbar.putClientProperty("chooserData", datasetChooser);
        addToToolbar(datasetChooser);

        addToToolbar(taskLabel);
        addToToolbar(taskChooser);

        scriptChooser = new JComboBox(scriptNames.toArray());
        initScripts(taskChooser.getSelectedItem().toString());

        methodLabel = new JLabel("Method: ");
        addToToolbar(methodLabel);
        toolbar.putClientProperty("chooserScript", scriptChooser);
        addToToolbar(scriptChooser);

        addToToolbar(runScript);
        chosserActionLisner = new scriptChosserActionLisner(this, scriptNames.get(scriptChooser.getSelectedIndex()));
        scriptChooser.addActionListener(chosserActionLisner);
        return toolbar;
    }

    @NotNull
    public static String currentTask() {
        return taskChooser.getSelectedItem().toString();
    }

    private void addToDatasetList(@NotNull final String name, @NotNull final String path) {
        datasetNames.add(name);
        nameToPath.put(name, path);
        datasetChooser.addItem(name);
    }

    private void addToMethodList(@NotNull final String name) {
        scriptNames.add(name);
        scriptChooser.addItem(name);
    }


    private void initScripts(@NotNull final String task) {
        String path = PATH_TO_RESOURCES + task;
        try (Stream<String> stream = Files.lines(Paths.get(path + "/ArgsFile"))) {
            stream.forEach(line -> parseClassificationScriptLine(task, line, path));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void parseClassificationScriptLine(@NotNull final String task,
                                               @NotNull final String line, @NotNull final String path) {
        String[] nameAndArgs = line.split(":");
        String label = nameAndArgs[0].replaceAll(" ", "");
        addToMethodList(label);
        String[] args = nameAndArgs[1].split(",");
        facroryTast(task).put(label, new Script(args[0].replace(" ", ""), path, args));
    }

    @Nullable
    public static Script currentScript() {
        return facroryTast(taskChooser.getSelectedItem().toString()).get(scriptChooser.getSelectedItem().toString());
    }

    @Nullable
    public static String datasetName() {
        String name = datasetNames.get(datasetChooser.getSelectedIndex());
        return nameToPath.get(name) + name;
    }


    @NotNull
    private static Map<String, Script> facroryTast(@NotNull final String task) throws RuntimeException {
        switch (task) {
            case "classification":
                return scriptsClassification;
            case "regression":
                return scriptRegression;
            default:
                throw new RuntimeException();
        }
    }
}

