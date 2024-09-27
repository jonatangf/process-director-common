package net.portic.library.domain.service.stages.tracking;

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
class TrackingStageTest {

    @Mock
    private TrackerFactory trackerFactory;
    @Mock
    private Tracker tracker;
    @InjectMocks
    private TrackingStage trackingStage;

    @Test
    void shouldGetOrder() {
        assertThat(trackingStage.getOrder()).isEqualTo(6);
    }

    @Test
    void shouldExecuteAndGenerateAudit() {
        when(trackerFactory.getTracker(any())).thenReturn(tracker);

        ExecutionContext executionContext = ExecutionContext.builder().build();

        when(tracker.execute(any())).thenReturn(executionContext);

        var result = trackingStage.execute(executionContext);

        assertThat(result).isEqualTo(executionContext);

        verify(tracker).execute(executionContext);

        List<MessageProcessorAudit> audits = executionContext.getMessageProcessorAudits();
        assertThat(audits).hasSize(1);
        assertThat(audits.get(0).getSummary()).startsWith("Trackered by Tracker");
    }

    @Test
    void shouldExecuteAndGenerateError() {
        when(trackerFactory.getTracker(any())).thenReturn(tracker);
        when(tracker.execute(any())).thenThrow(new RuntimeException());

        ExecutionContext inputExecutionContext = ExecutionContext.builder().build();
        var outputExecutionContext = trackingStage.execute(inputExecutionContext);

        assertThat(outputExecutionContext).isEqualTo(inputExecutionContext);

        List<MessageProcessorAudit> audits = inputExecutionContext.getMessageProcessorAudits();
        assertThat(audits).isEmpty();

        List<MessageProcessorError> errors = inputExecutionContext.getMessageProcessorErrors();
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getCode()).isEqualTo("TRACKER_ERROR");
        assertThat(errors.get(0).getDescription()).isEqualTo("Error while preparing");
        assertThat(errors.get(0).isFatal()).isTrue();
    }
}