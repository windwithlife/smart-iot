package com.simple.bz.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value="设备对象",description="设备请求参数或者返回参数")
public class NewDeviceResponse {
    private String   Status;             //状态
    private String   IEEEAddr;           //长地址
    private String   ShortAddr;          //短地址
    private String   PowerSource;        //是否外接电源还是电池供电
    private String   ReceiveWhenIdle;    //终端组
    private String   Security;


}
