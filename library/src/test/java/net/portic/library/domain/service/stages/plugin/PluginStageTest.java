package net.portic.library.domain.service.stages.plugin;

import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.model.MessageProcessorAudit;
import net.portic.library.domain.model.MessageProcessorError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PluginStageTest {

    @Mock
    private PluginerFactory pluginerFactory;
    @Mock
    private Pluginer pluginer;
    @InjectMocks
    private PluginStage pluginStage;

    @Test
    void shouldGetOrder() {
        assertThat(pluginStage.getOrder()).isEqualTo(4);
    }

    @Test
    void shouldExecuteAndGenerateAudit() {
        when(pluginerFactory.getPluginer(any())).thenReturn(pluginer);

        ExecutionContext executionContext = ExecutionContext.builder().build();

        when(pluginer.execute(any())).thenReturn(executionContext);

        var result = pluginStage.execute(executionContext);

        assertThat(result).isEqualTo(executionContext);

        verify(pluginer).execute(executionContext);

        List<MessageProcessorAudit> audits = executionContext.getMessageProcessorAudits();
        assertThat(audits).hasSize(1);
        assertThat(audits.get(0).getSummary()).startsWith("");
    }

    @Test
    void shouldExecuteAndGenerateError() {
        when(pluginerFactory.getPluginer(any())).thenReturn(pluginer);
        when(pluginer.execute(any())).thenThrow(new RuntimeException());

        ExecutionContext inputExecutionContext = ExecutionContext.builder().build();
        var outputExecutionContext = pluginStage.execute(inputExecutionContext);

        assertThat(outputExecutionContext).isEqualTo(inputExecutionContext);

        List<MessageProcessorAudit> audits = inputExecutionContext.getMessageProcessorAudits();
        assertThat(audits).isEmpty();

        List<MessageProcessorError> errors = inputExecutionContext.getMessageProcessorErrors();
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getCode()).isEqualTo("PLUGINER_ERROR");
        assertThat(errors.get(0).getDescription()).isEqualTo("Error while plugining");
        assertThat(errors.get(0).isFatal()).isTrue();
    }
}