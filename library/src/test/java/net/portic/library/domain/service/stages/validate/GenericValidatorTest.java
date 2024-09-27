package net.portic.library.domain.service.stages.validate;

import net.portic.library.domain.model.ExecutionContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GenericValidatorTest {

    @InjectMocks
    private GenericValidator genericValidator;

    @Test
    void testExecuteWithNull() {
        var result = genericValidator.execute(null);

        assertThat(result).isNull();
    }

    @Test
    void testExecuteWithNonNull() {
        ExecutionContext executionContext = ExecutionContext.builder().build();

        var result = genericValidator.execute(executionContext);

        assertThat(result).isEqualTo(executionContext);
    }
}