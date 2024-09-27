package net.portic.library.domain.service.stages.trackntrace;

import lombok.RequiredArgsConstructor;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.model.MessageProcessorAudit;
import net.portic.library.domain.model.MessageProcessorError;
import net.portic.library.domain.service.stages.Stage;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TrackNTraceStage extends Stage {

    private final TrackNTracerFactory trackNTracerFactory;

    @Override
    public int getOrder() {
        return 3;
    }

    @Override
    public ExecutionContext execute(ExecutionContext inputExecutionContext) {
        TrackNTracer trackNTracer = trackNTracerFactory.getTrackNTracer(inputExecutionContext);

        try {
            ExecutionContext executionContext = trackNTracer.execute(inputExecutionContext);
            executionContext.getMessageProcessorAudits().add(MessageProcessorAudit.builder()
                    .summary("Tracked and traced by " + trackNTracer.getClass().getSimpleName())
                    .build());
            return executionContext;
        } catch (Exception e) {
            inputExecutionContext.getMessageProcessorErrors().add(MessageProcessorError.builder()
                    .code("TRACK_N_TRACE_ERROR")
                    .description("Error while tracking and tracing")
                    .isFatal(true)
                    .build());
            return inputExecutionContext;
        }
    }
}
