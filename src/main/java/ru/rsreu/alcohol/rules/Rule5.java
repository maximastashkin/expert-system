package ru.rsreu.alcohol.rules;

import ru.rsreu.alcohol.enums.AlcoholVariable;
import ru.rsreu.alcohol.enums.values.PriceValue;
import ru.rsreu.expert.system.data.Variable;
import ru.rsreu.expert.system.rule.AbstractRule;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Rule5 extends AbstractRule {
    @Override
    public Optional<Variable> apply(List<Variable> variables) {
        int money = Integer.parseInt(extractValue(variables, AlcoholVariable.MONEY.getName()));
        if (money < 1000) {
            return Optional.of(new Variable(AlcoholVariable.PRICE.getName(), PriceValue.CHEAP.getName()));
        }
        return Optional.empty();
    }

    @Override
    public String getOutputVariableName() {
        return AlcoholVariable.PRICE.getName();
    }

    @Override
    public List<String> getInputVariableNames() {
        return Collections.singletonList(AlcoholVariable.MONEY.getName());
    }
}
