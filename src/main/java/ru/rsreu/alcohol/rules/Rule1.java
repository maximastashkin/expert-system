package ru.rsreu.alcohol.rules;

import ru.rsreu.alcohol.enums.AlcoholVariable;
import ru.rsreu.alcohol.enums.values.EffectValue;
import ru.rsreu.alcohol.enums.values.StrengthValue;
import ru.rsreu.expert.system.data.Variable;
import ru.rsreu.expert.system.rule.AbstractRule;

import java.util.List;
import java.util.Optional;

public class Rule1 extends AbstractRule {
    @Override
    public Optional<Variable> apply(List<Variable> variables) {
        super.apply(variables);
        String effect = extractValue(variables, AlcoholVariable.EFFECT);
        int people = Integer.parseInt(extractValue(variables, AlcoholVariable.PEOPLE));
        if (effect.equals(EffectValue.STRONG.getName()) && people > 5) {
            return Optional.of(new Variable(AlcoholVariable.STRENGTH.getName(), StrengthValue.HIGH.getName()));
        }
        return Optional.empty();
    }

    @Override
    public String getOutputVariableName() {
        return AlcoholVariable.STRENGTH.getName();
    }

    @Override
    public List<String> getInputVariableNames() {
        return List.of(
                AlcoholVariable.EFFECT.getName(),
                AlcoholVariable.PEOPLE.getName()
        );
    }
}
