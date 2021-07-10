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
public class DeviceState30 {

    private String   Status;           //状态
    private String   IEEEAddr;         //IEEEAddr
    private String   ShortAddr;        //ShortAddr
    private String   PowerSource;      //电源类型
    private String   ReceiveWhenIdle;  //短地址
    private String   Security;         //长地址
}
