package net.portic.library.domain.service.stages.tracking;

import lombok.RequiredArgsConstructor;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.ConfigurationService;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class TrackerFactory {

    private final GenericTracker genericTracker;
    private final Map<String, Tracker> trackers;
    private final ConfigurationService configurationService;

    public Tracker getTracker(ExecutionContext executionContext) {
        String trackerKey = executionContext.getExecution().getMsgType() + ".track";
        String trackerName = configurationService.getPropertyValue(trackerKey);
        return trackers.getOrDefault(trackerName, genericTracker);
    }
}
