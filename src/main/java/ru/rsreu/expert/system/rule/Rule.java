package ru.rsreu.expert.system.rule;

import ru.rsreu.expert.system.data.Variable;

import java.util.List;
import java.util.Optional;

public interface Rule {
    Optional<Variable> apply(List<Variable> variables);
    String getOutputVariableName();
    List<String> getInputVariableNames();
    boolean isApplied();
}
