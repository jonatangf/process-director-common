package net.portic.processdirector.common.domain.service.stages.trackntrace;

import lombok.RequiredArgsConstructor;
import net.portic.processdirector.common.domain.model.ExecutionContext;
import net.portic.processdirector.common.domain.service.stages.Stage;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrackNTraceStage extends Stage {

    private final GenericTrackNTracer genericTrackNTracer;
    private final List<TrackNTracer> tracers;

    @Override
    public int getOrder() {
        return 5;
    }

    @Override
    public ExecutionContext execute(ExecutionContext executionContext) {
        TrackNTracer trackNTracer = tracers.stream()
                .filter(tracer -> tracer.supports(executionContext))
                .findFirst()
                .orElse(genericTrackNTracer);

        return trackNTracer.execute(executionContext);
    }
}
