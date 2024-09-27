package net.portic.library.domain.service.stages.validate;

import lombok.extern.slf4j.Slf4j;
import net.portic.library.domain.model.ExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component(GenericValidator.BEAN_ID)
public class GenericValidator extends Validator {

    public static final String BEAN_ID = "GenericVAlidator";

    public ExecutionContext execute(ExecutionContext executionContext) {
        return executionContext;
    }
}
