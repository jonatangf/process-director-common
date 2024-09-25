package net.portic.processdirector.common.domain.service.stages;

import net.portic.processdirector.common.domain.model.ExecutionContext;

public abstract class Stage {
    public abstract int getOrder();

    public abstract ExecutionContext execute(ExecutionContext executionContext);
}
