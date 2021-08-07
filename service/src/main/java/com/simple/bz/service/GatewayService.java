package com.simple.bz.service;

import com.simple.bz.dao.ContextQuery;
import com.simple.bz.dao.GatewayRepository;
import com.simple.bz.dao.GatewayStatusRepository;
import com.simple.bz.dto.GatewayDto;
import com.simple.bz.dto.GatewayStatusDto;
import com.simple.bz.model.GatewayModel;
import com.simple.bz.model.GatewayStatusModel;
import com.simple.common.error.ServiceException;
import com.simple.common.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GatewayService {
    private final ModelMapper modelMapper;
    
    private final GatewayRepository dao;
    private final GatewayStatusRepository statusDao;
    private final ContextQuery contextQuery;

    public GatewayModel convertToModel(GatewayDto dto){
        return this.modelMapper.map(dto, GatewayModel.class);
    }
    public List<GatewayModel> convertToModels(List<GatewayDto> dtos){
        List<GatewayModel> resultList = new ArrayList<GatewayModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public GatewayDto convertToDto(GatewayModel model){
        return this.modelMapper.map(model, GatewayDto.class);
    }

    public List<GatewayDto> convertToDtos(List<GatewayModel> models){
        List<GatewayDto> resultList = new ArrayList<GatewayDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<GatewayDto> findAll(){

        List<GatewayModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }


    public GatewayDto findById(Long id){
        GatewayModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }


    public List<GatewayDto> queryAll(){
        List<GatewayDto> list = contextQuery.findList("select * from tbl_gateway", GatewayDto.class);
        return  list;
    }
    public List<GatewayDto> queryPage(int pageIndex, int pageSize){
        List<GatewayDto> listPage = contextQuery.findPage("select * from tbl_house", pageIndex,pageSize, GatewayDto.class);
        return  listPage;
    }
    public GatewayDto save(GatewayDto item){
        GatewayModel model = this.convertToModel(item);
        GatewayModel oldModel = dao.findOneByLocationTopic(model.getLocationTopic());
        if(null == oldModel){
            GatewayModel newModel = this.dao.save(model);
            return this.convertToDto(newModel);
        }else{
            throw new ServiceException("此网关已添加");
        }
    }


    public List<GatewayDto> queryByHouseId(Long houseId){
        List<GatewayDto> list = contextQuery.findList("select * from tbl_gateway where houseId=" + String.valueOf(houseId), GatewayDto.class);
        return  list;
    }
    public Long findHouseIdByGatewayName(String name){
        GatewayModel model = this.dao.findOneByName(name);
        return model.getHouseId();
    }
    public GatewayDto update(GatewayDto item){
        Long id = item.getId();
        GatewayModel model = dao.findById(id).get();
        if (null == model ){return null;}
        this.modelMapper.map(item, model);
        System.out.println("Iot device model info ");
        System.out.println(model.toString());
        this.dao.save(model);
        return item;
    }
    public GatewayStatusDto updateStatus(Long gatewayId, GatewayStatusDto item){
        Long id = item.getId();
        GatewayStatusModel model = this.statusDao.findById(gatewayId).orElse(null);
        if (null == model ){return null;}
        this.modelMapper.map(item, model);
        model.setUpTime(DateUtil.getDateToday());
        System.out.println("Iot device model info ");
        System.out.println(model.toString());
        this.statusDao.save(model);
        return item;
    }
    public boolean updateOnline(Long gatewayId, boolean isOnline){
        GatewayStatusModel model = this.statusDao.findById(gatewayId).orElse(null);
        if (null == model ){return false;}
        model.setActive(isOnline);
        model.setUpTime(DateUtil.getDateToday());
        this.statusDao.save(model);
        return  true;
    }

    public void remove(Long id){
        this.dao.deleteById(id);
    }

}