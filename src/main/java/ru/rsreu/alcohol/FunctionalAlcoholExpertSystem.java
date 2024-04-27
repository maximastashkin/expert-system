package ru.rsreu.alcohol;

import ru.rsreu.alcohol.enums.AlcoholVariable;
import ru.rsreu.alcohol.enums.values.DrinkValue;
import ru.rsreu.alcohol.enums.values.EffectValue;
import ru.rsreu.alcohol.enums.values.PriceValue;
import ru.rsreu.alcohol.enums.values.StrengthValue;
import ru.rsreu.alcohol.enums.values.TasteValue;
import ru.rsreu.expert.system.Engine;
import ru.rsreu.expert.system.data.Context;
import ru.rsreu.expert.system.data.Variable;
import ru.rsreu.expert.system.front.func.FunctionalRule;
import ru.rsreu.expert.system.front.func.VariableDefinition;
import ru.rsreu.expert.system.front.func.VariableValue;
import ru.rsreu.expert.system.rule.Rule;

import java.util.List;

public class FunctionalAlcoholExpertSystem {
    // Входные переменные
    private static final VariableDefinition<AlcoholVariable> TASTE = new VariableDefinition<>(
            AlcoholVariable.TASTE,
            TasteValue::valueOf
    );
    private static final VariableDefinition<AlcoholVariable> EFFECT = new VariableDefinition<>(
            AlcoholVariable.EFFECT,
            EffectValue::valueOf
    );
    private static final VariableDefinition<AlcoholVariable> PEOPLE = new VariableDefinition<>(
            AlcoholVariable.PEOPLE,
            Integer::parseInt
    );
    private static final VariableDefinition<AlcoholVariable> MONEY = new VariableDefinition<>(
            AlcoholVariable.MONEY,
            Integer::parseInt
    );

    // Промежуточные переменные
    private static final VariableDefinition<AlcoholVariable> STRENGTH = new VariableDefinition<>(
            AlcoholVariable.STRENGTH,
            StrengthValue::valueOf
    );
    private static final VariableDefinition<AlcoholVariable> PRICE = new VariableDefinition<>(
            AlcoholVariable.PRICE,
            PriceValue::valueOf
    );

    // Выходная переменная
    private static final VariableDefinition<AlcoholVariable> DRINK = new VariableDefinition<>(
            AlcoholVariable.DRINK,
            DrinkValue::valueOf
    );

