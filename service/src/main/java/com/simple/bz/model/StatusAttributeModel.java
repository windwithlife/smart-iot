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
@Table(name="tbl_status_attribute")
public class StatusAttributeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long     id;
    private Long     deviceId;          //设备ID
    private String   ieee;               //长地址
    private String   clusterAttribute;  //功能cluster的属性名；
    private String   value;              //功能cluster的属性值
    private String   endpoint;           //服务端口；

    private Date     updateTime;
    private Date     createTime;







}
