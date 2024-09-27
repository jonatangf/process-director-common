package net.portic.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ExecutionDTO {
    private String msgType;
    private String sender;
    private String receiver;
    private String fileIn;
    private String idFormat;
    private String numMens;
    private String numRef;
}
