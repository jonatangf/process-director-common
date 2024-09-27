package net.portic.library.domain;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.portic.library.domain.model.Execution;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.model.MessageProcessorError;
import net.portic.library.domain.service.stages.Stage;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExecutionService {

    private final List<Stage> stages;

    @PostConstruct
    public void initialize() {
        log.info("Initializing execution service with {} stages", stages.size());

        stages.sort(Comparator.comparingInt(Stage::getOrder));
    }

    public ExecutionContext execute(Execution execution) {
        ExecutionContext context = ExecutionContext.builder()
                .execution(execution)
                .build();

        for (Stage stage : stages) {
            Optional<MessageProcessorError> messageProcessorError = context.getMessageProcessorErrors().stream()
                    .filter(MessageProcessorError::isFatal)
                    .findFirst();

            if (messageProcessorError.isPresent()) {
                log.warn("Execution interrupted due to fatal error: {}", messageProcessorError.get().getCode());
                break;
            } else {
                context = stage.execute(context);
            }
        }

        log.info("Execution completed, final context: {}", context);
        return context;
    }
}
