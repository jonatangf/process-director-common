package net.portic.application.stages.deliverto;

import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.stages.deliverto.Deliverer;
import org.springframework.stereotype.Component;

@Component
public class MSG002Deliverer extends Deliverer {

    @Override
    public ExecutionContext execute(ExecutionContext executionContext) {
        return executionContext;
    }
}