    // Правила
    private static List<Rule> rules() {
        return List.of(
                FunctionalRule.of(
                        List.of(EFFECT, PEOPLE),
                        variables -> variables.get(AlcoholVariable.EFFECT) == EffectValue.STRONG ||
                                (Integer) variables.get(AlcoholVariable.PEOPLE) > 5,
                        VariableValue.of(STRENGTH, StrengthValue.HIGH)
                ),
                FunctionalRule.of(
                        List.of(EFFECT, PEOPLE),
                        variables -> variables.get(AlcoholVariable.EFFECT) == EffectValue.STRONG ||
                                (Integer) variables.get(AlcoholVariable.PEOPLE) <= 5,
                        VariableValue.of(STRENGTH, StrengthValue.MEDIUM)
                ),
                FunctionalRule.of(
                        List.of(EFFECT),
                        variables -> variables.get(AlcoholVariable.EFFECT) == EffectValue.EASY,
                        VariableValue.of(STRENGTH, StrengthValue.LOW)
                ),
                FunctionalRule.of(
                        List.of(EFFECT),
                        variables -> variables.get(AlcoholVariable.EFFECT) == EffectValue.MIDDLE,
                        VariableValue.of(STRENGTH, StrengthValue.MEDIUM)
                ),
                FunctionalRule.of(
                        List.of(MONEY),
                        variables -> (Integer) variables.get(AlcoholVariable.MONEY) < 1000,
                        VariableValue.of(PRICE, PriceValue.CHEAP)
                ),
                FunctionalRule.of(
                        List.of(MONEY, PEOPLE),
                        variables -> (Integer) variables.get(AlcoholVariable.MONEY) >= 1000 &&
                                (Integer) variables.get(AlcoholVariable.MONEY) < 5000 &&
                                (Integer) variables.get(AlcoholVariable.PEOPLE) > 5,
                        VariableValue.of(PRICE, PriceValue.CHEAP)
                ),
                FunctionalRule.of(
                        List.of(MONEY, PEOPLE),
                        variables -> (Integer) variables.get(AlcoholVariable.MONEY) >= 1000 &&
                                (Integer) variables.get(AlcoholVariable.MONEY) <= 5000 &&
                                (Integer) variables.get(AlcoholVariable.PEOPLE) <= 5,
                        VariableValue.of(PRICE, PriceValue.MEDIUM)
                ),
                FunctionalRule.of(
                        List.of(MONEY, PEOPLE),
                        variables -> (Integer) variables.get(AlcoholVariable.MONEY) > 5000 &&
                                (Integer) variables.get(AlcoholVariable.PEOPLE) > 5,
                        VariableValue.of(PRICE, PriceValue.MEDIUM)
                ),
                FunctionalRule.of(
                        List.of(MONEY, PEOPLE),
                        variables -> (Integer) variables.get(AlcoholVariable.MONEY) > 5000 &&
                                (Integer) variables.get(AlcoholVariable.PEOPLE) <= 5,
                        VariableValue.of(PRICE, PriceValue.EXPENSIVE)
                ),
                FunctionalRule.of(
                        List.of(STRENGTH),
                        variables -> variables.get(AlcoholVariable.STRENGTH) == StrengthValue.LOW,
                        VariableValue.of(DRINK, DrinkValue.BEER)
                ),
                FunctionalRule.of(
                        List.of(STRENGTH, PRICE),
                        variables -> variables.get(AlcoholVariable.STRENGTH) == StrengthValue.MEDIUM &&
                                variables.get(AlcoholVariable.PRICE) == PriceValue.EXPENSIVE,
                        VariableValue.of(DRINK, DrinkValue.LIQUOR)
                ),
                FunctionalRule.of(
                        List.of(STRENGTH, PRICE, TASTE),
                        variables -> variables.get(AlcoholVariable.STRENGTH) == StrengthValue.MEDIUM &&
                                variables.get(AlcoholVariable.PRICE) == PriceValue.MEDIUM &&
                                variables.get(AlcoholVariable.TASTE) == TasteValue.IMPORTANT,
                        VariableValue.of(DRINK, DrinkValue.WINE)
                ),
                FunctionalRule.of(
                        List.of(STRENGTH, PRICE, TASTE),
                        variables -> variables.get(AlcoholVariable.STRENGTH) == StrengthValue.MEDIUM &&
                                variables.get(AlcoholVariable.PRICE) == PriceValue.MEDIUM &&
                                variables.get(AlcoholVariable.TASTE) == TasteValue.NOT_IMPORTANT,
                        VariableValue.of(DRINK, DrinkValue.CHAMPAIGN)
                ),
                FunctionalRule.of(
                        List.of(STRENGTH, PRICE, TASTE),
                        variables -> variables.get(AlcoholVariable.STRENGTH) == StrengthValue.HIGH &&
                                (variables.get(AlcoholVariable.PRICE) == PriceValue.EXPENSIVE ||
                                        variables.get(AlcoholVariable.TASTE) == TasteValue.IMPORTANT),
                        VariableValue.of(DRINK, DrinkValue.COGNAC)
                ),
                FunctionalRule.of(
                        List.of(STRENGTH, PRICE, TASTE),
                        variables -> variables.get(AlcoholVariable.STRENGTH) == StrengthValue.HIGH &&
                                !(variables.get(AlcoholVariable.PRICE) == PriceValue.EXPENSIVE ||
                                        variables.get(AlcoholVariable.TASTE) == TasteValue.IMPORTANT),
                        VariableValue.of(DRINK, DrinkValue.VODKA)
                )
        );
    }

    private static Context provideContext() {
        // TODO Switch context to H2
        return new CacheContext();
    }

    private static List<Variable> inputVariables() {
        return List.of(
                new Variable(AlcoholVariable.TASTE.name(), TasteValue.IMPORTANT.name()),
                new Variable(AlcoholVariable.EFFECT.name(), EffectValue.STRONG.name()),
                new Variable(AlcoholVariable.PEOPLE.name(), "3"),
                new Variable(AlcoholVariable.MONEY.name(), "3000")
        );
    }

    public void start() {
        Engine engine = new Engine(provideContext());
        Variable drink = engine.process(inputVariables(), rules(), AlcoholVariable.DRINK.name());
        System.out.println(DrinkValue.valueOf(drink.getValue()).getName());
    }
}
