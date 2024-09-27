package net.portic.library.domain.service.stages.trackntrace;

import lombok.extern.slf4j.Slf4j;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.ConfigurationService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GenericTrackNTracer extends TrackNTracer {

    @Override
    public ExecutionContext execute(ExecutionContext executionContext) {
        return executionContext;
    }
}
