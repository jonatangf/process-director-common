package net.portic.library.domain.service.stages.transformin;

import lombok.RequiredArgsConstructor;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.ConfigurationService;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class InTransformerFactory {

    private final GenericInTransformer genericInTransformer;
    private final Map<String, InTransformer> intransformers;
    private final ConfigurationService configurationService;

    public InTransformer getInTransformer(ExecutionContext executionContext) {
        String intransformerKey = executionContext.getExecution().getMsgType() + ".inTransform";
        String intransformerName = configurationService.getPropertyValue(intransformerKey);
        return intransformers.getOrDefault(intransformerName, genericInTransformer);
    }
}
