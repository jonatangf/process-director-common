package net.portic.application.integration;

import net.portic.application.stages.prepare.MSG001Preparer;
import net.portic.library.domain.model.Execution;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.stages.prepare.Preparer;
import net.portic.library.domain.service.stages.prepare.PreparerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PrepareStageIntegrationTest {

    @Autowired
    private PreparerFactory preparerFactory;

    @Test
    void contextLoads() {
        Preparer preparer = preparerFactory.getPreparer(ExecutionContext.builder()
                .execution(Execution.builder().msgType("MSG001").build())
                .build());

        assertThat(preparer).isNotNull().isInstanceOf(MSG001Preparer.class);
    }
}
