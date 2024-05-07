package ru.rsreu.expert.system.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.rsreu.alcohol.CacheContext;
import ru.rsreu.expert.system.rule.Rule;

import java.time.LocalDateTime;
import java.util.List;

@JsonSerialize(as = CacheContext.class)
@JsonDeserialize(as = CacheContext.class)
public interface Context {
    boolean exists(String name);
    Variable get(String name);
    void add(Variable variable);
    void addRuleDescription(Rule rule);
    List<String> getUsedRulesDescriptions();
    LocalDateTime getTimestamp();
}
