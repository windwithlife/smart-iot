package com.simple.bz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceClusterAttrDto {
    private Long deviceId;      //设备ID
    private String ieee;          //长地址
    private List<DeviceClusterDto> statusCluster;
    private List<DeviceClusterDto> commandCluster;
}

