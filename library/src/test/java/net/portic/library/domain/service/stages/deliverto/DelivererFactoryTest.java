package net.portic.library.domain.service.stages.deliverto;

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
class DelivererFactoryTest {

    @Mock
    private GenericDeliverer genericDeliverer;
    @Mock
    private Deliverer deliverer;
    @Mock
    private ConfigurationService configurationService;
    @InjectMocks
    private DelivererFactory delivererFactory;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(delivererFactory, "genericDeliverer", genericDeliverer);
        ReflectionTestUtils.setField(delivererFactory, "deliverers", Map.of("MSG001Deliverer", deliverer));
    }

    @Test
    void getPreparerWhenExists() {
        when(configurationService.getPropertyValue("MSG001.deliver")).thenReturn("MSG001Deliverer");

        ExecutionContext executionContext = ExecutionContext.builder()
                .execution(Execution.builder()
                        .msgType("MSG001")
                        .build())
                .build();

        Deliverer deliverer = delivererFactory.getDeliverer(executionContext);

        assertThat(deliverer).isEqualTo(deliverer);
    }

    @Test
    void getPreparerWhenNotExists() {
        when(configurationService.getPropertyValue("MSG002.deliver")).thenReturn("MSG002Deliverer");

        ExecutionContext executionContext = ExecutionContext.builder()
                .execution(Execution.builder()
                        .msgType("MSG002")
                        .build())
                .build();

        Deliverer deliverer = delivererFactory.getDeliverer(executionContext);

        assertThat(deliverer).isEqualTo(genericDeliverer);
    }
}