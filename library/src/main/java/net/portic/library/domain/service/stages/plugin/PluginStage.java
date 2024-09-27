package net.portic.library.domain.service.stages.plugin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.model.MessageProcessorAudit;
import net.portic.library.domain.model.MessageProcessorError;
import net.portic.library.domain.service.stages.Stage;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PluginStage extends Stage {

    private final PluginerFactory pluginerFactory;

    @Override
    public int getOrder() {
        return 4;
    }

    public ExecutionContext execute(ExecutionContext executionContext) {
        Pluginer pluginer = pluginerFactory.getPluginer(executionContext);

        try {
            ExecutionContext outputExecutionContext = pluginer.execute(executionContext);
            outputExecutionContext.getMessageProcessorAudits().add(MessageProcessorAudit.builder()
                    .summary("Plugined by " + pluginer.getClass().getSimpleName())
                    .build());
            return outputExecutionContext;
        } catch (Exception e) {
            executionContext.getMessageProcessorErrors().add(MessageProcessorError.builder()
                    .code("PLUGINER_ERROR")
                    .description("Error while plugining")
                    .isFatal(true)
                    .build());
            return executionContext;
        }
    }
}
