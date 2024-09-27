package net.portic.application.stages.trackntrace;

import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.stages.trackntrace.TrackNTracer;
import org.springframework.stereotype.Component;

@Component
public class RESPONSETrackNTracer extends TrackNTracer {

    @Override
    public ExecutionContext execute(ExecutionContext executionContext) {
        return executionContext;
    }
}
