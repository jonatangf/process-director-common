package net.portic.library.domain.service.stages.tracking;

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
public class TrackingStage extends Stage {

    private final TrackerFactory trackerFactory;

    @Override
    public int getOrder() {
        return 6;
    }

    public ExecutionContext execute(ExecutionContext executionContext) {
        Tracker tracker = trackerFactory.getTracker(executionContext);

        try {
            ExecutionContext outputExecutionContext = tracker.execute(executionContext);
            outputExecutionContext.getMessageProcessorAudits().add(MessageProcessorAudit.builder()
                    .summary("Trackered by " + tracker.getClass().getSimpleName())
                    .build());
            return outputExecutionContext;
        } catch (Exception e) {
            executionContext.getMessageProcessorErrors().add(MessageProcessorError.builder()
                    .code("TRACKER_ERROR")
                    .description("Error while preparing")
                    .isFatal(true)
                    .build());
            return executionContext;
        }
    }
}
