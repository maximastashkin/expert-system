package ru.rsreu.alcohol;

import ru.rsreu.expert.system.exception.NoSuchVariableException;
import ru.rsreu.expert.system.data.Context;
import ru.rsreu.expert.system.data.Variable;

import java.util.ArrayList;
import java.util.List;

public class CacheContext implements Context {
    private final List<Variable> variables = new ArrayList<>();

    @Override
    public boolean exists(String name) {
        return variables.stream()
                .anyMatch(it -> name.equals(it.getName()));
    }

    @Override
    public Variable get(String name) {
        return variables.stream()
                .filter(it -> name.equals(it.getName()))
                .findAny()
                .orElseThrow(() -> new NoSuchVariableException(name));
    }

    @Override
    public void add(Variable variable) {
        variables.stream()
                .filter(it -> variable.getName().equals(it.getName()))
                .findAny()
                .ifPresent(variables::remove);
        variables.add(variable);
    }
}
