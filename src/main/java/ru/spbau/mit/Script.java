package ru.spbau.mit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Script {
    private static final String VALUE_ARG = "";

    private final String name;
    private final String path;
    private final String fullPath;
    private Map<String, String> arguments = new HashMap<>();

    public Script(@NotNull final String name, @NotNull final String path,
                  String[] args) {
        this.name = name;
        this.path = path;
        Path p = Paths.get(path + "/" + name);
        fullPath = p.toAbsolutePath().getParent().toString();

        for (String argName : args) {
            arguments.put(argName.replaceAll(" ", ""), VALUE_ARG);
        }
    }

    public boolean setArgValue(@NotNull final String key,
                               @NotNull final String value) {
        boolean res = arguments.containsKey(key);
        if (res) {
            arguments.put(key, value);
        }
        return res;
    }

    public void setOtherArgs() {
        arguments.forEach((key, value) -> {
            String arg = scriptChosserActionLisner.getPanelValue(key);
            if (arg != null) {
                setArgValue(key, arg);
            }
        });
    }

    @Nullable
    public final List<String> returnArgScript() {
        String res = "";
        List<String> values = arguments.values().stream().collect(Collectors.toList());
        List<String> keys = arguments.keySet().stream().collect(Collectors.toList());
        List<String> args = new ArrayList<>();

        for (int i = 0; i < keys.size(); i++) {
            if (values.get(i) != "") {
                args.add("--" + keys.get(i));
                args.add(values.get(i));
            }
        }

        return args;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getPath() {
        return path;
    }

    public String fullPath() {
        return fullPath + "/" + name + ".py";
    }
}
