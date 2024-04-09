package ru.rsreu.alcohol.enums.values;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EffectValue {
    STRONG("Сильно"),
    MIDDLE("Средне"),
    EASY("Слабо");

    private final String name;
}
