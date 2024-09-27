package net.portic.library.domain.service.stages.prepare;

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
class PreparerFactoryTest {

    @Mock
    private GenericPreparer genericPreparer;
    @Mock
    private Preparer preparer;
    @Mock
    private ConfigurationService configurationService;
    @InjectMocks
    private PreparerFactory preparerFactory;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(preparerFactory, "genericPreparer", genericPreparer);
        ReflectionTestUtils.setField(preparerFactory, "preparers", Map.of("MSG001Preparer", preparer));
    }

    @Test
    void getPreparerWhenExists() {
        when(configurationService.getPropertyValue("MSG001.prepare")).thenReturn("MSG001Preparer");

        ExecutionContext executionContext = ExecutionContext.builder()
                .execution(Execution.builder()
                        .msgType("MSG001")
                        .build())
                .build();

        Preparer preparer = preparerFactory.getPreparer(executionContext);

        assertThat(preparer).isEqualTo(preparer);
    }

    @Test
    void getPreparerWhenNotExists() {
        when(configurationService.getPropertyValue("MSG002.prepare")).thenReturn("MSG002Preparer");

        ExecutionContext executionContext = ExecutionContext.builder()
                .execution(Execution.builder()
                        .msgType("MSG002")
                        .build())
                .build();

        Preparer preparer = preparerFactory.getPreparer(executionContext);

        assertThat(preparer).isEqualTo(genericPreparer);
    }
}