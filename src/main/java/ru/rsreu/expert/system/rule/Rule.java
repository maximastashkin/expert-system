package ru.rsreu.expert.system.rule;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.rsreu.expert.system.data.Variable;
import ru.rsreu.expert.system.front.func.FunctionalRule;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@JsonSerialize(as = FunctionalRule.class)
@JsonDeserialize(as = FunctionalRule.class)
public interface Rule extends Serializable {
    Optional<Variable> apply(List<Variable> variables);

    String getOutputVariableName();

    List<String> getInputVariableNames();

    default String getRuleDescription() {
        return "";
    }
}
