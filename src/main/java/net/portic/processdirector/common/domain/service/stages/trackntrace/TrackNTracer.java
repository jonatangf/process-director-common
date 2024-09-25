package net.portic.processdirector.common.domain.service.stages.trackntrace;

import net.portic.processdirector.common.domain.service.ConfigurationService;
import net.portic.processdirector.common.domain.service.stages.Strategy;

public abstract class TrackNTracer extends Strategy {

    public TrackNTracer(ConfigurationService configurationService) {
        super(configurationService);
    }

    public String getStageName() {
        return "trackntrace";
    }
}
