package net.portic.processdirector.common.domain.stages.trackntrace;

import lombok.RequiredArgsConstructor;
import net.portic.processdirector.common.domain.stages.Stage;
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
    public void execute() {
        TrackNTracer trackNTracer = tracers.stream()
                .filter(TrackNTracer::supports)
                .findFirst()
                .orElse(genericTrackNTracer);

        trackNTracer.preExecute();
        trackNTracer.execute();
        trackNTracer.postExecute();
    }
}
