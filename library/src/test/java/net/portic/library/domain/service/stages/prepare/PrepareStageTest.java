package net.portic.library.domain.service.stages.prepare;

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
class PrepareStageTest {

    @Mock
    private PreparerFactory preparerFactory;
    @Mock
    private Preparer preparer;
    @InjectMocks
    private PrepareStage prepareStage;

    @Test
    void shouldGetOrder() {
        assertThat(prepareStage.getOrder()).isZero();
    }

    @Test
    void shouldExecuteAndGenerateAudit() {
        when(preparerFactory.getPreparer(any())).thenReturn(preparer);

        ExecutionContext executionContext = ExecutionContext.builder().build();

        when(preparer.execute(any())).thenReturn(executionContext);

        var result = prepareStage.execute(executionContext);

        assertThat(result).isEqualTo(executionContext);

        verify(preparer).execute(executionContext);

        List<MessageProcessorAudit> audits = executionContext.getMessageProcessorAudits();
        assertThat(audits).hasSize(1);
        assertThat(audits.get(0).getSummary()).startsWith("Prepared by Preparer");
    }

    @Test
    void shouldExecuteAndGenerateError() {
        when(preparerFactory.getPreparer(any())).thenReturn(preparer);
        when(preparer.execute(any())).thenThrow(new RuntimeException());

        ExecutionContext inputExecutionContext = ExecutionContext.builder().build();
        var outputExecutionContext = prepareStage.execute(inputExecutionContext);

        assertThat(outputExecutionContext).isEqualTo(inputExecutionContext);

        List<MessageProcessorAudit> audits = inputExecutionContext.getMessageProcessorAudits();
        assertThat(audits).isEmpty();

        List<MessageProcessorError> errors = inputExecutionContext.getMessageProcessorErrors();
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getCode()).isEqualTo("PREPARE_ERROR");
        assertThat(errors.get(0).getDescription()).isEqualTo("Error while preparing");
        assertThat(errors.get(0).isFatal()).isTrue();
    }
}