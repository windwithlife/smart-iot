package com.simple.bz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GatewayCommandRequest {

    private Long     gatewayId;
    private Long     houseId;
    private String   macAddress;
    private String   locationTopic;

}
