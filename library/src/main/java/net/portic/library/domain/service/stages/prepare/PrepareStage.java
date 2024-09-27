package net.portic.library.domain.service.stages.prepare;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.model.MessageProcessorAudit;
import net.portic.library.domain.model.MessageProcessorError;
import net.portic.library.domain.service.stages.Stage;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrepareStage extends Stage {

    private final PreparerFactory preparerFactory;

    @Override
    public int getOrder() {
        return 0;
    }

    public ExecutionContext execute(ExecutionContext executionContext) {
        Preparer preparer = preparerFactory.getPreparer(executionContext);

        try {
            ExecutionContext outputExecutionContext = preparer.execute(executionContext);
            outputExecutionContext.getMessageProcessorAudits().add(MessageProcessorAudit.builder()
                    .summary("Prepared by " + preparer.getClass().getSimpleName())
                    .build());
            return outputExecutionContext;
        } catch (Exception e) {
            executionContext.getMessageProcessorErrors().add(MessageProcessorError.builder()
                    .code("PREPARE_ERROR")
                    .description("Error while preparing")
                    .isFatal(true)
                    .build());
            return executionContext;
        }
    }
}
