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
    private Long     gatewayDeviceId; //绑定网关设备ID
    private String   ModelId;      //型号
    private String   Manufacturer; //制造厂商
    private String   Device;       //短地址
    private String   IEEEAddr;      //长地址
    private String   Endpoints;       //终端组
    private boolean  active;

}
