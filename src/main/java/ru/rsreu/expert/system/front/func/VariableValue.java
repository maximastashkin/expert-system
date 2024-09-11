package ru.rsreu.expert.system.front.func;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public record VariableValue<T extends Enum<T>, E>(VariableDefinition<T> definition, E value) {
    public static <T extends Enum<T>, E> VariableValue<T, E> of(VariableDefinition<T> definition, E value) {
        return new VariableValue<>(definition, value);
    }
}
