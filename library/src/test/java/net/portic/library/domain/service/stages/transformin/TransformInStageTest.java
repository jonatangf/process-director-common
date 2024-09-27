package net.portic.library.domain.service.stages.transformin;

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
class TransformInStageTest {

    @Mock
    private InTransformerFactory inTransformerFactory;
    @Mock
    private InTransformer inTransformer;
    @InjectMocks
    private TransformInStage transformInStage;

    @Test
    void shouldGetOrder() { assertThat(transformInStage.getOrder()).isEqualTo(1); }

    @Test
    void shouldExecuteAndGenerateAudit() {
        when(inTransformerFactory.getInTransformer(any())).thenReturn(inTransformer);

        ExecutionContext executionContext = ExecutionContext.builder().build();

        when(inTransformer.execute(any())).thenReturn(executionContext);

        var result = transformInStage.execute(executionContext);

        assertThat(result).isEqualTo(executionContext);

        verify(inTransformer).execute(executionContext);

        List<MessageProcessorAudit> audits = executionContext.getMessageProcessorAudits();
        assertThat(audits).hasSize(1);
        assertThat(audits.get(0).getSummary()).startsWith("In transformed by InTransformer");
    }

    @Test
    void shouldExecuteAndGenerateError() {
        when(inTransformerFactory.getInTransformer(any())).thenReturn(inTransformer);
        when(inTransformer.execute(any())).thenThrow(new RuntimeException());

        ExecutionContext inputExecutionContext = ExecutionContext.builder().build();
        var outputExecutionContext = transformInStage.execute(inputExecutionContext);

        assertThat(outputExecutionContext).isEqualTo(inputExecutionContext);

        List<MessageProcessorAudit> audits = inputExecutionContext.getMessageProcessorAudits();
        assertThat(audits).isEmpty();

        List<MessageProcessorError> errors = inputExecutionContext.getMessageProcessorErrors();
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getCode()).isEqualTo("TRANSFORM_IN_ERROR");
        assertThat(errors.get(0).getDescription()).isEqualTo("Error while transforming in");
        assertThat(errors.get(0).isFatal()).isTrue();
    }
}