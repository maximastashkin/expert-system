package ru.rsreu.alcohol.enums.values;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TasteValue {
    IMPORTANT("Важны"),
    NOT_IMPORTANT("Не важны");

    private final String name;
}
