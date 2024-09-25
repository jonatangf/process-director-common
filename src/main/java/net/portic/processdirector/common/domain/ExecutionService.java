package net.portic.processdirector.common.domain;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.portic.processdirector.common.domain.model.Execution;
import net.portic.processdirector.common.domain.model.ExecutionContext;
import net.portic.processdirector.common.domain.service.stages.Stage;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExecutionService {

    private final List<Stage> stages;

    public void execute(Execution execution) {
        ExecutionContext context = ExecutionContext.builder()
                .execution(execution)
                .build();

        for (Stage stage : stages) {
            log.info("Executing stage {}", stage.getClass().getSimpleName());
            context = stage.execute(context);
        }

        log.info("Execution completed, final context: {}", context);
    }

    @PostConstruct
    public void init() {
        log.info("Initializing execution service with {} stages", stages.size());

        stages.sort(Comparator.comparingInt(Stage::getOrder));
    }
}
