package ru.rsreu.alcohol.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.rsreu.alcohol.enums.values.DrinkValue;
import ru.rsreu.alcohol.enums.values.EffectValue;
import ru.rsreu.alcohol.enums.values.PriceValue;
import ru.rsreu.alcohol.enums.values.StrengthValue;
import ru.rsreu.alcohol.enums.values.TasteValue;

import java.util.function.Function;

@RequiredArgsConstructor
@Getter
public enum AlcoholVariable {
    TASTE("Вкусовые предпочтения", str -> TasteValue.valueOf(str).getName()),
    EFFECT("Желаемый эффект", str -> EffectValue.valueOf(str).getName()),
    PEOPLE("Количество человек", Function.identity()),
    MONEY("Бюджет", Function.identity()),

    STRENGTH("Крепость", str -> StrengthValue.valueOf(str).getName()),
    PRICE("Цена", str -> PriceValue.valueOf(str).getName()),

    DRINK("Напиток", str -> DrinkValue.valueOf(str).getName());

    private final String name;

    private final Function<String, String> converter;
}
