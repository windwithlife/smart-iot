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

public class DeviceWifi {
    private String   AP;
    private String   SSId;
    private String   BSSId;
    private String   Signal;
    private int      Channel;
    private int      RSSI;
    private int      LinkCount;
    private String   Downtime;

}
