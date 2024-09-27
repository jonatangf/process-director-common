package net.portic.library.domain.service.stages.validate;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidateStageTest {

    @Mock
    private ValidatorFactory validatorFactory;
    @Mock
    private Validator validator;
    @InjectMocks
    private ValidateStage validateStage;

    @Test
    void shouldGetOrder() { assertThat(validateStage.getOrder()).isEqualTo(2); }

    @Test
    void shouldExecuteAndGenerateAudit() {
        when(validatorFactory.getValidator(any())).thenReturn(validator);

        ExecutionContext executionContext = ExecutionContext.builder().build();

        when(validator.execute(any())).thenReturn(executionContext);

        var result = validateStage.execute(executionContext);

        assertThat(result).isEqualTo(executionContext);

        verify(validator).execute(executionContext);

        List<MessageProcessorAudit> audits = executionContext.getMessageProcessorAudits();
        assertThat(audits).hasSize(1);
        assertThat(audits.get(0).getSummary()).startsWith("Validated by Validator");
    }

    @Test
    void shouldExecuteAndGenerateError() {
        when(validatorFactory.getValidator(any())).thenReturn(validator);
        when(validator.execute(any())).thenThrow(new RuntimeException());

        ExecutionContext inputExecutionContext = ExecutionContext.builder().build();
        var outputExecutionContext = validateStage.execute(inputExecutionContext);

        assertThat(outputExecutionContext).isEqualTo(inputExecutionContext);

        List<MessageProcessorAudit> audits = inputExecutionContext.getMessageProcessorAudits();
        assertThat(audits).isEmpty();

        List<MessageProcessorError> errors = inputExecutionContext.getMessageProcessorErrors();
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getCode()).isEqualTo("VALIDATOR_ERROR");
        assertThat(errors.get(0).getDescription()).isEqualTo("Error while validating");
        assertThat(errors.get(0).isFatal()).isTrue();
    }
}