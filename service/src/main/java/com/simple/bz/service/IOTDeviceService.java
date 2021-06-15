package com.simple.bz.service;



import com.simple.bz.dao.DeviceRepository;
import com.simple.bz.dao.GatewayDeviceRepository;
import com.simple.bz.dto.IOTDeviceDto;
import com.simple.bz.model.IOTDeviceModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class IOTDeviceService {
    private final ModelMapper modelMapper;
    
    private final DeviceRepository dao;


    public IOTDeviceModel convertToModel(IOTDeviceDto dto){
        return this.modelMapper.map(dto, IOTDeviceModel.class);
    }
    public List<IOTDeviceModel> convertToModels(List<IOTDeviceDto> dtos){
        List<IOTDeviceModel> resultList = new ArrayList<IOTDeviceModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public IOTDeviceDto convertToDto(IOTDeviceModel model){
        return this.modelMapper.map(model, IOTDeviceDto.class);
    }

    public List<IOTDeviceDto> convertToDtos(List<IOTDeviceModel> models){
        List<IOTDeviceDto> resultList = new ArrayList<IOTDeviceDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<IOTDeviceDto> findAll(){

        List<IOTDeviceModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }


    public IOTDeviceDto findById(Long id){
        IOTDeviceModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }
    public IOTDeviceDto save(IOTDeviceDto item){
        IOTDeviceModel model = this.convertToModel(item);
        this.dao.save(model);
        return item;
    }

    public IOTDeviceDto update(IOTDeviceDto item){
        Long id = item.getId();
        IOTDeviceModel model = dao.findById(id).get();
        if(null == model ){return null;}
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