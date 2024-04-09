package ru.rsreu.alcohol.rules;

import ru.rsreu.alcohol.enums.AlcoholVariable;
import ru.rsreu.alcohol.enums.values.DrinkValue;
import ru.rsreu.alcohol.enums.values.StrengthValue;
import ru.rsreu.expert.system.data.Variable;
import ru.rsreu.expert.system.rule.AbstractRule;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Rule10 extends AbstractRule {
    @Override
    public Optional<Variable> apply(List<Variable> variables) {
        super.apply(variables);
        String strength = extractValue(variables, AlcoholVariable.STRENGTH);
        if (strength.equals(StrengthValue.LOW.getName())) {
            return Optional.of(new Variable(AlcoholVariable.DRINK.getName(), DrinkValue.BEER.getName()));
        }
        return Optional.empty();
    }

    @Override
    public String getOutputVariableName() {
        return AlcoholVariable.DRINK.getName();
    }

    @Override
    public List<String> getInputVariableNames() {
        return Collections.singletonList(AlcoholVariable.STRENGTH.getName());
    }
}
