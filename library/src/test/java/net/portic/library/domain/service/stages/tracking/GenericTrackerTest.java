package net.portic.library.domain.service.stages.tracking;

import net.portic.library.domain.model.ExecutionContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GenericTrackerTest {

    @InjectMocks
    private GenericTracker genericTracker;

    @Test
    void testExecuteWithNull() {
        var result = genericTracker.execute(null);

        assertThat(result).isNull();
    }

    @Test
    void testExecuteWithNonNull() {
        ExecutionContext executionContext = ExecutionContext.builder().build();

        var result = genericTracker.execute(executionContext);

        assertThat(result).isEqualTo(executionContext);
    }
}