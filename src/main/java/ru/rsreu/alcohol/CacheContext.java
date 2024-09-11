package ru.rsreu.alcohol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import ru.rsreu.expert.system.data.Context;
import ru.rsreu.expert.system.data.Variable;
import ru.rsreu.expert.system.exception.NoSuchVariableException;
import ru.rsreu.expert.system.rule.Rule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CacheContext implements Context {
    private final List<Variable> variables;

    private final List<Rule> usedRules;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime timestamp;

    public CacheContext() {
        this.variables = new ArrayList<>();
        this.usedRules = new ArrayList<>();
        this.timestamp = LocalDateTime.now();
    }

    @JsonCreator
    public CacheContext(@JsonProperty("variables") List<Variable> variables,
                        @JsonProperty("usedRules") List<Rule> usedRules,
                        @JsonProperty("timestamp") LocalDateTime timestamp) {
        this.variables = variables;
        this.usedRules = usedRules;
        this.timestamp = timestamp;
    }

    @Override
    public boolean exists(String name) {
        return variables.stream()
                .anyMatch(it -> name.equals(it.name()));
    }

    @Override
    public Variable get(String name) {
        return variables.stream()
                .filter(it -> name.equals(it.name()))
                .findAny()
                .orElseThrow(() -> new NoSuchVariableException(name));
    }

    @Override
    public void add(Variable variable) {
        variables.stream()
                .filter(it -> variable.name().equals(it.name()))
                .findAny()
                .ifPresent(variables::remove);
        variables.add(variable);
    }

    @Override
    public void addRuleDescription(Rule rule) {
        usedRules.add(rule);
    }

    @Override
    public List<String> getUsedRulesDescriptions() {
        return usedRules.stream().map(Rule::getRuleDescription).collect(Collectors.toList());
    }
}
