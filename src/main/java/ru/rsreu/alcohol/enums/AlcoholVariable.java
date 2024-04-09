package ru.rsreu.alcohol.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AlcoholVariable {
    TASTE("Вкусовые предпочтения"),
    EFFECT("Желаемый эффект"),
    PEOPLE("Количество человек"),
    MONEY("Бюджет"),

    STRENGTH("Крепость"),
    PRICE("Цена"),

    DRINK("Напиток");

    private final String name;
}
