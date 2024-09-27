package net.portic.library.domain.service.stages.transformin;

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
class InTransformerFactoryTest {

    @Mock
    private GenericInTransformer genericInTransformer;
    @Mock
    private InTransformer inTransformer;
    @Mock
    private ConfigurationService configurationService;
    @InjectMocks
    private InTransformerFactory inTransformerFactory;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(inTransformerFactory, "genericInTransformer", genericInTransformer);
        ReflectionTestUtils.setField(inTransformerFactory, "intransformers", Map.of());
    }

    @Test
    void getPreparerWhenExists() {
        when(configurationService.getPropertyValue("MSG001.inTransform")).thenReturn("MSG001InTransformer");

        ExecutionContext executionContext = ExecutionContext.builder()
                .execution(Execution.builder()
                        .msgType("MSG001")
                        .build())
                .build();

        InTransformer inTransformer = inTransformerFactory.getInTransformer(executionContext);

        assertThat(inTransformer).isEqualTo(inTransformer);
    }

    @Test
    void getPreparerWhenNotExists() {
        when(configurationService.getPropertyValue("MSG002.inTransform")).thenReturn("MSG002InTransformer");

        ExecutionContext executionContext = ExecutionContext.builder()
                .execution(Execution.builder()
                        .msgType("MSG002")
                        .build())
                .build();

        InTransformer inTransformer = inTransformerFactory.getInTransformer(executionContext);

        assertThat(inTransformer).isEqualTo(genericInTransformer);
    }
}