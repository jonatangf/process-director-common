package net.portic.application.runner;

import lombok.RequiredArgsConstructor;
import net.portic.library.domain.ExecutionService;
import net.portic.library.domain.model.Execution;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApplicationRunner implements CommandLineRunner {

    private final ExecutionService executionService;

    @Override
    public void run(String... args) throws Exception {
        Execution execution = Execution.builder()
                .msgType("MSG001")
                .build();

        executionService.execute(execution);
    }
}
