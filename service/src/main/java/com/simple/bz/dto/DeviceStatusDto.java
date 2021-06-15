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
@Entity
@Table(name="DeviceStatus")
public class DeviceStatusDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long     deviceId;
    private String   Device;
    private String   BatteryVoltage; //电压；
    private int      BatteryPercentage; //当前电池电量
    private int      LinkQuality;    //当前Zigbee网络联接质量
    private String   Temperature;    //当前温度

    private String   Endpoint;       //当前能力组
    private String   ClusterId;      //能力分组ID
    private String   ClusterValue;    //当前值。

    private boolean  online;
    private String   UpTime;
    private String   Time;
}
