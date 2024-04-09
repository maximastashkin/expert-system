package ru.rsreu.alcohol.enums.values;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StrengthValue {
    LOW("Слабая"),
    MEDIUM("Средняя"),
    HIGH("Сильная");

    private final String name;
}
