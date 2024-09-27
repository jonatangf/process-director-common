package net.portic.library.domain.model;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MessageProcessorAudit {
    private String summary;
}
