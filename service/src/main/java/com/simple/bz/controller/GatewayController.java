package com.simple.bz.controller;

import com.simple.bz.dto.GatewayCommandRequest;
import com.simple.bz.dto.GatewayDeviceDto;
import com.simple.bz.dto.GatewayDeviceStatusDto;
import com.simple.bz.dto.GatewayDevicesDto;
import com.simple.bz.service.GatewayDeviceService;
import com.simple.bz.service.GatewayStatusService;
import com.simple.bz.service.IOTService;
import com.simple.bz.service.RoomService;
import com.simple.common.api.BaseResponse;
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
@RequestMapping("/gateway")
@Api(tags = "处理网关的SOA集合")
public class GatewayController extends BaseController {

    private final GatewayDeviceService service;
    private final GatewayStatusService statusService;
    private final IOTService iotService;

    @ApiOperation(value="当前网关组（用于测试")
    @PostMapping(path = "/queryAll")
    public SimpleResponse<GatewayDevicesDto> queryAll (){

        List<GatewayDeviceDto> list = service.queryAll();
        SimpleResponse<GatewayDevicesDto> result = new SimpleResponse<GatewayDevicesDto>();

        return result.success(GatewayDevicesDto.builder().gatewayList(list).houseId(-1L).build());
    }
    @ApiOperation(value="开始添加设备（打开网关允许配对模式）")
    @PostMapping(path = "/openDevicePairing")
    public BaseResponse  openDevicePairing (@RequestBody SimpleRequest<GatewayCommandRequest> request){

        this.iotService.openDevicePairing(request.getParams().getLocationTopic());

        return BaseResponse.buildSuccess();
    }



    @ApiOperation(value="根据房子ID获取所属所有网关信息")
    @PostMapping(path = "/queryGatewaysByHouseId")
    public SimpleResponse<GatewayDevicesDto>  queryRoomsByHouse (@RequestBody SimpleRequest<Long> request){
        Long houseId = request.getParams();
        List<GatewayDeviceDto> list = service.queryByHouseId(houseId);
        SimpleResponse<GatewayDevicesDto> result = new SimpleResponse<GatewayDevicesDto>();
        return result.success(GatewayDevicesDto.builder().gatewayList(list).houseId(houseId).build());
    }
    @ApiOperation(value="根据ID获取网关信息")
    @PostMapping(path = "/findById")
    public SimpleResponse<GatewayDeviceDto> findById (@RequestBody SimpleRequest<Long> request){
        Long gatewayId = request.getParams();
        System.out.println("gatewayIDd:" + gatewayId);
        GatewayDeviceDto  dto = service.findById(gatewayId);
        SimpleResponse<GatewayDeviceDto> result = new SimpleResponse<GatewayDeviceDto>();
        return result.success(dto);
    }

    @ApiOperation(value="根据ID获取网关状态")
    @PostMapping(path = "/findGatewayStatusByGatewayId")
    public SimpleResponse<GatewayDeviceStatusDto> findStatusById (@RequestBody SimpleRequest<Long> request){
        Long gatewayId = request.getParams();
        System.out.println("gatewayIDd:" + gatewayId);
        GatewayDeviceStatusDto dto = statusService.queryStatusByGatewayId(gatewayId);
        SimpleResponse<GatewayDeviceStatusDto> result = new SimpleResponse<GatewayDeviceStatusDto>();
        return result.success(dto);
    }



    @ApiOperation(value="新增一个网关")
    @PostMapping(path = "/addGateway")
    public SimpleResponse<GatewayDeviceDto> addNew (@RequestBody SimpleRequest<GatewayDeviceDto> request){
        System.out.println(request.toString());
        GatewayDeviceDto dto = request.getParams();
        dto.setLocationTopic(dto.getMacAddress());
        dto.setName(dto.getMacAddress());
        GatewayDeviceDto newGateway = service.save(dto);
        SimpleResponse<GatewayDeviceDto> result = new SimpleResponse<GatewayDeviceDto>();
        return result.success(newGateway);
    }


    @ApiOperation(value="修改网关信息")
    @PostMapping(path = "/update")
    public SimpleResponse<GatewayDeviceDto> updateSave(@RequestBody SimpleRequest<GatewayDeviceDto> req) {
        GatewayDeviceDto dto = req.getParams();
        System.out.println(dto.toString());
        service.update(dto);
        SimpleResponse<GatewayDeviceDto> result = new SimpleResponse<GatewayDeviceDto>();
        return result.success(dto);

    }

    @ApiOperation(value="根扰ID删除一个网关")
    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public SimpleResponse<Long> removeById(@RequestBody SimpleRequest<Long> req) {
        service.remove(req.getParams());
        SimpleResponse<Long> result = new SimpleResponse<Long>();
        return result.success(req.getParams());
    }




}
