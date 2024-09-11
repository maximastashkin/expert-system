package ru.rsreu.expert.system.rule;

import ru.rsreu.expert.system.data.Variable;
import ru.rsreu.expert.system.exception.NoSuchVariableException;

import java.util.List;

public abstract class AbstractRule implements Rule {
    protected String extractValue(List<Variable> variables, String name) {
        return variables.stream()
                .filter(it -> it.name().equals(name))
                .findFirst()
                .map(Variable::value)
                .orElseThrow(() -> new NoSuchVariableException(name));
    }
}
