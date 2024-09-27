package net.portic.library.domain.service.stages.validate;

import lombok.RequiredArgsConstructor;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.ConfigurationService;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class ValidatorFactory {

    private final GenericValidator genericValidator;
    private final Map<String, Validator> validators;
    private final ConfigurationService configurationService;

    public Validator getValidator(ExecutionContext executionContext) {
        String validatorKey = executionContext.getExecution().getMsgType() + "";
        String validatorName = configurationService.getPropertyValue(validatorKey);
        return validators.getOrDefault(validatorName, genericValidator);
    }
}
