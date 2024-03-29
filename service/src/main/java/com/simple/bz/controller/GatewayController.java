package com.simple.bz.controller;

import com.simple.bz.dto.*;
import com.simple.bz.service.*;
import com.simple.common.api.BaseResponse;
import com.simple.common.api.SimpleRequest;
import com.simple.common.api.SimpleResponse;
import com.simple.common.controller.BaseController;
import com.simple.common.error.ServiceException;
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

    private final GatewayService service;
    private final DeviceService deviceService;
    private final GatewayStatusService statusService;
    private final IOTService iotService;

    @ApiOperation(value="当前网关组（用于测试")
    @PostMapping(path = "/queryAll")
    public SimpleResponse<HouseGatewaysDto> queryAll (){

        List<GatewayDto> list = service.queryAll();
        SimpleResponse<HouseGatewaysDto> result = new SimpleResponse<HouseGatewaysDto>();

        return result.success(HouseGatewaysDto.builder().gatewayList(list).houseId(-1L).build());
    }
    @ApiOperation(value="透传通用原始指令")
    @PostMapping(path = "/originCommand")
    public BaseResponse originCommand (@RequestBody SimpleRequest<OriginCommandRequest> request){
        OriginCommandRequest dto = request.getParams();
        iotService.sendMQTTCommand(dto.getGatewayTopic(),dto.getCommand(),dto.getPayload());
        return BaseResponse.buildSuccess();
    }
    @ApiOperation(value="发送控制命令进行控制设备")
    @PostMapping(path = "/sendCommand")
    public BaseResponse sendCommand (@RequestBody SimpleRequest<OriginCommandRequest> request){
        OriginCommandRequest dto = request.getParams();
        iotService.sendMQTTCommand(dto.getGatewayTopic(),"Send",dto.getPayload());
        return BaseResponse.buildSuccess();
    }
    @ApiOperation(value="开始添加设备（打开网关允许配对模式）")
    @PostMapping(path = "/openDevicePairing")
    public BaseResponse  openDevicePairing (@RequestBody SimpleRequest<GatewayCommandRequest> request){

        this.iotService.openDevicePairing(request.getParams().getGatewayId());

        return BaseResponse.buildSuccess();
    }
    @ApiOperation(value="新增一个简单设备（其实是与当所房间进行绑定")
    @PostMapping(path = "/addDevice")
    public SimpleResponse<DeviceDto> addNewDevice (@RequestBody SimpleRequest<DeviceNewRequest> request){
        System.out.println(request.toString());
        DeviceNewRequest dto = request.getParams();
        if(!dto.verify()){throw new ServiceException("Params is invalid!");}

        boolean bindResult = deviceService.bindToRoom(dto);
        SimpleResponse<DeviceDto> result = new SimpleResponse<DeviceDto>();
        if (bindResult){
            return result.success(DeviceDto.builder().id(dto.getDeviceId()).roomId(dto.getRoomId()).build());
        }else{
            return result.failure();
        }
    }
    @ApiOperation(value="根据房子ID获取所属所有网关信息")
    @PostMapping(path = "/queryGatewaysByHouseId")
    public SimpleResponse<HouseGatewaysDto>  queryRoomsByHouse (@RequestBody SimpleRequest<IDRequest> request){
        Long houseId = request.getParams().getId();
        List<GatewayDto> list = service.queryByHouseId(houseId);
        SimpleResponse<HouseGatewaysDto> result = new SimpleResponse<HouseGatewaysDto>();
        return result.success(HouseGatewaysDto.builder().gatewayList(list).houseId(houseId).build());
    }
    @ApiOperation(value="根据ID获取网关信息")
    @PostMapping(path = "/findById")
    public SimpleResponse<GatewayDto> findById (@RequestBody SimpleRequest<IDRequest> request){
        IDRequest req = request.getParams();
        if(null == req){
            SimpleResponse<GatewayDto> result = new SimpleResponse<GatewayDto>();
            return result.failure("参数错误");
        }
        Long gatewayId = req.getId();
        System.out.println("gatewayIDd:" + gatewayId);
        GatewayDto dto = service.findById(gatewayId);
        SimpleResponse<GatewayDto> result = new SimpleResponse<GatewayDto>();
        return result.success(dto);
    }

    @ApiOperation(value="根据ID获取网关状态")
    @PostMapping(path = "/findGatewayStatusByGatewayId")
    public SimpleResponse<GatewayStatusDto> findStatusById (@RequestBody SimpleRequest<Long> request){
        Long gatewayId = request.getParams();
        System.out.println("gatewayIDd:" + gatewayId);
        GatewayStatusDto dto = statusService.queryStatusByGatewayId(gatewayId);
        SimpleResponse<GatewayStatusDto> result = new SimpleResponse<GatewayStatusDto>();
        return result.success(dto);
    }


    @ApiOperation(value="根据网关查询所有设备信息")
    @PostMapping(path = "/queryDevicesByGatewayId")
    public SimpleResponse<GatewayDevicesResponse> queryDevicesByGatewayId (@RequestBody SimpleRequest<IDRequest> request){
        IDRequest dto = request.getParams();
        List<DeviceDto> devices = deviceService.queryByGatewayId(dto.getId());
        GatewayDevicesResponse resp = GatewayDevicesResponse.builder().devices(devices).build();
        SimpleResponse<GatewayDevicesResponse> result = new SimpleResponse<GatewayDevicesResponse>();
        return result.success(resp);
    }

    @ApiOperation(value="新增一个网关")
    @PostMapping(path = "/addGateway")
    public SimpleResponse<GatewayDto> bindGateway (@RequestBody SimpleRequest<GatewayDto> request){
        try {
            System.out.println(request.toString());
            GatewayDto dto = request.getParams();
            dto.setLocationTopic(dto.getMacAddress());
            dto.setName(dto.getMacAddress());
            GatewayDto newGateway = service.save(dto);
            SimpleResponse<GatewayDto> result = new SimpleResponse<GatewayDto>();
            return result.success(newGateway);
        }catch (Exception e) {
            e.printStackTrace();
            SimpleResponse<GatewayDto> result = new SimpleResponse<GatewayDto>();
            return result.failure();
        }
    }



    @ApiOperation(value="修改网关信息")
    @PostMapping(path = "/update")
    public SimpleResponse<GatewayDto> updateSave(@RequestBody SimpleRequest<GatewayDto> req) {
        GatewayDto dto = req.getParams();
        System.out.println(dto.toString());
        service.update(dto);
        SimpleResponse<GatewayDto> result = new SimpleResponse<GatewayDto>();
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
