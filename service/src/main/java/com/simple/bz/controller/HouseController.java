package com.simple.bz.controller;

import com.simple.bz.dto.HouseDto;

import com.simple.bz.dto.IDRequest;
import com.simple.bz.dto.IDResponse;
import com.simple.bz.dto.UserHousesDto;
import com.simple.bz.service.HouseService;

import com.simple.common.api.ResultCode;
import com.simple.common.api.SimpleRequest;
import com.simple.common.api.SimpleResponse;
import com.simple.common.auth.LoginUser;
import com.simple.common.auth.SessionUser;
import com.simple.common.auth.Sessions;
import com.simple.common.controller.BaseController;
import com.simple.common.error.ServiceException;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/house")
@Api(tags = "处理房子相关的SOA集")
public class HouseController extends BaseController {

    private final HouseService service;
    @ApiOperation(value="当前房子集合（用于测试）")
    @PostMapping(path = "/queryAll")
    public SimpleResponse<UserHousesDto> queryAll (){
        List<HouseDto> houseList = service.queryAll();
        SimpleResponse<UserHousesDto> result = new SimpleResponse<UserHousesDto>();
        return result.success(UserHousesDto.builder().houseList(houseList).userId("0").build());
    }

    @ApiOperation(value="根据用户ID获取所属所有住房信息",notes = "用于处理房子信息处理")
    @PostMapping(path = "/queryHouseByUser")
    public SimpleResponse<UserHousesDto>  queryUserHouses (HttpServletRequest req){
        String token = Sessions.getAuthToken(req);
        if (StringUtils.isBlank(token)){
            throw new ServiceException("请先登录");
        }
        System.out.println("current token--->" + token);
        String userId = Sessions.getSessionUserInfo(token).getUserId();
        System.out.println("Current UserId ===>" + userId);
        //String userId = request.getParams();
        List<HouseDto> houseList = service.queryByUser(userId);

        //List<HouseDto> houseList = service.queryAll();
        SimpleResponse<UserHousesDto> result = new SimpleResponse<UserHousesDto>();
        //return result.success(UserHousesDto.builder().houseList(houseList).userId(userId).build());
        return result.success(UserHousesDto.builder().houseList(houseList).build());
    }

    @ApiOperation(value="根据ID获取房子信息",notes = "用于处理房子信息处理")
    @PostMapping(path = "/findById")
    public SimpleResponse<HouseDto> findById (@RequestBody SimpleRequest<IDRequest> request){
        Long houseId = request.getParams().getId();
        System.out.println("applicationId:" + houseId);
        HouseDto  dto = service.findById(houseId);
        SimpleResponse<HouseDto> result = new SimpleResponse<HouseDto>();
        return result.success(dto);
    }

    @ApiOperation(value="增加房子信息",notes = "")
    @PostMapping(path = "/addHouseOwner")
    public SimpleResponse<IDResponse> addHouseOwner (@RequestBody SimpleRequest<IDRequest> request,@LoginUser SessionUser sessionUser){
        Long houseId = request.getParams().getId();
        if (null == houseId || houseId <= 0){
            throw new ServiceException(ResultCode.PARAM_VALID_ERROR);
        }
        if (!sessionUser.isLoginUser()){
            throw new ServiceException(ResultCode.UN_AUTHORIZED);
        }
        System.out.println("houseId:" + houseId);
        boolean success = service.addHouseOwner(houseId, sessionUser.getUserId());
        if(!success){
            throw new ServiceException("Current User already is owner");
        }
        SimpleResponse<IDResponse> result = new SimpleResponse<IDResponse>();
        return result.success(IDResponse.builder().id(houseId).build());
    }



    @ApiOperation(value="新增房子",notes = "用于处理房子信息处理")
    @PostMapping(path = "/addHouse")
    public SimpleResponse<HouseDto> addNewHouse (@RequestBody SimpleRequest<HouseDto> request, HttpServletRequest req, @LoginUser SessionUser sessionUser){
        String token = Sessions.getAuthToken(req);
        String userId = Sessions.getSessionUserInfo(token).getUserId();
        System.out.println(request.toString());
        System.out.println("current user-->"+ sessionUser.toString());
        HouseDto dto = request.getParams();
        dto.setUserId(userId);
        //HouseDto data = service.save(dto);
        HouseDto data = service.addHouse(dto);
        SimpleResponse<HouseDto> result = new SimpleResponse<HouseDto>();
        return result.success(data);

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
    public SimpleResponse<IDResponse> removeById(@RequestBody SimpleRequest<IDRequest> req) {
        service.remove(req.getParams().getId());
        SimpleResponse<IDResponse> result = new SimpleResponse<IDResponse>();

        return result.success(IDResponse.builder().id(req.getParams().getId()).build());
    }




}
