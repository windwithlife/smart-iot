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
@Table(name="tbl_device_cluster")
public class DeviceClusterModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long     id;
    private Long     deviceId;      //设备ID
    private String   ieee;          //长地址
    private String   cluster;       //功能cluster；
    private String   inOrOut;       //功能cluster类型--可操作或状态；
    private String   endpoint;      //服务端口；


    private Date     updateTime;
    private Date     createTime;







}
