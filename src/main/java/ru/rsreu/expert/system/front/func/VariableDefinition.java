package ru.rsreu.expert.system.front.func;

import lombok.Getter;

import java.util.function.Function;

@Getter
public record VariableDefinition<T extends Enum<T>>(Enum<T> variableName, Function<String, Object> valueStringFactory) {
}
