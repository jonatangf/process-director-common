package net.portic.library.domain.service.stages.transformin;

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
public class TransformInStage extends Stage {

    private final InTransformerFactory inTransformerFactory;

    @Override
    public int getOrder() {
        return 1;
    }

    public ExecutionContext execute(ExecutionContext executionContext) {
        InTransformer inTransformer = inTransformerFactory.getInTransformer(executionContext);

        try {
            ExecutionContext outputExecutionContext = inTransformer.execute(executionContext);
            outputExecutionContext.getMessageProcessorAudits().add(MessageProcessorAudit.builder()
                    .summary("" + inTransformer.getClass().getSimpleName())
                    .build());
            return outputExecutionContext;
        } catch (Exception e) {
            executionContext.getMessageProcessorErrors().add(MessageProcessorError.builder()
                    .code("TRANSFORM_IN_ERROR")
                    .description("Error while transforming in")
                    .isFatal(true)
                    .build());
            return executionContext;
        }
    }
}
