package net.portic.library.domain.service.stages.prepare;

import lombok.RequiredArgsConstructor;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.ConfigurationService;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class PreparerFactory {

    private final GenericPreparer genericPreparer;
    private final Map<String, Preparer> preparers;
    private final ConfigurationService configurationService;

    public Preparer getPreparer(ExecutionContext executionContext) {
        String preparerKey = executionContext.getExecution().getMsgType() + ".prepare";
        String preparerName = configurationService.getPropertyValue(preparerKey);
        return preparers.getOrDefault(preparerName, genericPreparer);
    }
}
