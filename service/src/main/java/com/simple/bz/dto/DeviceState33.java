package com.simple.bz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceState33 {

    private String   Status;           //状态
    private String   DeviceId;         //设备类型编号
    private String   DeviceVersion;    //设备版本编号
    private String   Device;         //ShortAddr
    private String   Endpoint;         //服务端口
    private String   ProfileId;      //文档编号
    private List<String> InClusters;  //输入功能支持cluster
    private List<String> OutClusters;  //输出功能支持cluster
}
