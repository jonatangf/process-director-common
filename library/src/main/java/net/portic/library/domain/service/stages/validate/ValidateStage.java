package net.portic.library.domain.service.stages.validate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.model.MessageProcessorAudit;
import net.portic.library.domain.model.MessageProcessorError;
import net.portic.library.domain.service.stages.Stage;
import net.portic.library.domain.service.stages.prepare.Preparer;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ValidateStage extends Stage {

    private final ValidatorFactory validatorFactory;
    
    @Override
    public int getOrder() {
        return 2;
    }

    public ExecutionContext execute(ExecutionContext executionContext) {
        Validator validator = validatorFactory.getValidator(executionContext);

        try {
            ExecutionContext outputExecutionContext = validator.execute(executionContext);
            outputExecutionContext.getMessageProcessorAudits().add(MessageProcessorAudit.builder()
                    .summary("Validated by " + validator.getClass().getSimpleName())
                    .build());
            return outputExecutionContext;
        } catch (Exception e) {
            executionContext.getMessageProcessorErrors().add(MessageProcessorError.builder()
                    .code("VALIDATOR_ERROR")
                    .description("Error while validating")
                    .isFatal(true)
                    .build());
            return executionContext;
        }
    }
}
