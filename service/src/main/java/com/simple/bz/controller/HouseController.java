package com.simple.bz.controller;

import com.simple.bz.dto.HouseDto;
import com.simple.bz.dto.RequestTemplate;
import com.simple.bz.dto.ResponseTemplate;
import com.simple.bz.dto.UserHousesDto;
import com.simple.bz.service.HouseService;
import com.simple.common.api.GenericRequest;
import com.simple.common.api.GenericResponse;
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
@Api(tags = "处理房子相关的SOA")
public class HouseController extends BaseController {

    private final HouseService service;


    @GetMapping(path = "/findAll")
    GenericResponse findAll (){

        List<HouseDto> dtoLst = service.findAll();
        GenericResponse result = GenericResponse.build().addKey$Value("list", dtoLst);

        return result;
    }

    @ApiOperation(value="当前房子集合（用于测试）",notes = "用于测试接口")
    @GetMapping(path = "/queryAll")
    public SimpleResponse<UserHousesDto> queryAll (){

        List<HouseDto> houseList = service.queryAll();

        SimpleResponse<UserHousesDto> result = new SimpleResponse<UserHousesDto>();

        return result.success(UserHousesDto.builder().houseList(houseList).userId("0").build());
    }
    @ApiOperation(value="根据用户ID获取所属所有住房信息",notes = "用于处理房子信息处理")
    @GetMapping(path = "/queryHouseByUser")
    public SimpleResponse<UserHousesDto>  queryUserHouses (@RequestParam("userId") String userId){

        List<HouseDto> houseList = service.queryAll();

        SimpleResponse<UserHousesDto> result = new SimpleResponse<UserHousesDto>();

        return result.success(UserHousesDto.builder().houseList(houseList).userId(userId).build());
    }
    @ApiOperation(value="根据ID获取房子信息",notes = "用于处理房子信息处理")
    @GetMapping(path = "/findById")
    public SimpleResponse<HouseDto> findById (@RequestParam("id") Long houseId){

        System.out.println("applicationId:" + houseId);
        HouseDto  dto = service.findById(houseId);
        SimpleResponse<HouseDto> result = new SimpleResponse<HouseDto>();
        return result.success(dto);
    }

//    @ApiOperation(value="新增或修改",notes = "用于处理房子信息处理")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "name", value = "房子名称",required=true,defaultValue="我的房子", dataType = "string",paramType = "body"),
//            @ApiImplicitParam(name = "address", value = "房子地址",required=true,defaultValue="无", dataType = "string",paramType = "body")
//    })
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
    @PostMapping(path = "/update/{id}")
    public SimpleResponse<HouseDto> updateSave(@RequestBody SimpleRequest<HouseDto> req, @PathVariable Long id) {
        HouseDto dto = req.getParams();
        dto.setId(id);
        System.out.println("projectInfo:" + String.valueOf(id));
        System.out.println(dto.toString());
        service.update(dto);
        SimpleResponse<HouseDto> result = new SimpleResponse<HouseDto>();
        return result.success(dto);

    }

    @ApiOperation(value="删除房子",notes = "用于处理房子信息处理")
    @ResponseBody
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public Long removeById(@PathVariable Long id) {
        service.remove(id);
        return id;
    }




}
