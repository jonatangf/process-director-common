package net.portic.library.domain.service.stages.deliverto;

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
public class DeliverToStage extends Stage {

    private final DelivererFactory delivererFactory;

    @Override
    public int getOrder() {
        return 8;
    }

    public ExecutionContext execute(ExecutionContext executionContext) {
        Deliverer deliverer = delivererFactory.getDeliverer(executionContext);

        try {
            ExecutionContext outputExecutionContext = deliverer.execute(executionContext);
            outputExecutionContext.getMessageProcessorAudits().add(MessageProcessorAudit.builder()
                    .summary("Delivered by " + deliverer.getClass().getSimpleName())
                    .build());
            return outputExecutionContext;
        } catch (Exception e) {
            executionContext.getMessageProcessorErrors().add(MessageProcessorError.builder()
                    .code("DELIVERER_ERROR")
                    .description("Error while preparing")
                    .isFatal(true)
                    .build());
            return executionContext;
        }
    }
}
