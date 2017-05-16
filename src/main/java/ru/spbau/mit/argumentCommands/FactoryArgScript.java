package ru.spbau.mit.argumentCommands;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FactoryArgScript {

    @Nullable
    public static ScriptArgs getArgsScriptByName(@NotNull final String nameScript) {
        switch (nameScript) {
            case "test":
                return null;
            case "train":
                return new ArgTrainScritp();
            default:
                return null;
        }
    }
}
