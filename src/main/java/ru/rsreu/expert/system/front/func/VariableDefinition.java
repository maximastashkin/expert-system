package ru.rsreu.expert.system.front.func;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public class VariableDefinition<T extends Enum<T>> {
    private final Enum<T> variableName;
    private final Function<String, Object> valueStringFactory;
}
