package net.portic.library.domain.service.stages.plugin;

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
class PluginerFactoryTest {

    @Mock
    private GenericPluginer genericPluginer;
    @Mock
    private Pluginer pluginer;
    @Mock
    private ConfigurationService configurationService;
    @InjectMocks
    private PluginerFactory pluginerFactory;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(pluginerFactory, "genericPluginer", genericPluginer);
        ReflectionTestUtils.setField(pluginerFactory, "pluginers", Map.of("MSG001Pluginer", pluginer));
    }

    @Test
    void getPreparerWhenExists() {
        when(configurationService.getPropertyValue("MSG001.plugin")).thenReturn("MSG001Pluginer");

        ExecutionContext executionContext = ExecutionContext.builder()
                .execution(Execution.builder()
                        .msgType("MSG001")
                        .build())
                .build();

        Pluginer pluginer = pluginerFactory.getPluginer(executionContext);

        assertThat(pluginer).isEqualTo(pluginer);
    }

    @Test
    void getPreparerWhenNotExists() {
        when(configurationService.getPropertyValue("MSG002.plugin")).thenReturn("MSG002Pluginer");

        ExecutionContext executionContext = ExecutionContext.builder()
                .execution(Execution.builder()
                        .msgType("MSG002")
                        .build())
                .build();

        Pluginer pluginer = pluginerFactory.getPluginer(executionContext);

        assertThat(pluginer).isEqualTo(genericPluginer);
    }
}