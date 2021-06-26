package com.simple.bz.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value="设备列表")
public class DevicesDto {
    private Long   gatewayId;
    private List<DeviceDto> deviceList;

}
