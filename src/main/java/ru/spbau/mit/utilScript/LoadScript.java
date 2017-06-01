package ru.spbau.mit.utilScript;

import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.Script;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class LoadScript {

    private static Map<String, Script> labelToScript = new HashMap<>();
    private static String pathToScript;

    public static void LoadArgs(@NotNull final String path, @NotNull final String pathScript) {
        labelToScript.clear();
        pathToScript = pathScript;
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            stream.forEach(line -> lineToScript(line));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Map<String, Script> getLabelToScriptMap() {
        return labelToScript;
    }

    public static int getSizeArgByLabel(@NotNull final String label) {
        return labelToScript.get(label).sizeArgs();
    }

    public static Set<String> getSetArgByLabel(@NotNull final String label) {
        return labelToScript.get(label).getKeyArgs();
    }

    public static void setValue(@NotNull final String nameScript, @NotNull final String argName,
                                @NotNull final String value) {
        labelToScript.get(nameScript).setArgValue(argName, value);
    }

    public static String getInitArgsValueByLabel(@NotNull final String label,
                                                 @NotNull final String key) {
        return labelToScript.get(label).getValueArgs(key);
    }

    public static List<String> getArgsListWithValueByLabel(@NotNull final String label,
                                                           @NotNull List<String> value) {
        return labelToScript.get(label).returnArgScript(value);
    }

    private static void lineToScript(@NotNull String line) {
        String[] nameAndArgs = line.split(":");
        String label = nameAndArgs[0].replaceAll(" ", "");
        initArgs(label, nameAndArgs[1].replaceAll(" ", "").split(","));
    }

    private static void initArgs(@NotNull final String key, String[] params) {
        if (params.length == 0)
            return;
        labelToScript.put(key, new Script(pathToScript, params));
    }

}

