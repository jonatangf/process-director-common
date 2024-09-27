package net.portic.library.domain.model;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MessageProcessorError {

    private String code;
    private String description;
    private boolean isFatal;
}
