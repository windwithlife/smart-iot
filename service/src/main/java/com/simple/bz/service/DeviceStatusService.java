package com.simple.bz.service;

import com.simple.bz.dao.ContextQuery;
import com.simple.bz.dao.DeviceRepository;
import com.simple.bz.dao.DeviceStatusRepository;
import com.simple.bz.dto.DeviceStatusDto;
import com.simple.bz.model.DeviceStatusModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DeviceStatusService {
    private final ModelMapper modelMapper;
    
    private final DeviceStatusRepository dao;
    private final ContextQuery contextQuery;

    public DeviceStatusModel convertToModel(DeviceStatusDto dto){
        return this.modelMapper.map(dto, DeviceStatusModel.class);
    }
    public List<DeviceStatusModel> convertToModels(List<DeviceStatusDto> dtos){
        List<DeviceStatusModel> resultList = new ArrayList<DeviceStatusModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public DeviceStatusDto convertToDto(DeviceStatusModel model){
        return this.modelMapper.map(model, DeviceStatusDto.class);
    }

    public List<DeviceStatusDto> convertToDtos(List<DeviceStatusModel> models){
        List<DeviceStatusDto> resultList = new ArrayList<DeviceStatusDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<DeviceStatusDto> findAll(){

        List<DeviceStatusModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }


    public DeviceStatusDto findById(Long id){
        DeviceStatusModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }


    public List<DeviceStatusDto> queryAll(){
        List<DeviceStatusDto> list = contextQuery.findList("select * from tbl_device_status", DeviceStatusDto.class);
        return  list;
    }
    public List<DeviceStatusDto> queryPage(int pageIndex, int pageSize){
        List<DeviceStatusDto> listPage = contextQuery.findPage("select * from tbl_device_status", pageIndex,pageSize, DeviceStatusDto.class);
        return  listPage;
    }
    public DeviceStatusDto save(DeviceStatusDto item){
        DeviceStatusModel model = this.convertToModel(item);
        this.dao.save(model);
        return item;
    }


    public DeviceStatusDto queryByDeviceId(Long deviceId){
        DeviceStatusModel model = dao.findByDeviceId(deviceId);
        return this.convertToDto(model);
    }

    public DeviceStatusDto update(DeviceStatusDto item){
        Long id = item.getId();
        DeviceStatusModel model = dao.findById(id).get();
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