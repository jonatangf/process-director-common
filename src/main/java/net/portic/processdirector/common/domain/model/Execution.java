package net.portic.processdirector.common.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Execution {
    private String idProc;
    private String sender;
    private String receiver;
    private String fileIn;
    private String idFormat;
    private String numMens;
    private String numRef;
}
