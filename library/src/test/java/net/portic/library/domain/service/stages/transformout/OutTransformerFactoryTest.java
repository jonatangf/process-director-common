package net.portic.library.domain.service.stages.transformout;

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
class OutTransformerFactoryTest {

    @Mock
    private GenericOutTransformer genericOutTransformer;
    @Mock
    private OutTransformer outTransformer;
    @Mock
    private ConfigurationService configurationService;
    @InjectMocks
    private OutTransformerFactory outTransformerFactory;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(outTransformerFactory, "genericOutTransformer", genericOutTransformer);
        ReflectionTestUtils.setField(outTransformerFactory, "outTransformers", Map.of());
    }

    @Test
    void getPreparerWhenExists() {
        when(configurationService.getPropertyValue("MSG001.outTransform")).thenReturn("MSGOO1OutTransform");

        ExecutionContext executionContext = ExecutionContext.builder()
                .execution(Execution.builder()
                        .msgType("MSG001")
                        .build())
                .build();

        OutTransformer outTransformer = outTransformerFactory.getOutTransformer(executionContext);

        assertThat(outTransformer).isEqualTo(outTransformer);
    }

    @Test
    void getPreparerWhenNotExists() {
        when(configurationService.getPropertyValue("MSG002.outTransform")).thenReturn("MSG002OutTransformer");

        ExecutionContext executionContext = ExecutionContext.builder()
                .execution(Execution.builder()
                        .msgType("MSG002")
                        .build())
                .build();

        OutTransformer outTransformer = outTransformerFactory.getOutTransformer(executionContext);

        assertThat(outTransformer).isEqualTo(genericOutTransformer);
    }
}