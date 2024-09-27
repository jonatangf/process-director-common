package net.portic.library.domain.service.stages.trackntrace;

import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.ConfigurationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GenericTrackNTracerTest {

    @Mock
    private ConfigurationService configurationService;
    @InjectMocks
    private GenericTrackNTracer genericTrackNTracer;

    @Test
    void testExecuteWithNull() {
        var result = genericTrackNTracer.execute(null);

        assertThat(result).isNull();
    }

    @Test
    void testExecuteWithNonNull() {
        var result = genericTrackNTracer.execute(ExecutionContext.builder().build());

        assertThat(result).isNotNull();
    }
}