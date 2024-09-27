package net.portic.library.domain.service.stages.prepare;

import lombok.extern.slf4j.Slf4j;
import net.portic.library.domain.model.ExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component(GenericPreparer.BEAN_ID)
public class GenericPreparer extends Preparer {

    public static final String BEAN_ID = "GenericPreparer";

    public ExecutionContext execute(ExecutionContext executionContext) {
        return executionContext;
    }
}
