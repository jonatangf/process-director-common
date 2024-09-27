package net.portic.library.domain.service.stages.transformin;

import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.stages.trackntrace.GenericTrackNTracer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GenericInTransformerTest {

    @InjectMocks
    private GenericInTransformer genericInTransformer;

    @Test
    void testExecuteWithNull() {
        var result = genericInTransformer.execute(null);

        assertThat(result).isNull();
    }

    @Test
    void testExecuteWithNonNull() {
        ExecutionContext executionContext = ExecutionContext.builder().build();

        var result = genericInTransformer.execute(executionContext);

        assertThat(result).isEqualTo(executionContext);
    }
}