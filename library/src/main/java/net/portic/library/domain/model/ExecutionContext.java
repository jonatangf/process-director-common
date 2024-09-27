package net.portic.library.domain.model;

import lombok.*;

import java.util.LinkedList;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class ExecutionContext {
    private Execution execution;
    @Builder.Default
    private List<MessageProcessorError> messageProcessorErrors = new LinkedList<>();
    @Builder.Default
    private List<MessageProcessorAudit> messageProcessorAudits = new LinkedList<>();
}
