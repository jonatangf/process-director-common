package net.portic.application.stages.prepare;

import lombok.extern.slf4j.Slf4j;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.stages.prepare.Preparer;
import org.springframework.stereotype.Component;

@Slf4j
@Component(value = MSG001Preparer.BEAN_ID)
public class MSG001Preparer extends Preparer {

    public static final String BEAN_ID = "MSG001Preparer";

    @Override
    public ExecutionContext execute(ExecutionContext executionContext) {
        return executionContext;
    }
}
