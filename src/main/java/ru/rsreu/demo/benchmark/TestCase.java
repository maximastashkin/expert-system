package ru.rsreu.demo.benchmark;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.rsreu.alcohol.enums.values.DrinkValue;
import ru.rsreu.alcohol.enums.values.EffectValue;
import ru.rsreu.alcohol.enums.values.TasteValue;

@Data
@AllArgsConstructor
public class TestCase {
    private EffectValue effectValue;
    private Integer money;
    private Integer people;
    private TasteValue tasteValue;

    private DrinkValue drinkValue;
}
