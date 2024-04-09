package ru.rsreu.alcohol;

import ru.rsreu.alcohol.enums.values.EffectValue;
import ru.rsreu.alcohol.enums.AlcoholVariable;
import ru.rsreu.alcohol.enums.values.TasteValue;
import ru.rsreu.alcohol.rules.Rule1;
import ru.rsreu.alcohol.rules.Rule10;
import ru.rsreu.alcohol.rules.Rule11;
import ru.rsreu.alcohol.rules.Rule12;
import ru.rsreu.alcohol.rules.Rule13;
import ru.rsreu.alcohol.rules.Rule14;
import ru.rsreu.alcohol.rules.Rule15;
import ru.rsreu.alcohol.rules.Rule2;
import ru.rsreu.alcohol.rules.Rule3;
import ru.rsreu.alcohol.rules.Rule4;
import ru.rsreu.alcohol.rules.Rule5;
import ru.rsreu.alcohol.rules.Rule6;
import ru.rsreu.alcohol.rules.Rule7;
import ru.rsreu.alcohol.rules.Rule8;
import ru.rsreu.alcohol.rules.Rule9;
import ru.rsreu.expert.system.Engine;
import ru.rsreu.expert.system.rule.Rule;
import ru.rsreu.expert.system.data.Context;
import ru.rsreu.expert.system.data.Variable;

import java.util.List;

public class AlcoholExpertSystem {
    public void start() {
        Engine engine = new Engine(provideContext());
        Variable drink = engine.process(provideVariables(), provideRules(), AlcoholVariable.DRINK.getName());
        System.out.println(drink.getValue());
    }

    private List<Variable> provideVariables() {
        return List.of(
                new Variable(AlcoholVariable.TASTE.getName(), TasteValue.IMPORTANT.getName()),
                new Variable(AlcoholVariable.EFFECT.getName(), EffectValue.STRONG.getName()),
                new Variable(AlcoholVariable.PEOPLE.getName(), "3"),
                new Variable(AlcoholVariable.MONEY.getName(), "3000")
        );
    }

    private List<Rule> provideRules() {
        return List.of(
                new Rule1(),
                new Rule2(),
                new Rule3(),
                new Rule4(),
                new Rule5(),
                new Rule6(),
                new Rule7(),
                new Rule8(),
                new Rule9(),
                new Rule10(),
                new Rule11(),
                new Rule12(),
                new Rule13(),
                new Rule14(),
                new Rule15()
        );
    }

    private Context provideContext() {
        // TODO Switch context to H2
        return new CacheContext();
    }
}