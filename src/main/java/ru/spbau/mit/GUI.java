package ru.spbau.mit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;


public class GUI extends JFrame {
    private static final String PATH_TO_RESOURCES = "src/main/resources/";

    private static final String RUN_SCRIPT = "%s  %s  %s ";
    private static final String PYTHON2 = "python2";

    private static final Map<String, Script> SCRIPTS_CLASSIFICATION = new HashMap<String, Script>() {{
        put("test", new Script("test", PATH_TO_RESOURCES));
        put("train", new Script("train", PATH_TO_RESOURCES, "data"));
        put("FullConNet", new Script("FullConNet", PATH_TO_RESOURCES, "data", "func_act", "lr"));
        put("Conv1AndFullConnect", new Script("Conv1AndFullConnect", PATH_TO_RESOURCES,
                "data", "fucn_act", "lr"));
        put("Conv2AndFullConc", new Script("Conv2AndFullConc", PATH_TO_RESOURCES, "data", "lr"));
        put("Conv2DropAndFull", new Script("Conv2DropAndFull", PATH_TO_RESOURCES, "data", "drop", "lr"));
    }};
    private static final Map<String, Script> SCRIPT_REGRESSION = new HashMap<String, Script>() {{

    }};

    private JToolBar toolbar;
    private JButton datasetButton;
    private JComboBox datasetChooser;
    private DataActionListener datasetListener;
    private JLabel methodLabel;
    private JComboBox scriptChooser;
    private JFileChooser fileChooser;
    private JTextArea textArea;
    private JButton runScript;

    private JLabel taskLabel = new JLabel("Task :");
    private JComboBox taskChooser = new JComboBox() {{
        addItem("regression");
        addItem("classification");
        setSelectedIndex(1);
    }};

    private List<String> scriptNames = new ArrayList<>();
    private List<String> datasetNames = new ArrayList<>(Arrays.asList("notMNIST.pickle"));
    private Map<String, String> nameToPath = new HashMap<String, String>() {{
        put(datasetNames.get(0), PATH_TO_RESOURCES);
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
                switch ((String)taskChooser.getSelectedItem()) {
                    case "regression":
                        System.out.println("regrer");
                        break;
                    case "classification":
                        SCRIPTS_CLASSIFICATION.forEach((name, script) -> addToMethodList(name));
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

        runScript.addActionListener(new scriptActiomListener(SCRIPTS_CLASSIFICATION, datasetNames, nameToPath,
                datasetChooser, textArea, scriptNames, scriptChooser));

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
        SCRIPTS_CLASSIFICATION.forEach((name, script) -> addToMethodList(name));

        methodLabel = new JLabel("Method: ");
        addToToolbar(methodLabel);
        toolbar.putClientProperty("chooserScript", scriptChooser);
        addToToolbar(scriptChooser);

        addToToolbar(runScript);
        chosserActionLisner = new scriptChosserActionLisner(this, scriptNames.get(scriptChooser.getSelectedIndex()));
        scriptChooser.addActionListener(chosserActionLisner);
        return toolbar;
    }

    private void addToDatasetList(String name, String path) {
        datasetNames.add(name);
        nameToPath.put(name, path);
        datasetChooser.addItem(name);
    }

    private void addToMethodList(String name) {
        scriptNames.add(name);
        scriptChooser.addItem(name);
    }
}

