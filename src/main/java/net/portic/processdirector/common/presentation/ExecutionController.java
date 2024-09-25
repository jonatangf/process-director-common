package net.portic.processdirector.common.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.portic.processdirector.common.domain.ExecutionService;
import net.portic.processdirector.common.domain.model.Execution;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/execution")
public class ExecutionController {

    private final ExecutionService executionService;

    @PostMapping("/execute")
    public void execute(
            @RequestBody ExecutionDTO executionDTO
    ) {
        log.info("Received request to execute...");
        executionService.execute(
                Execution.builder()
                        .idProc(executionDTO.getIdProc())
                        .sender(executionDTO.getSender())
                        .receiver(executionDTO.getReceiver())
                        .fileIn(executionDTO.getFileIn())
                        .idFormat(executionDTO.getIdFormat())
                        .numMens(executionDTO.getNumMens())
                        .numRef(executionDTO.getNumRef())
                        .build()
        );
    }
}
