package net.portic.library.domain.service.stages.deliverto;

import lombok.RequiredArgsConstructor;
import net.portic.library.domain.model.ExecutionContext;
import net.portic.library.domain.service.ConfigurationService;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class DelivererFactory {

    private final GenericDeliverer genericDeliverer;
    private final Map<String, Deliverer> deliverers;
    private final ConfigurationService configurationService;

    public Deliverer getDeliverer(ExecutionContext executionContext) {
        String delivererKey = executionContext.getExecution().getMsgType() + ".deliver";
        String delivererName = configurationService.getPropertyValue(delivererKey);
        return deliverers.getOrDefault(delivererName, genericDeliverer);
    }
}
