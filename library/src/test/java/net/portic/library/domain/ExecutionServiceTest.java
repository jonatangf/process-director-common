package net.portic.library.domain;

import net.portic.library.domain.model.Execution;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.stages.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExecutionServiceTest {

    @Mock
    private Stage stageA;
    @Mock
    private Stage stageB;
    @Mock
    private Stage stageC;
    @InjectMocks
    private ExecutionService executionService;

    @Test
    void testInitialize() {
        ReflectionTestUtils.setField(executionService, "stages", new ArrayList<>(List.of(stageA, stageB, stageC)));

        when(stageA.getOrder()).thenReturn(3);
        when(stageB.getOrder()).thenReturn(1);
        when(stageC.getOrder()).thenReturn(2);

        executionService.initialize();

        List<Stage> stages = (List<Stage>) ReflectionTestUtils.getField(executionService, "stages");
        assertThat(stages).hasSize(3);
        assertThat(stages.get(0)).isEqualTo(stageB);
        assertThat(stages.get(1)).isEqualTo(stageC);
        assertThat(stages.get(2)).isEqualTo(stageA);
    }

    @Test
    void testExecute() {
        ReflectionTestUtils.setField(executionService, "stages", List.of(stageA, stageB, stageC));
        when(stageA.execute(any())).thenAnswer(ExecutionServiceTest::getExecutionContext);
        when(stageB.execute(any())).thenAnswer(ExecutionServiceTest::getExecutionContext);
        when(stageC.execute(any())).thenAnswer(ExecutionServiceTest::getExecutionContext);

        Execution execution = Execution.builder().build();

        ExecutionContext executionContext = executionService.execute(execution);

        assertThat(executionContext).isNotNull();
        assertThat(executionContext.getExecution()).isEqualTo(execution);
        verify(stageA).execute(any());
        verify(stageB).execute(any());
        verify(stageC).execute(any());
    }

    private static ExecutionContext getExecutionContext(InvocationOnMock invocation) {
        ExecutionContext context = invocation.getArgument(0);
        return ExecutionContext.builder().execution(context.getExecution()).build();
    }

}