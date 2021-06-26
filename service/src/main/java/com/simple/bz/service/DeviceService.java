package com.simple.bz.service;

import com.simple.bz.dao.ContextQuery;
import com.simple.bz.dao.DeviceRepository;
import com.simple.bz.dao.GatewayDeviceRepository;
import com.simple.bz.dto.DeviceDto;
import com.simple.bz.model.DeviceModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DeviceService {
    private final ModelMapper modelMapper;
    
    private final DeviceRepository dao;
    private final ContextQuery contextQuery;

    public DeviceModel convertToModel(DeviceDto dto){
        return this.modelMapper.map(dto, DeviceModel.class);
    }
    public List<DeviceModel> convertToModels(List<DeviceDto> dtos){
        List<DeviceModel> resultList = new ArrayList<DeviceModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public DeviceDto convertToDto(DeviceModel model){
        return this.modelMapper.map(model, DeviceDto.class);
    }

    public List<DeviceDto> convertToDtos(List<DeviceModel> models){
        List<DeviceDto> resultList = new ArrayList<DeviceDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<DeviceDto> findAll(){

        List<DeviceModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }


    public DeviceDto findById(Long id){
        DeviceModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }


    public List<DeviceDto> queryAll(){
        List<DeviceDto> list = contextQuery.findList("select * from tbl_device", DeviceDto.class);
        return  list;
    }
    public List<DeviceDto> queryPage(int pageIndex, int pageSize){
        List<DeviceDto> listPage = contextQuery.findPage("select * from tbl_device", pageIndex,pageSize, DeviceDto.class);
        return  listPage;
    }
    public DeviceDto save(DeviceDto item){
        DeviceModel model = this.convertToModel(item);
        this.dao.save(model);
        return item;
    }


    public List<DeviceDto> queryByGatewayId(Long gatewayId){
        List<DeviceDto> list = contextQuery.findList("select * from tbl_gateway where gatewayDeviceId=" + String.valueOf(gatewayId), DeviceDto.class);
        return  list;
    }
    public DeviceDto update(DeviceDto item){
        Long id = item.getId();
        DeviceModel model = dao.findById(id).get();
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