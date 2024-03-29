package com.simple.bz.controller;

import com.simple.bz.dao.ContextQuery;
import com.simple.bz.dao.DeviceRepository;
import com.simple.bz.dto.*;
import com.simple.bz.dto.DeviceDto;
import com.simple.bz.service.DeviceService;
import com.simple.bz.service.DeviceStatusService;
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
    //private final ContextQuery contextQuery;
    //private final DeviceRepository deviceRepository;

    @ApiOperation(value="测试调用")
    @GetMapping(path = "/testJPA")
    public SimpleResponse<DeviceDto>  queryDevicesByGatewayId (@RequestParam("id") Long deviceId){
        //deviceRepository.deleteByDeviceId(deviceId);
        //this.contextQuery.executeQuery("delete from test where id=" + String.valueOf(deviceId));
        SimpleResponse<DeviceDto> result = new SimpleResponse<DeviceDto>();
        return result.success(DeviceDto.builder().id(deviceId).build());
    }


    @ApiOperation(value="根据ID获取设备信息")
    @PostMapping(path = "/findById")
    public SimpleResponse<DeviceDto> findById (@RequestBody SimpleRequest<IDRequest> request){
        Long deviceId = request.getParams().getId();
        System.out.println("deviceId:" + deviceId);
        DeviceDto  dto = service.findById(deviceId);
        SimpleResponse<DeviceDto> result = new SimpleResponse<DeviceDto>();
        return result.success(dto);
    }

    @ApiOperation(value="获取所有设备信息")
    @GetMapping(path = "/findAll")
    public SimpleResponse<DevicesDto> findAll(){

        List<DeviceDto> dtos = service.findAll();
        DevicesDto allDevices = DevicesDto.builder().deviceList(dtos).build();
        SimpleResponse<DevicesDto> result = new SimpleResponse<DevicesDto>();
        return result.success(allDevices);
    }

    @ApiOperation(value="获取所有设备信息")
    @GetMapping(path = "/findDevicesByParams")
    public SimpleResponse<DevicesDto> findDevicesByParams(@RequestBody DeviceParams params){

        List<DeviceDto>  dtos = service.findAll();
        DevicesDto allDevices = DevicesDto.builder().deviceList(dtos).build();
        SimpleResponse<DevicesDto> result = new SimpleResponse<DevicesDto>();
        return result.success(allDevices);
    }
    @ApiOperation(value="根据ID获取设备信息")
    @PostMapping(path = "/findDevicesByRoomId")
    public SimpleResponse<List<DeviceDto>> findByRoomId (@RequestBody SimpleRequest<IDRequest> request){
        Long roomId = request.getParams().getId();
        System.out.println("roomId:" + roomId);
        List<DeviceDto> devices = service.queryByRoomId(roomId);
        SimpleResponse<List<DeviceDto>> result = new SimpleResponse<List<DeviceDto>>();
        return result.success(devices);
    }

    @ApiOperation(value="根据设备ID获取设备所支持的所有功能")
    @PostMapping(path = "/queryDeviceSupportAttributeById")
    public SimpleResponse<DeviceClusterAttrDto> queryDeviceSupportAttributeById (@RequestBody SimpleRequest<IDRequest> request){
        Long deviceId = request.getParams().getId();
        System.out.println("deviceId:" + deviceId);
        DeviceClusterAttrDto dto = service.querySupportClusterAttribute(deviceId);
        SimpleResponse<DeviceClusterAttrDto> result = new SimpleResponse<DeviceClusterAttrDto>();
        return result.success(dto);
    }
    @ApiOperation(value="根据设备ID获取设备状态")
    @PostMapping(path = "/findStatusByDeviceId")
    public SimpleResponse<DeviceStatusDto> findStatusByDeviceId (@RequestBody SimpleRequest<IDRequest> request){
        Long deviceId = request.getParams().getId();
        System.out.println("ID:" + deviceId);
        DeviceStatusDto dto = serviceStatus.queryStatusByDeviceId(deviceId);
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
