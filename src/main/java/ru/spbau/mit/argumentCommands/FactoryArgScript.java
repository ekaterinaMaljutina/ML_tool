package ru.spbau.mit.argumentCommands;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FactoryArgScript {

    @Nullable
    public static ScriptArgsAbstractClass getArgsScriptByName(@NotNull final String nameScript) {
        switch (nameScript) {
            case "test":
                return null;
            case "train":
                return new ArgTrainScritp();
            case "FullConNet":
                return new ArgFullConcNet();
            case "Conv1AndFullConnect":
                return new ArgConv1Full();
            case "Conv2AndFullConc":
                return new ArgConv2Full();
            case "Conv2DropAndFull":
                return new Conv2Drop();
            default:
                return null;
        }
    }
}
