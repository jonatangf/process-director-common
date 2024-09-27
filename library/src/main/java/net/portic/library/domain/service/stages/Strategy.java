package net.portic.library.domain.service.stages;

import lombok.RequiredArgsConstructor;
import net.portic.library.domain.model.ExecutionContext;

@RequiredArgsConstructor
public abstract class Strategy {

    public abstract ExecutionContext execute(ExecutionContext executionContext);
}
