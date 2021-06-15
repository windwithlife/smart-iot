package com.simple.bz.service;


import com.simple.bz.dao.ApplicationTypeRepository;
import com.simple.bz.dto.ApplicationTypeDto;
import com.simple.bz.model.ApplicationTypeModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationTypeService {
    private final ModelMapper modelMapper;


    private final ApplicationTypeRepository dao;


    public ApplicationTypeModel convertToModel(ApplicationTypeDto dto){
        return this.modelMapper.map(dto, ApplicationTypeModel.class);
    }
    public List<ApplicationTypeModel> convertToModels(List<ApplicationTypeDto> dtos){
        List<ApplicationTypeModel> resultList = new ArrayList<ApplicationTypeModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public ApplicationTypeDto convertToDto(ApplicationTypeModel model){
        return this.modelMapper.map(model, ApplicationTypeDto.class);
    }

    public List<ApplicationTypeDto> convertToDtos(List<ApplicationTypeModel> models){
        List<ApplicationTypeDto> resultList = new ArrayList<ApplicationTypeDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<ApplicationTypeDto> findAll(){

        List<ApplicationTypeModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }


    public ApplicationTypeDto findById(Long id){
        ApplicationTypeModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }
    public ApplicationTypeDto save(ApplicationTypeDto item){
        ApplicationTypeModel model = this.convertToModel(item);
        this.dao.save(model);
        return item;
    }

    public ApplicationTypeDto update(ApplicationTypeDto item){
        Long id = item.getId();
        ApplicationTypeModel model = dao.findById(id).get();
        if(null == model ){return null;}
        this.modelMapper.map(item, model);
        this.dao.save(model);
        return item;
    }

    public void remove(Long id){
        this.dao.deleteById(id);
    }

}