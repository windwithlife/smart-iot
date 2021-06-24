package com.simple.bz.service;

import com.simple.bz.dao.RoomRepository;
import com.simple.bz.dto.RoomDto;
import com.simple.bz.model.RoomModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final ModelMapper modelMapper;
    
    private final RoomRepository dao;


    public RoomModel convertToModel(RoomDto dto){
        return this.modelMapper.map(dto, RoomModel.class);
    }
    public List<RoomModel> convertToModels(List<RoomDto> dtos){
        List<RoomModel> resultList = new ArrayList<RoomModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public RoomDto convertToDto(RoomModel model){
        return this.modelMapper.map(model, RoomDto.class);
    }

    public List<RoomDto> convertToDtos(List<RoomModel> models){
        List<RoomDto> resultList = new ArrayList<RoomDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<RoomDto> findAll(){

        List<RoomModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }


    public RoomDto findById(Long id){
        RoomModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }
    public RoomDto save(RoomDto item){
        RoomModel model = this.convertToModel(item);
        this.dao.save(model);
        return item;
    }

    public RoomDto update(RoomDto item){
        Long id = item.getId();
        RoomModel model = dao.findById(id).get();
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