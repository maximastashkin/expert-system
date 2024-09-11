package ru.rsreu.demo.benchmark;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rsreu.alcohol.enums.AlcoholVariable;
import ru.rsreu.alcohol.enums.values.DrinkValue;
import ru.rsreu.alcohol.enums.values.EffectValue;
import ru.rsreu.alcohol.enums.values.TasteValue;
import ru.rsreu.demo.storage.PersistenceStorage;
import ru.rsreu.expert.system.Engine;
import ru.rsreu.expert.system.data.Variable;
import ru.rsreu.expert.system.exception.ImpossibleToCalculateTargetException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static ru.rsreu.alcohol.FunctionalAlcoholExpertSystem.provideContext;
import static ru.rsreu.alcohol.FunctionalAlcoholExpertSystem.rules;

@RequiredArgsConstructor
@Component
public class Benchmark {
    private final PersistenceStorage persistenceStorage;

    public List<TestCase> start() {
        List<TestCase> testCases = generateStartTestCases();
        testCases.forEach(testCase -> {
            Engine engine = new Engine(provideContext());
            List<Variable> inputVariables = new ArrayList<>();
            if (testCase.getTasteValue() != null) {
                inputVariables.add(new Variable(AlcoholVariable.TASTE.name(), testCase.getTasteValue().name()));
            }
            if (testCase.getEffectValue() != null) {
                inputVariables.add(new Variable(AlcoholVariable.EFFECT.name(), testCase.getEffectValue().name()));
            }
            if (testCase.getPeople() != null) {
                inputVariables.add(new Variable(AlcoholVariable.PEOPLE.name(), String.valueOf(testCase.getPeople())));
            }
            if (testCase.getMoney() != null) {
                inputVariables.add(new Variable(AlcoholVariable.MONEY.name(), String.valueOf(testCase.getMoney())));
            }
            try {
                Variable drink = engine.process(inputVariables, rules(), AlcoholVariable.DRINK.name());

                testCase.setDrinkValue(DrinkValue.valueOf(drink.value()));
            } catch (ImpossibleToCalculateTargetException ignored) {

            }
        });
        return testCases.stream().sorted((t1, t2) -> {
            if (t1.getDrinkValue() == null) {
                return (t2.getDrinkValue() == null) ? 0 : 1;
            }
            if (t2.getDrinkValue() == null) {
                return -1;
            }
            return 0;
        }).toList();

        //.filter(testCase -> testCase.getDrinkValue() == null && testCase.getEffectValue() != null && testCase.getTasteValue() != null && testCase.getPeople() != null && testCase.getMoney() != null)
    }

    private List<TestCase> generateStartTestCases() {
        List<EffectValue> effectValues = new ArrayList<>(EnumSet.allOf(EffectValue.class));
        effectValues.add(null);
        List<TasteValue> tasteValues = new ArrayList<>(EnumSet.allOf(TasteValue.class));
        tasteValues.add(null);
        List<Integer> moneyValues = Arrays.asList(500, 3000, 6000, null);
        List<Integer> peopleValues = Arrays.asList(3, 7, null);

        List<TestCase> testCases = new ArrayList<>();
        for (EffectValue effectValue : effectValues) {
            for (TasteValue tasteValue : tasteValues) {
                for (Integer moneyValue : moneyValues) {
                    for (Integer peopleValue : peopleValues) {
                        testCases.add(new TestCase(effectValue, moneyValue, peopleValue, tasteValue, null));
                    }
                }
            }
        }
        return testCases;
    }

}
