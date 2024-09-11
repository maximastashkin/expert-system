package ru.rsreu.demo.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.rsreu.alcohol.enums.AlcoholVariable;
import ru.rsreu.demo.storage.PersistenceStorage;
import ru.rsreu.demo.view.dto.InputFormDto;
import ru.rsreu.demo.view.dto.ResultDto;
import ru.rsreu.demo.view.utils.ViewPrettyVariablesUtils;
import ru.rsreu.expert.system.Engine;
import ru.rsreu.expert.system.data.Context;
import ru.rsreu.expert.system.data.Variable;
import ru.rsreu.expert.system.exception.ImpossibleToCalculateTargetException;

import java.util.ArrayList;
import java.util.List;

import static ru.rsreu.alcohol.FunctionalAlcoholExpertSystem.provideContext;
import static ru.rsreu.alcohol.FunctionalAlcoholExpertSystem.rules;

@Controller
@RequiredArgsConstructor
public class ProcessController {
    private static final String FORM_MODEL_ATTRIBUTE_NAME = "inputForm";
    private static final String RESULT_MODEL_ATTRIBUTE_NAME = "result";
    private final PersistenceStorage persistenceStorage;

    @GetMapping("/")
    public String getForm(Model model) {
        model.addAttribute(FORM_MODEL_ATTRIBUTE_NAME, new InputFormDto());
        return "inputForm";
    }

    @PostMapping("/process")
    public String process(@ModelAttribute(FORM_MODEL_ATTRIBUTE_NAME) InputFormDto input, Model model) {
        Context context = provideContext();
        Engine engine = new Engine(context);
        ResultDto resultDto;
        try {
            Variable drink = engine.process(prepareInputVariables(input), rules(), AlcoholVariable.DRINK.name());
            resultDto = prepareResultDto(context, drink);
        } catch (ImpossibleToCalculateTargetException e) {
            resultDto = prepareResultDto(context, null);
        }
        model.addAttribute(RESULT_MODEL_ATTRIBUTE_NAME, resultDto);
        persistenceStorage.saveContext(context);
        return "inputForm";
    }

    private List<Variable> prepareInputVariables(InputFormDto input) {
        List<Variable> result = new ArrayList<>();
        if (input.getTaste() != null) {
            result.add(new Variable(AlcoholVariable.TASTE.name(), input.getTaste().name()));
        }
        if (input.getEffect() != null) {
            result.add(new Variable(AlcoholVariable.EFFECT.name(), input.getEffect().name()));
        }
        if (input.getPeople() != null) {
            result.add(new Variable(AlcoholVariable.PEOPLE.name(), String.valueOf(input.getPeople())));
        }
        if (input.getMoney() != null) {
            result.add(new Variable(AlcoholVariable.MONEY.name(), String.valueOf(input.getMoney())));
        }
        return result;
    }

    private ResultDto prepareResultDto(Context context, Variable resultVariable) {
        List<String> variables = ViewPrettyVariablesUtils.getVariablesFromContext(context);
        return ResultDto.builder()
                .variables(variables)
                .rules(context.getUsedRulesDescriptions())
                .result(resultVariable != null ? ViewPrettyVariablesUtils.prepareVariableString(
                        resultVariable,
                        AlcoholVariable.DRINK.getConverter()
                ) : null)
                .build();
    }
}
