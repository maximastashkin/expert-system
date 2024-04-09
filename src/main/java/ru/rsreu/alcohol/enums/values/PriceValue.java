package ru.rsreu.alcohol.enums.values;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PriceValue {
    CHEAP("Дешевые"),
    MEDIUM("Средние"),
    EXPENSIVE("Дорогие");

    private final String name;
}
