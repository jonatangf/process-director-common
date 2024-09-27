package net.portic.library.domain.service.stages.transformin;

import lombok.extern.slf4j.Slf4j;
import net.portic.library.domain.model.ExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component(GenericInTransformer.BEAN_ID)
public class GenericInTransformer extends InTransformer {

    public static final String BEAN_ID = "GenericInTransformer";

    public ExecutionContext execute(ExecutionContext executionContext) {
        return executionContext;
    }
}
