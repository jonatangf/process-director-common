package net.portic.processdirector.common.domain.service.stages.prepare;

import net.portic.processdirector.common.domain.service.ConfigurationService;
import net.portic.processdirector.common.domain.service.stages.Strategy;

public abstract class Preparer extends Strategy {

    public Preparer(ConfigurationService configurationService) {
        super(configurationService);
    }

    public String getStageName() {
        return "prepare";
    }
}
