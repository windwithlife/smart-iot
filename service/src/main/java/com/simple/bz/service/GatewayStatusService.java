package com.simple.bz.service;

import com.simple.bz.dao.ContextQuery;
import com.simple.bz.dao.GatewayStatusRepository;
import com.simple.bz.dto.GatewayStatusDto;
import com.simple.bz.model.GatewayStatusModel;
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

    public GatewayStatusModel convertToModel(GatewayStatusDto dto){
        return this.modelMapper.map(dto, GatewayStatusModel.class);
    }
    public List<GatewayStatusModel> convertToModels(List<GatewayStatusDto> dtos){
        List<GatewayStatusModel> resultList = new ArrayList<GatewayStatusModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public GatewayStatusDto convertToDto(GatewayStatusModel model){
        return this.modelMapper.map(model, GatewayStatusDto.class);
    }

    public List<GatewayStatusDto> convertToDtos(List<GatewayStatusModel> models){
        List<GatewayStatusDto> resultList = new ArrayList<GatewayStatusDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<GatewayStatusDto> findAll(){

        List<GatewayStatusModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }


    public GatewayStatusDto findById(Long id){
        GatewayStatusModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }


    public List<GatewayStatusDto> queryAll(){
        List<GatewayStatusDto> list = contextQuery.findList("select * from tbl_house", GatewayStatusDto.class);
        return  list;
    }
    public List<GatewayStatusDto> queryPage(int pageIndex, int pageSize){
        List<GatewayStatusDto> listPage = contextQuery.findPage("select * from tbl_house", pageIndex,pageSize, GatewayStatusDto.class);
        return  listPage;
    }
    public GatewayStatusDto save(GatewayStatusDto item){
        GatewayStatusModel model = this.convertToModel(item);
        this.dao.save(model);
        return item;
    }


    public GatewayStatusDto queryStatusByGatewayId(Long gatewayId){
        List<GatewayStatusDto> list = contextQuery.findList("select * from tbl_gateway_status where gatewayId=" + String.valueOf(gatewayId), GatewayStatusDto.class);
        return  list.get(0);
    }
    public GatewayStatusDto update(GatewayStatusDto item){
        Long id = item.getId();
        GatewayStatusModel model = dao.findById(id).get();
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