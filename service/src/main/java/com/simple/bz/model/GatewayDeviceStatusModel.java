package com.simple.bz.model;

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
@Table(name="GatewayDeviceStatus")
public class GatewayDeviceStatusModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long     gatewayDeviceId;
    private String   nickName;
    private int      wifiLinkCount;
    private int      wifiChannel;
    private String   wifiAp;
    private boolean  active;
    private Date     upTime;
    private Date     createDate;
    private String   locationTopic;
    private int      sleepSeconds;
    private String   sleepMode;
    private int      heap;
    private int      loadAvg;
    private int      mqttCount;


}
