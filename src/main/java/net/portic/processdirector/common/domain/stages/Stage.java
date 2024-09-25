package net.portic.processdirector.common.domain.stages;

public abstract class Stage {
    public abstract int getOrder();

    public abstract void execute();
}
