package com.simple.bz.controller;

import com.simple.bz.dto.HouseDto;

import com.simple.bz.dto.UserHousesDto;
import com.simple.bz.service.HouseService;

import com.simple.common.api.SimpleRequest;
import com.simple.common.api.SimpleResponse;
import com.simple.common.controller.BaseController;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/house")
@Api(tags = "处理房子相关的SOA集")
public class HouseController extends BaseController {

    private final HouseService service;

    @ApiOperation(value="当前房子集合（用于测试）",notes = "用于测试接口")
    @PostMapping(path = "/queryAll")
    public SimpleResponse<UserHousesDto> queryAll (){

        List<HouseDto> houseList = service.queryAll();

        SimpleResponse<UserHousesDto> result = new SimpleResponse<UserHousesDto>();

        return result.success(UserHousesDto.builder().houseList(houseList).userId("0").build());
    }
    @ApiOperation(value="根据用户ID获取所属所有住房信息",notes = "用于处理房子信息处理")
    @PostMapping(path = "/queryHouseByUser")
    public SimpleResponse<UserHousesDto>  queryUserHouses (@RequestBody SimpleRequest<String> request){
        String userId = request.getParams();
        List<HouseDto> houseList = service.queryByUser(userId);
        SimpleResponse<UserHousesDto> result = new SimpleResponse<UserHousesDto>();
        return result.success(UserHousesDto.builder().houseList(houseList).userId(userId).build());
    }
    @ApiOperation(value="根据ID获取房子信息",notes = "用于处理房子信息处理")
    @PostMapping(path = "/findById")
    public SimpleResponse<HouseDto> findById (@RequestBody SimpleRequest<Long> request){
        Long houseId = request.getParams();
        System.out.println("applicationId:" + houseId);
        HouseDto  dto = service.findById(houseId);
        SimpleResponse<HouseDto> result = new SimpleResponse<HouseDto>();
        return result.success(dto);
    }


//    @ApiResponses({
//            @ApiResponse(code=400,message="请求参数没填好"),
//            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
//    })

    @ApiOperation(value="新增房子",notes = "用于处理房子信息处理")
    @PostMapping(path = "/addHouse")
    public SimpleResponse<HouseDto> addNew (@RequestBody SimpleRequest<HouseDto> request){
        System.out.println(request.toString());
        HouseDto dto = request.getParams();
        service.save(dto);
        SimpleResponse<HouseDto> result = new SimpleResponse<HouseDto>();
        return result.success(dto);
    }


    @ApiOperation(value="修改房子",notes = "用于处理房子信息处理")
    @PostMapping(path = "/update")
    public SimpleResponse<HouseDto> updateSave(@RequestBody SimpleRequest<HouseDto> req) {
        HouseDto dto = req.getParams();
        System.out.println(dto.toString());
        service.update(dto);
        SimpleResponse<HouseDto> result = new SimpleResponse<HouseDto>();
        return result.success(dto);

    }

    @ApiOperation(value="删除房子",notes = "用于处理房子信息处理")
    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public SimpleResponse<Long> removeById(@RequestBody SimpleRequest<Long> req) {
        service.remove(req.getParams());
        SimpleResponse<Long> result = new SimpleResponse<Long>();
        return result.success(req.getParams());
    }




}
