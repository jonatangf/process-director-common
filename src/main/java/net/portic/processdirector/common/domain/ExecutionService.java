package net.portic.processdirector.common.domain;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.portic.processdirector.common.domain.stages.Stage;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExecutionService {

    private final List<Stage> stages;

    public void execute() {
        stages.forEach(stage -> {
            log.info("Executing stage {}", stage.getClass().getSimpleName());
            stage.execute();
        });
    }

    @PostConstruct
    public void init() {
        log.info("Initializing execution service with {} stages", stages.size());

        stages.sort(Comparator.comparingInt(Stage::getOrder));
    }
}
