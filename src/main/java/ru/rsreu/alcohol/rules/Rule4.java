package ru.rsreu.alcohol.rules;

import ru.rsreu.alcohol.enums.AlcoholVariable;
import ru.rsreu.alcohol.enums.values.EffectValue;
import ru.rsreu.alcohol.enums.values.StrengthValue;
import ru.rsreu.expert.system.data.Variable;
import ru.rsreu.expert.system.rule.AbstractRule;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Rule4 extends AbstractRule {
    @Override
    public Optional<Variable> apply(List<Variable> variables) {
        super.apply(variables);
        String effect = extractValue(variables, AlcoholVariable.EFFECT);
        if (effect.equals(EffectValue.MIDDLE.getName())) {
            return Optional.of(new Variable(AlcoholVariable.STRENGTH.getName(), StrengthValue.MEDIUM.getName()));
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
