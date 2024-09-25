package net.portic.processdirector.common.domain.stages.trackntrace;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GenericTrackNTracer extends TrackNTracer {

    @Override
    public boolean supports() {
        return false;
    }

    @Override
    public void execute() {
        log.info("Tracking and Tracing...");
    }
}
