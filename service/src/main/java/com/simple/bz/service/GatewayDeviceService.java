package com.simple.bz.service;

import com.simple.bz.dao.ContextQuery;
import com.simple.bz.dao.GatewayDeviceRepository;
import com.simple.bz.dto.GatewayDto;
import com.simple.bz.model.GatewayDeviceModel;
import com.simple.common.error.ServiceException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GatewayDeviceService {
    private final ModelMapper modelMapper;
    
    private final GatewayDeviceRepository dao;
    private final ContextQuery contextQuery;

    public GatewayDeviceModel convertToModel(GatewayDto dto){
        return this.modelMapper.map(dto, GatewayDeviceModel.class);
    }
    public List<GatewayDeviceModel> convertToModels(List<GatewayDto> dtos){
        List<GatewayDeviceModel> resultList = new ArrayList<GatewayDeviceModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public GatewayDto convertToDto(GatewayDeviceModel model){
        return this.modelMapper.map(model, GatewayDto.class);
    }

    public List<GatewayDto> convertToDtos(List<GatewayDeviceModel> models){
        List<GatewayDto> resultList = new ArrayList<GatewayDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<GatewayDto> findAll(){

        List<GatewayDeviceModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }


    public GatewayDto findById(Long id){
        GatewayDeviceModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }


    public List<GatewayDto> queryAll(){
        List<GatewayDto> list = contextQuery.findList("select * from tbl_house", GatewayDto.class);
        return  list;
    }
    public List<GatewayDto> queryPage(int pageIndex, int pageSize){
        List<GatewayDto> listPage = contextQuery.findPage("select * from tbl_house", pageIndex,pageSize, GatewayDto.class);
        return  listPage;
    }
    public GatewayDto save(GatewayDto item){
        GatewayDeviceModel model = this.convertToModel(item);
        GatewayDeviceModel oldModel = dao.findOneByLocationTopic(model.getLocationTopic());
        if(null == oldModel){
            GatewayDeviceModel newModel = this.dao.save(model);
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
        GatewayDeviceModel model = this.dao.findOneByName(name);
        return model.getHouseId();
    }
    public GatewayDto update(GatewayDto item){
        Long id = item.getId();
        GatewayDeviceModel model = dao.findById(id).get();
        if (null == model ){return null;}
        this.modelMapper.map(item, model);
        System.out.println("Iot device model info ");
        System.out.println(model.toString());
        this.dao.save(model);
        return item;
    }

    public void remove(Long id){
        this.dao.deleteById(id);
    }

}