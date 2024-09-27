package net.portic.library.domain.service.stages.transformout;

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
public class TransformOutStage extends Stage {

    private final OutTransformerFactory outTransformerFactory;

    @Override
    public int getOrder() {
        return 5;
    }

    public ExecutionContext execute(ExecutionContext executionContext) {
        OutTransformer outTransformer = outTransformerFactory.getOutTransformer(executionContext);

        try {
            ExecutionContext outputExecutionContext = outTransformer.execute(executionContext);
            outputExecutionContext.getMessageProcessorAudits().add(MessageProcessorAudit.builder()
                    .summary("" + outTransformer.getClass().getSimpleName())
                    .build());
            return outputExecutionContext;
        } catch (Exception e) {
            executionContext.getMessageProcessorErrors().add(MessageProcessorError.builder()
                    .code("TRANSFORM_OUT_ERROR")
                    .description("Error while transforming out")
                    .isFatal(true)
                    .build());
            return executionContext;
        }
    }
}
