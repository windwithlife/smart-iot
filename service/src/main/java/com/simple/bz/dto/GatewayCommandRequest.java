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

    private Long     id;
    private Long     houseId;
    private String   name;
    private String   macAddress;
    private String   locationTopic;


}
