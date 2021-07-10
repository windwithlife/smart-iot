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
@Table(name="tbl_device")
public class DeviceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long     id;
    private Long     gatewayId;     //绑定网关设备ID
    private Long     roomId;        //房间或区域ID
    private String   topic;         //主题
    private String   deviceId;      //设备类型识别
    private String   deviceVersion; //设备类型识别
    private String   profileId;     //配置ID
    private String   modelId;       //型号
    private String   manufacturer;  //制造厂商
    private String   shortAddress;  //短地址
    private String   ieee;          //长地址
    private String   nickName;      //别名

    private String   endpoint;      //服务端口；
    private String   powerSource;       //电源类型
    private String   security;          //安全性
    private String   receiveWhenIdle;   //

    private int      status;
    private Date     upTime;
    private Date     createTime;







}
