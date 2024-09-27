package net.portic.application.stages.plugin;

import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.stages.plugin.Pluginer;
import org.springframework.stereotype.Component;

@Component
public class MSG002Pluginer extends Pluginer {

    @Override
    public ExecutionContext execute(ExecutionContext executionContext) {
        return executionContext;
    }
}
