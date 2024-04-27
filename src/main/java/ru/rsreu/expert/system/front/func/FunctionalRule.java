package ru.rsreu.expert.system.front.func;

import lombok.RequiredArgsConstructor;
import ru.rsreu.expert.system.data.Variable;
import ru.rsreu.expert.system.rule.AbstractRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FunctionalRule<T extends Enum<T>> extends AbstractRule {
    private final List<VariableDefinition<T>> inputVariableDefinitions;
    private final Predicate<Map<T, Object>> predicate;
    private final VariableValue<T, Object> outputVariableValue;

    public static <T extends Enum<T>> FunctionalRule<T> of(List<VariableDefinition<T>> inputVariableDefinitions,
                                                           Predicate<Map<T, Object>> predicate,
                                                           VariableValue<T, Object> outputVariableValue) {
        return new FunctionalRule<>(inputVariableDefinitions, predicate, outputVariableValue);
    }

    @Override
    public Optional<Variable> apply(List<Variable> variables) {
        Map<T, Object> predicateMap = new HashMap<>();
        inputVariableDefinitions.forEach(inputVariableDefinition -> {
            String stringVariableValue = extractValue(variables, inputVariableDefinition.getVariableName().name());
            Object variableObject = inputVariableDefinition.getValueStringFactory().apply(stringVariableValue);
            VariableValue<T, Object> variableValue = new VariableValue<>(inputVariableDefinition, variableObject);
            predicateMap.put(
                    inputVariableDefinition
                            .getVariableName()
                            .getDeclaringClass()
                            .cast(inputVariableDefinition.getVariableName()),
                    variableValue.getValue()
            );
        });
        if (predicate.test(predicateMap)) {
            return Optional.of(new Variable(getOutputVariableName(), outputVariableValue.getValue().toString()));
        }
        return Optional.empty();
    }

    @Override
    public String getOutputVariableName() {
        return outputVariableValue.getDefinition().getVariableName().name();
    }

    @Override
    public List<String> getInputVariableNames() {
        return inputVariableDefinitions
                .stream()
                .map(VariableDefinition::getVariableName)
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
