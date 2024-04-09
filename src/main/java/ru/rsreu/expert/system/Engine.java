package ru.rsreu.expert.system;

import lombok.RequiredArgsConstructor;
import ru.rsreu.expert.system.exception.ImpossibleToCalculateTarget;
import ru.rsreu.expert.system.data.Context;
import ru.rsreu.expert.system.data.Variable;
import ru.rsreu.expert.system.rule.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Engine {
    private final Context context;

    public Variable process(List<Variable> inputVariables, List<Rule> rules, String targetVariable) {
        inputVariables.forEach(this::saveVariable);

        int before = rules.size();
        int after = 0;

        while (rules.size() > 0 && before > after) {
            before = rules.size();
            rules = applyRules(rules);
            after = rules.size();
        }

        if (context.exists(targetVariable)) {
            return context.get(targetVariable);
        } else {
            throw new ImpossibleToCalculateTarget(targetVariable);
        }
    }

    private List<Rule> applyRules(List<Rule> rules) {
        List<Rule> rulesCopy = new ArrayList<>(rules);
        rules.forEach(it -> {
            if (applyFromContextIfPossible(it)) {
                rulesCopy.remove(it);
            }
        });
        return rulesCopy;
    }

    private boolean applyFromContextIfPossible(Rule rule) {
        if (!canApply(rule)) {
            return false;
        }
        List<Variable> input = rule.getInputVariableNames().stream()
                .map(context::get)
                .collect(Collectors.toList());
        Optional<Variable> variable = rule.apply(input);
        if (variable.isPresent()) {
            saveVariable(variable.get());
            return true;
        } else {
            return false;
        }
    }

    private boolean canApply(Rule rule) {
        for (String variable : rule.getInputVariableNames()) {
            if (!context.exists(variable)) {
                return false;
            }
        }
        return true;
    }

    private void saveVariable(Variable variable) {
        context.add(variable);
    }
}
