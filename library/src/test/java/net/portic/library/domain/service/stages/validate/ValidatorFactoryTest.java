package net.portic.library.domain.service.stages.validate;

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
class ValidatorFactoryTest {

    @Mock
    private GenericValidator genericValidator;
    @Mock
    private Validator validator;
    @Mock
    private ConfigurationService configurationService;
    @InjectMocks
    private ValidatorFactory validatorFactory;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(validatorFactory, "genericValidator", genericValidator);
        ReflectionTestUtils.setField(validatorFactory, "validators", Map.of());
    }

    @Test
    void getPreparerWhenExists() {
        when(configurationService.getPropertyValue("MSG001.validate")).thenReturn("MSG001Validator");

        ExecutionContext executionContext = ExecutionContext.builder()
                .execution(Execution.builder()
                        .msgType("MSG001")
                        .build())
                .build();

        Validator validator = validatorFactory.getValidator(executionContext);

        assertThat(validator).isEqualTo(validator);
    }

    @Test
    void getPreparerWhenNotExists() {
        when(configurationService.getPropertyValue("MSG002.validate")).thenReturn("MSG002Validator");

        ExecutionContext executionContext = ExecutionContext.builder()
                .execution(Execution.builder()
                        .msgType("MSG002")
                        .build())
                .build();

        Validator validator = validatorFactory.getValidator(executionContext);

        assertThat(validator).isEqualTo(genericValidator);
    }
}