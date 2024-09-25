package net.portic.processdirector.common.domain.service.stages.trackntrace;

import lombok.extern.slf4j.Slf4j;
import net.portic.processdirector.common.domain.model.ExecutionContext;
import net.portic.processdirector.common.domain.service.ConfigurationService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GenericTrackNTracer extends TrackNTracer {

    public GenericTrackNTracer(ConfigurationService configurationService) {
        super(configurationService);
    }

    @Override
    public ExecutionContext execute(ExecutionContext executionContext) {
        log.info("Tracking and Tracing...");
        return executionContext;
    }
}
