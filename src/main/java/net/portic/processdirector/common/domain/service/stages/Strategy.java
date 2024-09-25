package net.portic.processdirector.common.domain.service.stages;

import lombok.RequiredArgsConstructor;
import net.portic.processdirector.common.domain.model.ExecutionContext;
import net.portic.processdirector.common.domain.service.ConfigurationService;

@RequiredArgsConstructor
public abstract class Strategy {

    private final ConfigurationService configurationService;

    public final boolean supports(ExecutionContext executionContext) {
        String value = configurationService.getProperty(executionContext.getExecution().getIdProc() + "." + getStageName());
        return this.getClass().getSimpleName().equals(value);
    }

    public abstract ExecutionContext execute(ExecutionContext executionContext);

    public abstract String getStageName();


}
