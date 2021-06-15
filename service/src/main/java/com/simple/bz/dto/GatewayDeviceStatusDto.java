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

public class GatewayDeviceStatusDto {
    private Long id;
    private Long     gatewayDeviceId;
    private String   nickName;
    private boolean  active;
    private String   upTime;
    private String   locationTopic;
    private int      SleepSeconds;
    private String   SleepMode;
    private int      Heap;
    private int      LoadAvg;
    private int      MqttCount;
    private DeviceWifi Wifi;


}
