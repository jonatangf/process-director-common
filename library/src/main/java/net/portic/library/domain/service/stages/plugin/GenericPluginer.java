package net.portic.library.domain.service.stages.plugin;

import lombok.extern.slf4j.Slf4j;
import net.portic.library.domain.model.ExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component(GenericPluginer.BEAN_ID)
public class GenericPluginer extends Pluginer {

    public static final String BEAN_ID = "GenericPluginer";

    public ExecutionContext execute(ExecutionContext executionContext) {
        return executionContext;
    }
}
