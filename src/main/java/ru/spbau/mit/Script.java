package ru.spbau.mit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public final class Script {
    private static final String VALUE_ARG = "";

    private final String name;
    private final String path;
    private final String fullPath;
    private Map<String, String> arguments = new HashMap<>();

    public Script(@NotNull final String path,
                  final String[] args) {
        if (args.length == 0)
            throw new RuntimeException("arguments script in not valid");
        this.name = args[0];
        this.path = path.replaceAll(" ", "");
        Path p = Paths.get(path + "/" + name);
        fullPath = p.toAbsolutePath().getParent().toString();

        for (String argName : args) {
            if (argName.equals(name))
                continue;
            argName = argName.replaceAll(" ", "");

            if (argName.split("#").length == 1) {
                arguments.put(argName, VALUE_ARG);
            } else {
                String[] initValue = argName.split("#");
                arguments.put(initValue[0], initValue[1]);
            }
        }
    }

    public int sizeArgs() {
        return arguments.size();
    }

    public String getValueArgs(@NotNull final String key) {
        return arguments.get(key);
    }

    public Set<String> getKeyArgs() {
        return arguments.keySet();
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

    @NotNull
    public final List<String> returnArgScript(@NotNull List<String> args) {
        args.add(path + "/" + name + ".py");
        for (Map.Entry entry : arguments.entrySet()) {
            if (entry.getValue() != "") {
                args.add("--" + entry.getKey());
                args.add(entry.getValue().toString());
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
