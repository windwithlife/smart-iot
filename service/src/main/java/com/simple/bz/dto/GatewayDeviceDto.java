package com.simple.bz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GatewayDeviceDto {

    private Long id;
    private String   nickName;
    private String   model;
    private String   code;
    private boolean  active;
    private Date     upTime;
    private Date     createDate;
    private String   locationTopic;
    private int      mqttCount;

}
