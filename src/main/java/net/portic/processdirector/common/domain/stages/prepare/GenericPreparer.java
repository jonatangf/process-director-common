package net.portic.processdirector.common.domain.stages.prepare;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GenericPreparer extends Preparer {

    @Override
    public boolean supports() {
        return false;
    }

    public void execute() {
        log.info("Preparing with the generic preparer ...");
    }
}
