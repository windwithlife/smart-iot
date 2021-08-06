package com.simple.bz.dto;

import io.swagger.annotations.ApiModel;
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
@ApiModel(value="设备对象",description="设备请求参数或者返回参数")
public class DeviceDto {

    private Long     id;
    private Long     gatewayId;     //绑定网关设备ID
    private Long     roomId;        //房间或区域ID
    private String   topic;         //主题
    private String   deviceId;      //设备类型识别
    private String   description;   //设备描述名
    private String   deviceDefine;  //设备类型识别
    private String   profileId;     //配置ID
    private String   modelId;       //型号
    private String   manufacturer;  //制造厂商
    private String   shortAddress;  //短地址
    private String   ieee;          //长地址
    private String   nickName;      //别名,终端用户可以编辑

    private DeviceClusterAttrDto clusterAttributes; //设备的操作属性

    private String   endpoint;      //服务端口；
    private String   powerSource;       //电源类型
    private String   security;          //安全性
    private String   receiveWhenIdle;   //

    private int      status;
    private Date     upTime;
    private Date     createTime;


}
