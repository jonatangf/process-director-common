package net.portic.library.domain.service.stages.trackntrace;

import net.portic.library.domain.model.Execution;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.ConfigurationService;
import net.portic.library.domain.service.stages.tracking.Tracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrackNTracerFactoryTest {

    @Mock
    private GenericTrackNTracer genericTrackNTracer;
    @Mock
    private Tracker tracker;
    @Mock
    private ConfigurationService configurationService;
    @InjectMocks
    private TrackNTracerFactory trackNTracerFactory;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(trackNTracerFactory, "genericTrackNTracer", genericTrackNTracer);
        ReflectionTestUtils.setField(trackNTracerFactory, "trackNTracers", Map.of());
    }

    @Test
    void getPreparerWhenExists() {
        when(configurationService.getPropertyValue("MSG001.trackntrace")).thenReturn("MSG001Trackntracer");

        ExecutionContext executionContext = ExecutionContext.builder()
                .execution(Execution.builder()
                        .msgType("MSG001")
                        .build())
                .build();

        TrackNTracer trackNTracer = trackNTracerFactory.getTrackNTracer(executionContext);

        assertThat(trackNTracer).isEqualTo(trackNTracer);
    }

    @Test
    void getPreparerWhenNotExists() {
        when(configurationService.getPropertyValue("MSG002.trackntrace")).thenReturn("MSG002Trackntracer");

        ExecutionContext executionContext = ExecutionContext.builder()
                .execution(Execution.builder()
                        .msgType("MSG002")
                        .build())
                .build();

        TrackNTracer trackNTracer = trackNTracerFactory.getTrackNTracer(executionContext);

        assertThat(trackNTracer).isEqualTo(genericTrackNTracer);
    }
}