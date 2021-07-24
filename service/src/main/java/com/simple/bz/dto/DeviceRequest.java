package com.simple.bz.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value="设备对象",description="设备请求参数或者返回参数")
public class DeviceRequest {

    private Long     id;
    private Long     gatewayId;     //绑定网关设备ID
    private Long     roomId;        //房间或区域ID
    private String   topic;         //主题
    private String   deviceId;      //设备类型识别
    private String   description;   //设备描述名,终端用户不可以编辑
    private String   ieee;          //长地址
    private String   nickName;      //别名,终端用户可以编辑




}
