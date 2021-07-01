package com.simple.bz.controller;

import com.simple.bz.dto.RoomDto;
import com.simple.bz.dto.RoomsDto;

import com.simple.bz.service.RoomService;
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
@RequestMapping("/room")
@Api(tags = "处理房间或区域相关的SOA集")
public class RoomController extends BaseController {

    private final RoomService service;

    @ApiOperation(value="当前房间或区域集合（用于开发测试)")
    @PostMapping(path = "/queryAll")
    public SimpleResponse<RoomsDto> queryAll (){

        List<RoomDto> rooms = service.queryAll();
        SimpleResponse<RoomsDto> result = new SimpleResponse<RoomsDto>();

        return result.success(RoomsDto.builder().roomList(rooms).houseId(-1L).build());
    }
    @ApiOperation(value="根据房子ID获取所属所有房间信息")
    @PostMapping(path = "/queryRoomsByHouseId")
    public SimpleResponse<RoomsDto>  queryRoomsByHouse (@RequestBody SimpleRequest<Long> request){
        Long houseId = request.getParams();
        List<RoomDto> roomList = service.queryByHouseId(houseId);
        SimpleResponse<RoomsDto> result = new SimpleResponse<RoomsDto>();
        return result.success(RoomsDto.builder().roomList(roomList).houseId(houseId).build());
    }
    @ApiOperation(value="根据ID获取房间信息")
    @PostMapping(path = "/findById")
    public SimpleResponse<RoomDto> findById (@RequestBody SimpleRequest<Long> request){
        Long roomId = request.getParams();
        System.out.println("roomId:" + roomId);
        RoomDto  dto = service.findById(roomId);
        SimpleResponse<RoomDto> result = new SimpleResponse<RoomDto>();
        return result.success(dto);
    }



    @ApiOperation(value="新增房间")
    @PostMapping(path = "/addRoom")
    public SimpleResponse<RoomDto> addNew (@RequestBody SimpleRequest<RoomDto> request){
        System.out.println(request.toString());
        RoomDto dto = request.getParams();
        service.save(dto);
        SimpleResponse<RoomDto> result = new SimpleResponse<RoomDto>();
        return result.success(dto);
    }


    @ApiOperation(value="修改房间信息")
    @PostMapping(path = "/update")
    public SimpleResponse<RoomDto> updateSave(@RequestBody SimpleRequest<RoomDto> req) {
        RoomDto dto = req.getParams();
        System.out.println(dto.toString());
        service.update(dto);
        SimpleResponse<RoomDto> result = new SimpleResponse<RoomDto>();
        return result.success(dto);

    }

    @ApiOperation(value="删除房间")
    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public SimpleResponse<Long> removeById(@RequestBody SimpleRequest<Long> req) {
        service.remove(req.getParams());
        SimpleResponse<Long> result = new SimpleResponse<Long>();
        return result.success(req.getParams());
    }




}
