package net.portic.library.domain.service.stages.plugin;

import lombok.RequiredArgsConstructor;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.ConfigurationService;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class PluginerFactory {

    private final GenericPluginer genericPluginer;
    private final Map<String, Pluginer> pluginers;
    private final ConfigurationService configurationService;

    public Pluginer getPluginer(ExecutionContext executionContext) {
        String pluginerKey = executionContext.getExecution().getMsgType() + ".plugin";
        String pluginerName = configurationService.getPropertyValue(pluginerKey);
        return pluginers.getOrDefault(pluginerName, genericPluginer);
    }
}
