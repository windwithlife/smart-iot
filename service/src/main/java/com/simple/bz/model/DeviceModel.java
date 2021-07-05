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
    private Long     gatewayId; //绑定网关设备ID
    private Long     roomId;    //房间或区域ID
    private String   code;         //识别码
    private String   modelId;      //型号
    private String   manufacturer; //制造厂商
    private String   shortAddress; //短地址
    private String   address;      //长地址
    private String   nickName;     //别名

    private String   batteryVoltage; //电压；

    private String   endpoint;       //终端组
    private String   supportAbility; //支持能力

    private boolean  active;
    private Date     upTime;
    private Date     createTime;







}
