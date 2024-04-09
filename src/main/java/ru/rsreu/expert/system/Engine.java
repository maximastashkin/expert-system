package ru.rsreu.expert.system;

import lombok.RequiredArgsConstructor;
import ru.rsreu.expert.system.exception.ImpossibleToCalculateTarget;
import ru.rsreu.expert.system.data.Context;
import ru.rsreu.expert.system.data.Variable;
import ru.rsreu.expert.system.rule.Rule;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Engine {
    private final Context context;

    public Variable process(List<Variable> inputVariables, List<Rule> rules, String targetVariable) {
        inputVariables.forEach(this::saveVariable);

        List<Rule> unappliedRules = rules;
        int before = rules.size();
        int after = 0;

        while (unappliedRules.size() > 0 && before > after) {
            before = unappliedRules.size();
            unappliedRules.forEach(this::applyFromContextIfPossible);
            unappliedRules = unappliedRules.stream()
                    .filter(it -> !it.isApplied())
                    .collect(Collectors.toList());
            after = unappliedRules.size();
        }

        if (context.exists(targetVariable)) {
            return context.get(targetVariable);
        } else {
            throw new ImpossibleToCalculateTarget(targetVariable);
        }
    }

    private void applyFromContextIfPossible(Rule rule) {
        if (!canApply(rule)) {
            return;
        }
        List<Variable> input = rule.getInputVariableNames().stream()
                .map(context::get)
                .collect(Collectors.toList());
        rule.apply(input).ifPresent(this::saveVariable);
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
