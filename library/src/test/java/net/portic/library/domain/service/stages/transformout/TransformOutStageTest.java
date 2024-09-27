package net.portic.library.domain.service.stages.transformout;

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
class TransformOutStageTest {

    @Mock
    private OutTransformerFactory outTransformerFactory;
    @Mock
    private OutTransformer outTransformer;
    @InjectMocks
    private TransformOutStage transformOutStage;

    @Test
    void shouldGetOrder() { assertThat(transformOutStage.getOrder()).isEqualTo(5); }

    @Test
    void shouldExecuteAndGenerateAudit() {
        when(outTransformerFactory.getOutTransformer(any())).thenReturn(outTransformer);

        ExecutionContext executionContext = ExecutionContext.builder().build();

        when(outTransformer.execute(any())).thenReturn(executionContext);

        var result = transformOutStage.execute(executionContext);

        assertThat(result).isEqualTo(executionContext);

        verify(outTransformer).execute(executionContext);

        List<MessageProcessorAudit> audits = executionContext.getMessageProcessorAudits();
        assertThat(audits).hasSize(1);
        assertThat(audits.get(0).getSummary()).startsWith("Out transformed by OutTransformer");
    }

    @Test
    void shouldExecuteAndGenerateError() {
        when(outTransformerFactory.getOutTransformer(any())).thenReturn(outTransformer);
        when(outTransformer.execute(any())).thenThrow(new RuntimeException());

        var outputExecutionContext = transformOutStage.execute(ExecutionContext.builder().build());

        List<MessageProcessorAudit> audits = outputExecutionContext.getMessageProcessorAudits();
        assertThat(audits).isEmpty();

        List<MessageProcessorError> errors = outputExecutionContext.getMessageProcessorErrors();
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getCode()).isEqualTo("TRANSFORM_OUT_ERROR");
        assertThat(errors.get(0).getDescription()).isEqualTo("Error while transforming out");
        assertThat(errors.get(0).isFatal()).isTrue();
    }
}