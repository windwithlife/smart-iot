package com.simple.bz.controller;

import com.simple.bz.dto.*;
import com.simple.bz.dto.DeviceDto;
import com.simple.bz.service.DeviceService;
import com.simple.bz.service.DeviceStatusService;
import com.simple.bz.service.GatewayDeviceService;
import com.simple.bz.service.GatewayStatusService;
import com.simple.common.api.SimpleRequest;
import com.simple.common.api.SimpleResponse;
import com.simple.common.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/device")
@Api(tags = "处理设备控制器的SOA服务集")
public class DeviceController extends BaseController {

    private final DeviceService service;
    private final DeviceStatusService serviceStatus;

    @ApiOperation(value="根据网关ID获取所属所有设备信息")
    @PostMapping(path = "/queryDevicesByGatewayId")
    public SimpleResponse<DevicesDto>  queryDevicesByGateway (@RequestBody SimpleRequest<Long> request){
        Long gatewayId = request.getParams();
        List<DeviceDto> list = service.queryByGatewayId(gatewayId);
        SimpleResponse<DevicesDto> result = new SimpleResponse<DevicesDto>();
        return result.success(DevicesDto.builder().deviceList(list).gatewayId(gatewayId).build());
    }
    @ApiOperation(value="根据ID获取设备信息")
    @PostMapping(path = "/findById")
    public SimpleResponse<DeviceDto> findById (@RequestBody SimpleRequest<Long> request){
        Long deviceId = request.getParams();
        System.out.println("deviceId:" + deviceId);
        DeviceDto  dto = service.findById(deviceId);
        SimpleResponse<DeviceDto> result = new SimpleResponse<DeviceDto>();
        return result.success(dto);
    }

    @ApiOperation(value="根据设备ID获取设备状态")
    @PostMapping(path = "/findStatusByDeviceId")
    public SimpleResponse<DeviceStatusDto> findStatusByDeviceId (@RequestBody SimpleRequest<Long> request){
        Long deviceId = request.getParams();
        System.out.println("ID:" + deviceId);
        DeviceStatusDto dto = serviceStatus.queryByDeviceId(deviceId);
        SimpleResponse<DeviceStatusDto> result = new SimpleResponse<DeviceStatusDto>();
        return result.success(dto);
    }




    @ApiOperation(value="修改设备信息")
    @PostMapping(path = "/update")
    public SimpleResponse<DeviceDto> updateSave(@RequestBody SimpleRequest<DeviceDto> req) {
        DeviceDto dto = req.getParams();
        System.out.println(dto.toString());
        service.update(dto);
        SimpleResponse<DeviceDto> result = new SimpleResponse<DeviceDto>();
        return result.success(dto);

    }

    @ApiOperation(value="根扰ID删除一个设备")
    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public SimpleResponse<Long> removeById(@RequestBody SimpleRequest<Long> req) {
        service.remove(req.getParams());
        SimpleResponse<Long> result = new SimpleResponse<Long>();
        return result.success(req.getParams());
    }




}
