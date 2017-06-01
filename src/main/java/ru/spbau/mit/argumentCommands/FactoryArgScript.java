package ru.spbau.mit.argumentCommands;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.mit.argumentCommands.classification.*;
import ru.spbau.mit.argumentCommands.regression.LassoRegression;
import ru.spbau.mit.argumentCommands.regression.LinRegression;
import ru.spbau.mit.argumentCommands.regression.RidgeRegression;

public final class FactoryArgScript {

    @Nullable
    public static ArgsAbstactClass getArgsClassificationScriptByName(@NotNull final String nameScript) {
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

    @Nullable
    public static ArgsAbstactClass getArgsRegressionScriptByName(@NotNull final String nameScript) {
        switch (nameScript) {
            case "LinearRegression":
                return new LinRegression();
            case "RidgeRegression":
                return new RidgeRegression();
            case "Lasso":
                return new LassoRegression();
            default:
                return null;
        }
    }
}
