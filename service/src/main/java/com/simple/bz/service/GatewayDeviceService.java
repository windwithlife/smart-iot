package com.simple.bz.service;

import com.simple.bz.dao.ContextQuery;
import com.simple.bz.dao.GatewayDeviceRepository;
import com.simple.bz.dao.RoomRepository;
import com.simple.bz.dto.GatewayDeviceDto;
import com.simple.bz.model.GatewayDeviceModel;
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

    public GatewayDeviceModel convertToModel(GatewayDeviceDto dto){
        return this.modelMapper.map(dto, GatewayDeviceModel.class);
    }
    public List<GatewayDeviceModel> convertToModels(List<GatewayDeviceDto> dtos){
        List<GatewayDeviceModel> resultList = new ArrayList<GatewayDeviceModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public GatewayDeviceDto convertToDto(GatewayDeviceModel model){
        return this.modelMapper.map(model, GatewayDeviceDto.class);
    }

    public List<GatewayDeviceDto> convertToDtos(List<GatewayDeviceModel> models){
        List<GatewayDeviceDto> resultList = new ArrayList<GatewayDeviceDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<GatewayDeviceDto> findAll(){

        List<GatewayDeviceModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }


    public GatewayDeviceDto findById(Long id){
        GatewayDeviceModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }


    public List<GatewayDeviceDto> queryAll(){
        List<GatewayDeviceDto> list = contextQuery.findList("select * from tbl_house", GatewayDeviceDto.class);
        return  list;
    }
    public List<GatewayDeviceDto> queryPage(int pageIndex, int pageSize){
        List<GatewayDeviceDto> listPage = contextQuery.findPage("select * from tbl_house", pageIndex,pageSize, GatewayDeviceDto.class);
        return  listPage;
    }
    public GatewayDeviceDto save(GatewayDeviceDto item){
        GatewayDeviceModel model = this.convertToModel(item);
        this.dao.save(model);
        return item;
    }


    public List<GatewayDeviceDto> queryByHouseId(Long houseId){
        List<GatewayDeviceDto> list = contextQuery.findList("select * from tbl_gateway where houseId=" + String.valueOf(houseId), GatewayDeviceDto.class);
        return  list;
    }
    public GatewayDeviceDto update(GatewayDeviceDto item){
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