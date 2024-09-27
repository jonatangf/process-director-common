package net.portic.library.domain.service.stages.deliverto;

import lombok.extern.slf4j.Slf4j;
import net.portic.library.domain.model.ExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component(GenericDeliverer.BEAN_ID)
public class GenericDeliverer extends Deliverer {

    public static final String BEAN_ID = "GenericDeliverer";

    public ExecutionContext execute(ExecutionContext executionContext) {
        return executionContext;
    }
}
