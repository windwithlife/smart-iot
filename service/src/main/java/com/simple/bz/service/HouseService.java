    package com.simple.bz.service;


import com.simple.bz.dao.*;
import com.simple.bz.dto.HouseDto;

import com.simple.bz.model.*;
import com.simple.common.auth.AuthModel;
import com.simple.common.auth.Sessions;
import com.simple.common.error.ServiceException;
import com.simple.common.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class HouseService {
    private final ModelMapper modelMapper;
    private final HouseRepository dao;
    private final UserHouseRepository userHouseDao;
    private final RoomRepository roomDao;
    private final GatewayRepository gatewayDao;
    private final ContextQuery contextQuery;

    public HouseModel convertToModel(HouseDto dto){
        return this.modelMapper.map(dto, HouseModel.class);
    }
    public List<HouseModel> convertToModels(List<HouseDto> dtos){
        List<HouseModel> resultList = new ArrayList<HouseModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public HouseDto convertToDto(HouseModel model){
        return this.modelMapper.map(model, HouseDto.class);
    }

    public List<HouseDto> convertToDtos(List<HouseModel> models){
        List<HouseDto> resultList = new ArrayList<HouseDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<HouseDto> findAll(){
        
        List<HouseModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }


    public HouseDto findById(Long id){
        HouseModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }
    public boolean addHouseOwner(Long houseId, String userId){
        List<UserHouseModel>  list = userHouseDao.findByUserIdAndHouseId(userId,houseId);
        if (list.size() > 0){
            return false;
        }
        UserHouseModel model = userHouseDao.save(UserHouseModel.builder().houseId(houseId).userId(userId).build());
        return true;
    }

    public HouseDto save(HouseDto item){
        HouseModel model = this.convertToModel(item);
        HouseModel newModel = this.dao.save(model);
        return this.convertToDto(newModel);
    }
    public HouseDto addHouse(HouseDto item){
        HouseModel model = this.convertToModel(item);
        HouseModel newModel = this.dao.save(model);
        UserHouseModel userHouse = UserHouseModel.builder().userId(newModel.getUserId()).houseId(newModel.getId()).build();
        userHouseDao.save(userHouse);
        return this.convertToDto(newModel);
    }

    public HouseDto update(HouseDto item){
        Long id = item.getId();
        HouseModel model = dao.findById(id).get();
        if (null == model ){return null;}
        this.modelMapper.map(item, model);
        System.out.println("Iot device model info ");
        System.out.println(model.toString());
        this.dao.save(model);
        return item;
    }
    public List<HouseDto> queryAll(){
        List<HouseDto> list = contextQuery.findList("select * from tbl_house", HouseDto.class);
        return  list;
    }
    public List<HouseUsersDto> queryUsersByGatewayId(Long gatewayId){
        GatewayModel gateway =  gatewayDao.findById(gatewayId).orElse(null);
        if (null == gateway){
            throw new ServiceException("根据此网关名称，找不到登记的网关设备");
        }
        List<UserHouseModel> list = userHouseDao.findByHouseId(gateway.getHouseId());
        List<HouseUsersDto> retList = new ArrayList<HouseUsersDto>();
        list.forEach(item->{
            AuthModel auth = Sessions.getSessionUserStatusByUserId(item.getUserId());
            if(null != auth){
                String token = Sessions.getSessionUserStatusByUserId(item.getUserId()).getToken();
                HouseUsersDto  houseUsers = HouseUsersDto.builder().userId(item.getUserId()).houseId(item.getHouseId()).token(token).build();
                retList.add(houseUsers);
                System.out.println("find the target user of the gateway and house -->" + houseUsers.getUserId());
            }

        });

        return retList;
    }

    public List<HouseDto> queryByUser(String userId){
        List<HouseDto> list = contextQuery.findList("select * from tbl_user_house where userId='" + userId + "'", HouseDto.class);
        return  list;
    }

    public List<HouseDto> createDefaultHouse(String userId){
        try {
            HouseModel defaultHouse = HouseModel.builder().userId(userId).name(HouseModel.DEFAUlT_HOUSE_NAME)
                    .address("上海市").createdDate(DateUtil.getDateToday()).active(false).build();
            HouseModel newModel = this.dao.save(defaultHouse);
            UserHouseModel userHouse = UserHouseModel.builder().userId(newModel.getUserId()).houseId(newModel.getId()).build();
            userHouseDao.save(userHouse);
            RoomModel bedRoom = RoomModel.builder().name("卧室").houseId(newModel.getId()).build();
            RoomModel bathRoom = RoomModel.builder().name("卫生间").houseId(newModel.getId()).build();
            roomDao.save(bedRoom);
            roomDao.save(bathRoom);
            HouseDto newHouseDto = this.convertToDto(newModel);
            List<HouseDto> result = new ArrayList<HouseDto>();
            result.add(newHouseDto);
            return result;

        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("无法正确获取房子相关信息");
        }
    }


    public List<HouseDto> queryPage(int pageIndex, int pageSize){
        List<HouseDto> listPage = contextQuery.findPage("select * from tbl_house", pageIndex,pageSize, HouseDto.class);
        return  listPage;
    }
    public void remove(Long id){
        this.dao.deleteById(id);
    }

}