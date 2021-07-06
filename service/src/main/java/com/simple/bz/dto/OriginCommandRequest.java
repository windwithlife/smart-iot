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
public class OriginCommandRequest {

    private Long     gatewayId;     //绑定网关设备ID
    private String   gatewayTopic;  //绑定网关设备ID
    private String   command;       //命令
    private String   payload;       //命令Payload




}
