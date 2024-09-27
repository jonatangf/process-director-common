package net.portic.library.domain.model;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Execution {
    private String msgType;
    private String sender;
    private String receiver;
    private String fileIn;
    private String idFormat;
    private String numMens;
    private String numRef;
    private String log;
}
