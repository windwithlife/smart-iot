package com.simple.bz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="DeviceStatus")
public class DeviceStatusModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long     deviceId;
    private String   name;
    private String   shortAddress; //短地址
    private String   address;      //长地址

    private String   batteryVoltage; //电压；
    private int      batteryPercentage; //当前电池电量
    private int      linkQuality;    //当前Zigbee网络联接质量
    private String   temperature;    //当前温度

    private String   endpoint;       //当前能力组
    private String   clusterId;      //能力分组ID
    private String   statusValue;    //当前值。

    private boolean  online;
    private Date     upTime;
    private Date     createTime;


}
