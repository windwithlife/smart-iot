package com.simple.bz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GatewayCommandResponse {

    private Long     id;
    private String   name;
    private String   macAddress;
    private String   locationTopic;
    private String   message;


}
