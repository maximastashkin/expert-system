package ru.rsreu.demo.view.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.rsreu.alcohol.enums.AlcoholVariable;
import ru.rsreu.expert.system.data.Context;
import ru.rsreu.expert.system.data.Variable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ViewPrettyVariablesUtils {
    private static final String VARIABLE_PRETTY_TEMPLATE = "%s: %s";

    public static List<String> getVariablesFromContext(Context context) {
        return Arrays.stream(AlcoholVariable.values())
                .filter(variable -> context.exists(variable.name()))
                .map(alcoholVariable -> {
                    Variable variable = context.get(alcoholVariable.name());
                    Function<String, String> variableValueConverter = AlcoholVariable.valueOf(variable.getName())
                            .getConverter();
                    return prepareVariableString(variable, variableValueConverter);
                })
                .toList();
    }

    public static String prepareVariableString(Variable variable, Function<String, String> converter) {
        return String.format(
                VARIABLE_PRETTY_TEMPLATE,
                AlcoholVariable.valueOf(variable.getName()).getName(),
                converter.apply(variable.getValue())
        );
    }
}
