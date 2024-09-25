package net.portic.processdirector.common.domain.stages.prepare;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.portic.processdirector.common.domain.stages.Stage;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrepareStage extends Stage {

    private final GenericPreparer genericPreparer;
    private final List<Preparer> preparers;

    @Override
    public int getOrder() {
        return 0;
    }

    public void execute() {
        log.info("Preparing from the service ...");
        Preparer preparer = preparers.stream()
                .filter(Preparer::supports)
                .findFirst()
                .orElse(genericPreparer);

        preparer.preExecute();
        preparer.execute();
        preparer.postExecute();
    }
}
