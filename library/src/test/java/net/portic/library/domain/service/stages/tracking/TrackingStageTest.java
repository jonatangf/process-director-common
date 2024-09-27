package net.portic.library.domain.service.stages.tracking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

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
    }
}