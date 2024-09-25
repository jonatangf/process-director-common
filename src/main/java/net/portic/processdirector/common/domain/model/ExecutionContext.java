package net.portic.processdirector.common.domain.model;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class ExecutionContext {
    private Execution execution;
}
