package net.portic.processdirector.common.domain.stages;

public abstract class Strategy {

    public abstract boolean supports();

    public void preExecute() {

    }

    public abstract void execute();

    public void postExecute() {

    }
}
