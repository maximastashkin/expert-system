package ru.rsreu.expert.system.front.func;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class VariableValue<T extends Enum<T>, E> {
    private final VariableDefinition<T> definition;
    private final E value;

    public static <T extends Enum<T>, E> VariableValue<T, E> of(VariableDefinition<T> definition, E value) {
        return new VariableValue<>(definition, value);
    }
}
