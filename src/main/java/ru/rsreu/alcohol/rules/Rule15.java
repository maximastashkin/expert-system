package ru.rsreu.alcohol.rules;

import ru.rsreu.alcohol.enums.AlcoholVariable;
import ru.rsreu.alcohol.enums.values.DrinkValue;
import ru.rsreu.alcohol.enums.values.PriceValue;
import ru.rsreu.alcohol.enums.values.StrengthValue;
import ru.rsreu.alcohol.enums.values.TasteValue;
import ru.rsreu.expert.system.data.Variable;
import ru.rsreu.expert.system.rule.AbstractRule;

import java.util.List;
import java.util.Optional;

public class Rule15 extends AbstractRule {
    @Override
    public Optional<Variable> apply(List<Variable> variables) {
        String strength = extractValue(variables, AlcoholVariable.STRENGTH.getName());
        String price = extractValue(variables, AlcoholVariable.PRICE.getName());
        String taste = extractValue(variables, AlcoholVariable.TASTE.getName());
        if (strength.equals(StrengthValue.HIGH.getName())
                && !(price.equals(PriceValue.EXPENSIVE.getName()) || taste.equals(TasteValue.IMPORTANT.getName()))) {
            return Optional.of(new Variable(AlcoholVariable.DRINK.getName(), DrinkValue.VODKA.getName()));
        }
        return Optional.empty();
    }

    @Override
    public String getOutputVariableName() {
        return AlcoholVariable.DRINK.getName();
    }

    @Override
    public List<String> getInputVariableNames() {
        return List.of(
                AlcoholVariable.STRENGTH.getName(),
                AlcoholVariable.PRICE.getName(),
                AlcoholVariable.TASTE.getName()
        );
    }
}
