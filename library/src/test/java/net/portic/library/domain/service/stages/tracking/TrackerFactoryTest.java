package net.portic.library.domain.service.stages.tracking;

import net.portic.library.domain.model.Execution;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.ConfigurationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrackerFactoryTest {

    @Mock
    private GenericTracker genericTracker;
    @Mock
    private Tracker tracker;
    @Mock
    private ConfigurationService configurationService;
    @InjectMocks
    private TrackerFactory trackerFactory;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(trackerFactory, "genericTracker", genericTracker);
        ReflectionTestUtils.setField(trackerFactory, "trackers", Map.of());
    }

    @Test
    void getPreparerWhenExists() {
        when(configurationService.getPropertyValue("MSG001.track")).thenReturn("MSG001Tracker");

        ExecutionContext executionContext = ExecutionContext.builder()
                .execution(Execution.builder()
                        .msgType("MSG001")
                        .build())
                .build();

        Tracker tracker = trackerFactory.getTracker(executionContext);

        assertThat(tracker).isEqualTo(genericTracker);
    }

    @Test
    void getPreparerWhenNotExists() {
        when(configurationService.getPropertyValue("MSG002.track")).thenReturn("MSG002Tracker");

        ExecutionContext executionContext = ExecutionContext.builder()
                .execution(Execution.builder()
                        .msgType("MSG002")
                        .build())
                .build();

        Tracker tracker = trackerFactory.getTracker(executionContext);

        assertThat(tracker).isEqualTo(genericTracker);
    }
}