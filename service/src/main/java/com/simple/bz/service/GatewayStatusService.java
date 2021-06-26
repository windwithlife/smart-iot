package com.simple.bz.service;

import com.simple.bz.dao.ContextQuery;
import com.simple.bz.dao.GatewayStatusRepository;
import com.simple.bz.dto.GatewayDeviceStatusDto;
import com.simple.bz.model.GatewayDeviceStatusModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GatewayStatusService {
    private final ModelMapper modelMapper;
    
    private final GatewayStatusRepository dao;
    private final ContextQuery contextQuery;

    public GatewayDeviceStatusModel convertToModel(GatewayDeviceStatusDto dto){
        return this.modelMapper.map(dto, GatewayDeviceStatusModel.class);
    }
    public List<GatewayDeviceStatusModel> convertToModels(List<GatewayDeviceStatusDto> dtos){
        List<GatewayDeviceStatusModel> resultList = new ArrayList<GatewayDeviceStatusModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public GatewayDeviceStatusDto convertToDto(GatewayDeviceStatusModel model){
        return this.modelMapper.map(model, GatewayDeviceStatusDto.class);
    }

    public List<GatewayDeviceStatusDto> convertToDtos(List<GatewayDeviceStatusModel> models){
        List<GatewayDeviceStatusDto> resultList = new ArrayList<GatewayDeviceStatusDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<GatewayDeviceStatusDto> findAll(){

        List<GatewayDeviceStatusModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }


    public GatewayDeviceStatusDto findById(Long id){
        GatewayDeviceStatusModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }


    public List<GatewayDeviceStatusDto> queryAll(){
        List<GatewayDeviceStatusDto> list = contextQuery.findList("select * from tbl_house", GatewayDeviceStatusDto.class);
        return  list;
    }
    public List<GatewayDeviceStatusDto> queryPage(int pageIndex, int pageSize){
        List<GatewayDeviceStatusDto> listPage = contextQuery.findPage("select * from tbl_house", pageIndex,pageSize, GatewayDeviceStatusDto.class);
        return  listPage;
    }
    public GatewayDeviceStatusDto save(GatewayDeviceStatusDto item){
        GatewayDeviceStatusModel model = this.convertToModel(item);
        this.dao.save(model);
        return item;
    }


    public GatewayDeviceStatusDto queryStatusByGatewayId(Long gatewayId){
        List<GatewayDeviceStatusDto> list = contextQuery.findList("select * from tbl_gateway_status where gatewayId=" + String.valueOf(gatewayId), GatewayDeviceStatusDto.class);
        return  list.get(0);
    }
    public GatewayDeviceStatusDto update(GatewayDeviceStatusDto item){
        Long id = item.getId();
        GatewayDeviceStatusModel model = dao.findById(id).get();
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