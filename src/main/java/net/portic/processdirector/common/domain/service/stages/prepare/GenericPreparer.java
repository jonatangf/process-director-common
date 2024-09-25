package net.portic.processdirector.common.domain.service.stages.prepare;

import lombok.extern.slf4j.Slf4j;
import net.portic.processdirector.common.domain.model.ExecutionContext;
import net.portic.processdirector.common.domain.service.ConfigurationService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GenericPreparer extends Preparer {

    public GenericPreparer(ConfigurationService configurationService) {
        super(configurationService);
    }

    public ExecutionContext execute(ExecutionContext executionContext) {
        log.info("Preparing with the generic preparer ...");
        return executionContext;
    }
}
