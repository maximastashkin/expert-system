package ru.rsreu.alcohol;

import ru.rsreu.alcohol.enums.AlcoholVariable;
import ru.rsreu.alcohol.enums.values.DrinkValue;
import ru.rsreu.alcohol.enums.values.EffectValue;
import ru.rsreu.alcohol.enums.values.PriceValue;
import ru.rsreu.alcohol.enums.values.StrengthValue;
import ru.rsreu.alcohol.enums.values.TasteValue;
import ru.rsreu.expert.system.data.Context;
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
    public static List<Rule> rules() {
        return List.of(
                FunctionalRule.of(
                        "1. ЕСЛИ Желаемый эффект = сильно ИЛИ Количество человек > 5, ТО Крепость = сильная",
                        List.of(EFFECT, PEOPLE),
                        variables -> variables.get(AlcoholVariable.EFFECT) == EffectValue.STRONG ||
                                (Integer) variables.get(AlcoholVariable.PEOPLE) > 5,
                        VariableValue.of(STRENGTH, StrengthValue.HIGH)
                ),
                FunctionalRule.of(
                        "2. ЕСЛИ Желаемый эффект = сильно И Количество человек <= 5, ТО Крепость = средняя",
                        List.of(EFFECT, PEOPLE),
                        variables -> variables.get(AlcoholVariable.EFFECT) == EffectValue.STRONG &&
                                (Integer) variables.get(AlcoholVariable.PEOPLE) <= 5,
                        VariableValue.of(STRENGTH, StrengthValue.MEDIUM)
                ),
                FunctionalRule.of(
                        "3. ЕСЛИ Желаемый эффект = слабо, ТО Крепость = слабая",
                        List.of(EFFECT),
                        variables -> variables.get(AlcoholVariable.EFFECT) == EffectValue.EASY,
                        VariableValue.of(STRENGTH, StrengthValue.LOW)
                ),
                FunctionalRule.of(
                        "4. ЕСЛИ Желаемый эффект = средне, ТО Крепость = средняя",
                        List.of(EFFECT),
                        variables -> variables.get(AlcoholVariable.EFFECT) == EffectValue.MIDDLE,
                        VariableValue.of(STRENGTH, StrengthValue.MEDIUM)
                ),
                FunctionalRule.of(
                        "5. ЕСЛИ Бюджет < 1000, ТО Цена = дешевые",
                        List.of(MONEY),
                        variables -> (Integer) variables.get(AlcoholVariable.MONEY) < 1000,
                        VariableValue.of(PRICE, PriceValue.CHEAP)
                ),
                FunctionalRule.of(
                        "6. ЕСЛИ Бюджет от 1000 до 5000 И Количество человек > 5, ТО Цена = дешевые",
                        List.of(MONEY, PEOPLE),
                        variables -> (Integer) variables.get(AlcoholVariable.MONEY) >= 1000 &&
                                (Integer) variables.get(AlcoholVariable.MONEY) < 5000 &&
                                (Integer) variables.get(AlcoholVariable.PEOPLE) > 5,
                        VariableValue.of(PRICE, PriceValue.CHEAP)
                ),
                FunctionalRule.of(
                        "7. ЕСЛИ Бюджет от 1000 до 5000 И Количество человек <= 5, ТО Цена = средние",
                        List.of(MONEY, PEOPLE),
                        variables -> (Integer) variables.get(AlcoholVariable.MONEY) >= 1000 &&
                                (Integer) variables.get(AlcoholVariable.MONEY) <= 5000 &&
                                (Integer) variables.get(AlcoholVariable.PEOPLE) <= 5,
                        VariableValue.of(PRICE, PriceValue.MEDIUM)
                ),
                FunctionalRule.of(
                        "8. ЕСЛИ Бюджет > 5000 И Количество человек > 5, ТО Цена = средние",
                        List.of(MONEY, PEOPLE),
                        variables -> (Integer) variables.get(AlcoholVariable.MONEY) > 5000 &&
                                (Integer) variables.get(AlcoholVariable.PEOPLE) > 5,
                        VariableValue.of(PRICE, PriceValue.MEDIUM)
                ),
                FunctionalRule.of(
                        "9. ЕСЛИ Бюджет > 5000 И Количество человек <= 5, ТО Цена = дорогие",
                        List.of(MONEY, PEOPLE),
                        variables -> (Integer) variables.get(AlcoholVariable.MONEY) > 5000 &&
                                (Integer) variables.get(AlcoholVariable.PEOPLE) <= 5,
                        VariableValue.of(PRICE, PriceValue.EXPENSIVE)
                ),

                FunctionalRule.of(
                        "10. ЕСЛИ Крепость = слабая, ТО Напиток = пиво",
                        List.of(STRENGTH),
                        variables -> variables.get(AlcoholVariable.STRENGTH) == StrengthValue.LOW,
                        VariableValue.of(DRINK, DrinkValue.BEER)
                ),
                FunctionalRule.of(
                        "11. ЕСЛИ Крепость = средняя И Цена = дорого, ТО Напиток = Ликер",
                        List.of(STRENGTH, PRICE),
                        variables -> variables.get(AlcoholVariable.STRENGTH) == StrengthValue.MEDIUM &&
                                variables.get(AlcoholVariable.PRICE) == PriceValue.EXPENSIVE,
                        VariableValue.of(DRINK, DrinkValue.LIQUOR)
                ),
                FunctionalRule.of(
                        "12. ЕСЛИ Крепость = средняя И Цена = средние И Вкус = важен, ТО Напиток = Вино",
                        List.of(STRENGTH, PRICE, TASTE),
                        variables -> variables.get(AlcoholVariable.STRENGTH) == StrengthValue.MEDIUM &&
                                variables.get(AlcoholVariable.PRICE) == PriceValue.MEDIUM &&
                                variables.get(AlcoholVariable.TASTE) == TasteValue.IMPORTANT,
                        VariableValue.of(DRINK, DrinkValue.WINE)
                ),
                FunctionalRule.of(
                        "13. ЕСЛИ Крепость = средняя И Цена = средние И Вкус = не важен, ТО Напиток = Шампанское",
                        List.of(STRENGTH, PRICE, TASTE),
                        variables -> variables.get(AlcoholVariable.STRENGTH) == StrengthValue.MEDIUM &&
                                variables.get(AlcoholVariable.PRICE) == PriceValue.MEDIUM &&
                                variables.get(AlcoholVariable.TASTE) == TasteValue.NOT_IMPORTANT,
                        VariableValue.of(DRINK, DrinkValue.CHAMPAIGN)
                ),
                FunctionalRule.of(
                        "14. ЕСЛИ Крепость = высокая И (Цена = дорого ИЛИ Вкус = важен), ТО Напиток = Коньяк",
                        List.of(STRENGTH, PRICE, TASTE),
                        variables -> variables.get(AlcoholVariable.STRENGTH) == StrengthValue.HIGH &&
                                (variables.get(AlcoholVariable.PRICE) == PriceValue.EXPENSIVE ||
                                        variables.get(AlcoholVariable.TASTE) == TasteValue.IMPORTANT),
                        VariableValue.of(DRINK, DrinkValue.COGNAC)
                ),
                FunctionalRule.of(
                        "15. ЕСЛИ Крепость = высокая И НЕ (Цена = дорого ИЛИ Вкус = важен), ТО Напиток = Водка",
                        List.of(STRENGTH, PRICE, TASTE),
                        variables -> variables.get(AlcoholVariable.STRENGTH) == StrengthValue.HIGH &&
                                !(variables.get(AlcoholVariable.PRICE) == PriceValue.EXPENSIVE ||
                                        variables.get(AlcoholVariable.TASTE) == TasteValue.IMPORTANT),
                        VariableValue.of(DRINK, DrinkValue.VODKA)
                )
        );
    }

    public static Context provideContext() {
        return new CacheContext();
    }
}
