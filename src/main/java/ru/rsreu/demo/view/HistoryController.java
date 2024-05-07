package ru.rsreu.demo.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.rsreu.alcohol.enums.AlcoholVariable;
import ru.rsreu.demo.storage.PersistenceStorage;
import ru.rsreu.demo.view.dto.ResultDto;
import ru.rsreu.demo.view.utils.ViewPrettyVariablesUtils;
import ru.rsreu.expert.system.data.Context;
import ru.rsreu.expert.system.data.Variable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HistoryController {
    private static final String RESULTS_MODEL_ATTRIBUTE_NAME = "results";

    private final PersistenceStorage persistenceStorage;

    @GetMapping("/history")
    public String getContextsHistory(Model model) {
        List<? extends Context> contexts = persistenceStorage.getAllContexts();
        List<ResultDto> results = contexts.stream().map(this::prepareResultDto).toList();
        model.addAttribute(RESULTS_MODEL_ATTRIBUTE_NAME, results);
        return "history";
    }

    private ResultDto prepareResultDto(Context context) {
        List<String> variables = ViewPrettyVariablesUtils.getVariablesFromContext(context);
        Variable resultVariable = null;
        if (context.exists(AlcoholVariable.DRINK.name())) {
            resultVariable = context.get(AlcoholVariable.DRINK.name());
        }
        return ResultDto.builder()
                .variables(variables)
                .rules(context.getUsedRulesDescriptions())
                .result(resultVariable != null ? ViewPrettyVariablesUtils.prepareVariableString(
                        resultVariable,
                        AlcoholVariable.DRINK.getConverter()
                ) : null)
                .timestamp(context.getTimestamp())
                .build();
    }
}
