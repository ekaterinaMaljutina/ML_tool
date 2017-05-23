package ru.spbau.mit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
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
            arguments.put(argName, VALUE_ARG);
        }
    }

    public boolean getArgValue(@NotNull final String key,
                               @NotNull final String value) {
        boolean res = arguments.containsKey(key);
        if (res) {
            arguments.put(key, value);
        }
        return res;
    }

    @Nullable
    public final String returnArgScript() {
        String res = "";
        return arguments.entrySet().stream()
                .filter(stringStringEntry -> stringStringEntry.getValue() != "")
                .map(entry -> getArg(entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(" "));
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

    @NotNull
    private String getArg(@NotNull final String name, @NotNull final String value) {

        return String.format(" -%s %s", name, value);
    }
}
