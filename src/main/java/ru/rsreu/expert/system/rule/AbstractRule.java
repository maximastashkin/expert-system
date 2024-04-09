package ru.rsreu.expert.system.rule;

import ru.rsreu.alcohol.enums.AlcoholVariable;
import ru.rsreu.expert.system.data.Variable;
import ru.rsreu.expert.system.exception.NoSuchVariableException;

import java.util.List;
import java.util.Optional;

public abstract class AbstractRule implements Rule {
    private boolean applied = false;

    @Override
    public Optional<Variable> apply(List<Variable> variables) {
        this.applied = true;
        return Optional.empty();
    }

    @Override
    public boolean isApplied() {
        return this.applied;
    }

    protected String extractValue(List<Variable> variables, AlcoholVariable variable) {
        return variables.stream()
                .filter(it -> it.getName().equals(variable.getName()))
                .findFirst()
                .map(Variable::getValue)
                .orElseThrow(() -> new NoSuchVariableException(variable.getName()));
    }
}
