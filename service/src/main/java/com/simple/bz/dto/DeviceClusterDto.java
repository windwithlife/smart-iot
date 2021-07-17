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
public class DeviceClusterDto {
    private Long     deviceId;      //设备ID
    private String   cluster;       //功能cluster；
    private String   inOrOut;       //功能cluster类型--可操作或状态；
    private String   endpoint;      //服务端口；
    private List<ClusterAttributeDto> clusterAttributes;
}
