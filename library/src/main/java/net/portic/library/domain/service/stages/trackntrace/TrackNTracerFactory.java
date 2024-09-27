package net.portic.library.domain.service.stages.trackntrace;

import lombok.RequiredArgsConstructor;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.ConfigurationService;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class TrackNTracerFactory {

    private final GenericTrackNTracer genericTrackNTracer;
    private final Map<String, TrackNTracer> preparers;
    private final ConfigurationService configurationService;

    public TrackNTracer getTrackNTracer(ExecutionContext executionContext) {
        String preparerKey = executionContext.getExecution().getMsgType() + ".trackntrace";
        String preparerName = configurationService.getPropertyValue(preparerKey);
        return preparers.getOrDefault(preparerName, genericTrackNTracer);
    }
}
