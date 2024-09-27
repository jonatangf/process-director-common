package net.portic.library.domain.service.stages.tracking;

import lombok.extern.slf4j.Slf4j;
import net.portic.library.domain.model.ExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component(GenericTracker.BEAN_ID)
public class GenericTracker extends Tracker {

    public static final String BEAN_ID = "GenericTracker";

    public ExecutionContext execute(ExecutionContext executionContext) {
        return executionContext;
    }
}
