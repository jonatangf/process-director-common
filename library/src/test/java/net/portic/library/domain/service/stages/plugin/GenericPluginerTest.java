package net.portic.library.domain.service.stages.plugin;

import net.portic.library.domain.model.ExecutionContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GenericPluginerTest {

    @InjectMocks
    private GenericPluginer genericPluginer;

    @Test
    void testExecuteWithNull() {
        var result = genericPluginer.execute(null);

        assertThat(result).isNull();
    }

    @Test
    void testExecuteWithNonNull() {
        ExecutionContext executionContext = ExecutionContext.builder().build();

        var result = genericPluginer.execute(executionContext);

        assertThat(result).isEqualTo(executionContext);
    }
}