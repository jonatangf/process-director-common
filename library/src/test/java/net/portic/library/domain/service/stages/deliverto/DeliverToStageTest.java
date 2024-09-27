package net.portic.library.domain.service.stages.deliverto;

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
class DeliverToStageTest {

    @Mock
    private DelivererFactory delivererFactory;
    @Mock
    private Deliverer deliverer;
    @InjectMocks
    private DeliverToStage deliverToStage;

    @Test
    void shouldGetOrder() {
        assertThat(deliverToStage.getOrder()).isEqualTo(8);
    }

    @Test
    void shouldExecuteAndGenerateAudit() {
        when(delivererFactory.getDeliverer(any())).thenReturn(deliverer);

        ExecutionContext executionContext = ExecutionContext.builder().build();

        when(deliverer.execute(any())).thenReturn(executionContext);

        var result = deliverToStage.execute(executionContext);

        assertThat(result).isEqualTo(executionContext);

        verify(deliverer).execute(executionContext);

        List<MessageProcessorAudit> audits = executionContext.getMessageProcessorAudits();
        assertThat(audits).hasSize(1);
        assertThat(audits.get(0).getSummary()).startsWith("Delivered by Deliverer");
    }

    @Test
    void shouldExecuteAndGenerateError() {
        when(delivererFactory.getDeliverer(any())).thenReturn(deliverer);
        when(deliverer.execute(any())).thenThrow(new RuntimeException());

        ExecutionContext inputExecutionContext = ExecutionContext.builder().build();
        var outputExecutionContext = deliverToStage.execute(inputExecutionContext);

        assertThat(outputExecutionContext).isEqualTo(inputExecutionContext);

        List<MessageProcessorAudit> audits = outputExecutionContext.getMessageProcessorAudits();
        assertThat(audits).isEmpty();

        List<MessageProcessorError> errors = outputExecutionContext.getMessageProcessorErrors();
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getCode()).isEqualTo("DELIVERER_ERROR");
        assertThat(errors.get(0).getDescription()).isEqualTo("Error while preparing");
        assertThat(errors.get(0).isFatal()).isTrue();
    }
}