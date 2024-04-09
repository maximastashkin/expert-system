package ru.rsreu.alcohol.rules;

import ru.rsreu.alcohol.enums.AlcoholVariable;
import ru.rsreu.alcohol.enums.values.EffectValue;
import ru.rsreu.alcohol.enums.values.StrengthValue;
import ru.rsreu.expert.system.rule.AbstractRule;
import ru.rsreu.expert.system.data.Variable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Rule3 extends AbstractRule {
    @Override
    public Optional<Variable> apply(List<Variable> variables) {
        super.apply(variables);
        String effect = extractValue(variables, AlcoholVariable.EFFECT.getName());
        if (effect.equals(EffectValue.EASY.getName())) {
            return Optional.of(new Variable(AlcoholVariable.STRENGTH.getName(), StrengthValue.LOW.getName()));
        }
        return Optional.empty();
    }

    @Override
    public String getOutputVariableName() {
        return AlcoholVariable.STRENGTH.getName();
    }

    @Override
    public List<String> getInputVariableNames() {
        return Collections.singletonList(AlcoholVariable.EFFECT.getName());
    }
}
