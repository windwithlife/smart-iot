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

    private Long id;
    private Long     gatewayId; //绑定网关设备ID
    private Long     roomId;    //房间或区域ID
    private String   ModelId;      //型号
    private String   Manufacturer; //制造厂商
    private String   Device;       //短地址
    private String   address;      //长地址
    private String   Endpoints;       //终端组
    private boolean  active;

}
