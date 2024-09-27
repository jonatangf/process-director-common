package net.portic.library.domain.service.stages.transformout;

import lombok.extern.slf4j.Slf4j;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.stages.prepare.GenericPreparer;
import org.springframework.stereotype.Component;

@Slf4j
@Component(GenericOutTransformer.BEAN_ID)
public class GenericOutTransformer extends OutTransformer {

    public static final String BEAN_ID = "GenericOutTransformer";

    public ExecutionContext execute(ExecutionContext executionContext) {
        return executionContext;
    }
}
