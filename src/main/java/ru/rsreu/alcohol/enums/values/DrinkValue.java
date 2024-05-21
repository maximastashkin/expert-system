package ru.rsreu.alcohol.enums.values;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DrinkValue {
    BEER("Пиво"),
    WINE("Вино"),
    CHAMPAIGN("Шампанское"),
    LIQUOR("Ликер"),
    VODKA("Водка"),
    COGNAC("Коньяк"),
    BORMUTUHA("Самогонка");

    private final String name;
}
