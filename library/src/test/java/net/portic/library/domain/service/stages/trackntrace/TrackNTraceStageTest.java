package net.portic.library.domain.service.stages.trackntrace;

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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrackNTraceStageTest {

    @Mock
    private TrackNTracer trackNTracer;
    @Mock
    private TrackNTracerFactory trackNTracerFactory;
    @InjectMocks
    private TrackNTraceStage trackNTraceStage;

    @Test
    void getOrder() {
        assertThat(trackNTraceStage.getOrder()).isEqualTo(3);
    }

    @Test
    void testExecuteAndGenerateAudit() {
        when(trackNTracerFactory.getTrackNTracer(any())).thenReturn(trackNTracer);
        when(trackNTracer.execute(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ExecutionContext outputExecutionContext = trackNTraceStage.execute(ExecutionContext.builder().build());

        List<MessageProcessorError> errors = outputExecutionContext.getMessageProcessorErrors();
        assertThat(errors).isEmpty();

        List<MessageProcessorAudit> audits = outputExecutionContext.getMessageProcessorAudits();
        assertThat(audits).hasSize(1);
        assertThat(audits.get(0).getSummary()).startsWith("Tracked and traced by TrackNTracer");
    }

    @Test
    void testExecuteWithGenericTrackNTracerAndGenerateError() {
        when(trackNTracerFactory.getTrackNTracer(any())).thenReturn(trackNTracer);
        doThrow(new RuntimeException()).when(trackNTracer).execute(any());

        ExecutionContext outputExecutionContext = trackNTraceStage.execute(ExecutionContext.builder().build());

        List<MessageProcessorAudit> audits = outputExecutionContext.getMessageProcessorAudits();
        assertThat(audits).isEmpty();

        List<MessageProcessorError> errors = outputExecutionContext.getMessageProcessorErrors();
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getCode()).isEqualTo("TRACK_N_TRACE_ERROR");
        assertThat(errors.get(0).getDescription()).isEqualTo("Error while tracking and tracing");
        assertThat(errors.get(0).isFatal()).isTrue();
    }
}