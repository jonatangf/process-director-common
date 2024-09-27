package net.portic.application.stages.prepare;

import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.stages.prepare.Preparer;
import org.springframework.stereotype.Component;

@Component
public class RESPONSEPreparer extends Preparer {

    @Override
    public ExecutionContext execute(ExecutionContext executionContext) {
        return executionContext;
    }
}
