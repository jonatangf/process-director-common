package net.portic.processdirector.common.domain.service.stages.prepare;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.portic.processdirector.common.domain.model.ExecutionContext;
import net.portic.processdirector.common.domain.service.stages.Stage;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrepareStage extends Stage {

    private final GenericPreparer genericPreparer;
    private final List<Preparer> preparers;

    @Override
    public int getOrder() {
        return 0;
    }

    public ExecutionContext execute(ExecutionContext executionContext) {
        log.info("Preparing from the service ...");
        Preparer preparer = preparers.stream()
                .filter(p -> p.supports(executionContext))
                .findFirst()
                .orElse(genericPreparer);

        return preparer.execute(executionContext);
    }
}
