package net.portic.library.domain.service.stages.transformout;

import lombok.RequiredArgsConstructor;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.ConfigurationService;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class OutTransformerFactory {

    private final GenericOutTransformer genericOutTransformer;
    private final Map<String, OutTransformer> outTransformers;
    private final ConfigurationService configurationService;

    public OutTransformer getOutTransformer(ExecutionContext executionContext) {
        String outTransformerKey = executionContext.getExecution().getMsgType() + "";
        String outTransformerName = configurationService.getPropertyValue(outTransformerKey);
        return outTransformers.getOrDefault(outTransformerName, genericOutTransformer);
    }
}
