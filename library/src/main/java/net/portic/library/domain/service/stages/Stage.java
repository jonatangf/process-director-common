package net.portic.library.domain.service.stages;

import net.portic.library.domain.model.ExecutionContext;

public abstract class Stage {
    public abstract int getOrder();

    public abstract ExecutionContext execute(ExecutionContext executionContext);
}
