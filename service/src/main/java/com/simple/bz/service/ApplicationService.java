package com.simple.bz.service;


import com.simple.bz.dao.ApplicationRepository;
import com.simple.bz.dto.ApplicationDto;
import com.simple.bz.model.ApplicationModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationService {
    private final ModelMapper modelMapper;

    @Autowired
    ApplicationRepository dao;
    public ApplicationModel convertToModel(ApplicationDto dto){
        return this.modelMapper.map(dto, ApplicationModel.class);
    }
    public List<ApplicationModel> convertToModels(List<ApplicationDto> dtos){
        List<ApplicationModel> resultList = new ArrayList<ApplicationModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public ApplicationDto convertToDto(ApplicationModel model){
        return this.modelMapper.map(model, ApplicationDto.class);
    }

    public List<ApplicationDto> convertToDtos(List<ApplicationModel> models){
        List<ApplicationDto> resultList = new ArrayList<ApplicationDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<ApplicationDto> findAll(){

        List<ApplicationModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }

    public  List<ApplicationDto> findByProjectId(Long id){
        List<ApplicationModel> list = dao.findByProjectId(id);
        return this.convertToDtos(list);
    }



    public ApplicationDto findById(Long id){
        ApplicationModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }
    public ApplicationDto save(ApplicationDto item){
        ApplicationModel model = this.convertToModel(item);

        this.dao.save(model);
        return  item;
    }

    public ApplicationDto update(ApplicationDto item){
        Long id = item.getId();
        ApplicationModel model = dao.findById(id).get();
        if(null == model ){return null;}
        this.modelMapper.map(item, model);
        this.dao.save(model);
        return item;
    }

    public void remove(Long id){
        this.dao.deleteById(id);
    }

}