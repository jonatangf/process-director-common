package net.portic.application.integration;

import net.portic.library.domain.ExecutionService;
import net.portic.library.domain.model.Execution;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExecutionIntegrationTest {

    @Autowired
    private ExecutionService executionService;

    @Test
    void shouldExecute() {
        Execution executionMsg1 = Execution.builder()
                .msgType("MSG001")
                .build();

        executionService.execute(executionMsg1);


        Execution executionMsg2 = Execution.builder()
                .msgType("MSG002")
                .build();

        executionService.execute(executionMsg2);

        Execution executionResponse = Execution.builder()
                .msgType("RESPONSE")
                .build();

        executionService.execute(executionResponse);
    }
}
