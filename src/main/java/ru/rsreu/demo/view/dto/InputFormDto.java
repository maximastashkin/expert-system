package ru.rsreu.demo.view.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rsreu.alcohol.enums.values.EffectValue;
import ru.rsreu.alcohol.enums.values.TasteValue;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputFormDto {
    private TasteValue taste;
    private EffectValue effect;
    private Integer people;
    private Integer money;
}
