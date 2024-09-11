package ru.rsreu.expert.system.front.func;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.RequiredArgsConstructor;
import ru.rsreu.expert.system.data.Variable;
import ru.rsreu.expert.system.rule.AbstractRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class FunctionalRule<T extends Enum<T>> extends AbstractRule {
    private final List<VariableDefinition<T>> inputVariableDefinitions;
    private final Predicate<Map<T, Object>> predicate;
    private final VariableValue<T, Object> outputVariableValue;
    private final String ruleDescription;

    @JsonCreator
    public static <T extends Enum<T>> FunctionalRule<T> of(
            @JsonProperty("ruleDescription") String ruleDescription,
            @JsonProperty("inputVariableDefinitions") List<VariableDefinition<T>> inputVariableDefinitions,
            @JsonProperty("predicate") Predicate<Map<T, Object>> predicate,
            @JsonProperty("outputVariableValue") VariableValue<T, Object> outputVariableValue) {
        return new FunctionalRule<>(inputVariableDefinitions, predicate, outputVariableValue, ruleDescription);
    }

    @Override
    public Optional<Variable> apply(List<Variable> variables) {
        Map<T, Object> predicateMap = new HashMap<>();
        inputVariableDefinitions.forEach(inputVariableDefinition -> {
            String stringVariableValue = extractValue(variables, inputVariableDefinition.variableName().name());
            Object variableObject = inputVariableDefinition.valueStringFactory().apply(stringVariableValue);
            VariableValue<T, Object> variableValue = new VariableValue<>(inputVariableDefinition, variableObject);
            predicateMap.put(
                    inputVariableDefinition
                            .variableName()
                            .getDeclaringClass()
                            .cast(inputVariableDefinition.variableName()),
                    variableValue.value()
            );
        });
        if (predicate.test(predicateMap)) {
            return Optional.of(new Variable(getOutputVariableName(), outputVariableValue.value().toString()));
        }
        return Optional.empty();
    }

    @Override
    @JsonIgnore
    public String getOutputVariableName() {
        return outputVariableValue.definition().variableName().name();
    }

    @Override
    @JsonIgnore
    public List<String> getInputVariableNames() {
        return inputVariableDefinitions.stream()
                .map(VariableDefinition::variableName)
                .map(Enum::name)
                .toList();
    }

    @JsonSerialize
    @Override
    public String getRuleDescription() {
        return ruleDescription;
    }
}
