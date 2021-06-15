package com.simple.bz.service;



import com.simple.bz.dao.DeploymentGroupRepository;
import com.simple.bz.dto.DeploymentGroupDto;
import com.simple.bz.model.DeploymentGroupModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//此注解保证变量被DI
@RequiredArgsConstructor
@Service
public class DeploymentGroupService {
    private final ModelMapper modelMapper;


    private final DeploymentGroupRepository dao;

    public DeploymentGroupModel convertToModel(DeploymentGroupDto dto){
        return this.modelMapper.map(dto, DeploymentGroupModel.class);
    }
    public List<DeploymentGroupModel> convertToModels(List<DeploymentGroupDto> dtos){
        List<DeploymentGroupModel> resultList = new ArrayList<DeploymentGroupModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public DeploymentGroupDto convertToDto(DeploymentGroupModel model){
        return this.modelMapper.map(model, DeploymentGroupDto.class);
    }

    public List<DeploymentGroupDto> convertToDtos(List<DeploymentGroupModel> models){
        List<DeploymentGroupDto> resultList = new ArrayList<DeploymentGroupDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<DeploymentGroupDto> findAll(){

        List<DeploymentGroupModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }



    public DeploymentGroupDto findById(Long id){
        DeploymentGroupModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }
    public void save(DeploymentGroupDto item){
        DeploymentGroupModel model = this.convertToModel(item);
        this.dao.save(model);


    }

    public DeploymentGroupDto update(DeploymentGroupDto item){
        Long id = item.getId();
        DeploymentGroupModel model = dao.findById(id).get();
        if(null == model ){return null;}
        this.modelMapper.map(item, model);
        this.dao.save(model);
        return item;
    }


    public void remove(Long id){
        this.dao.deleteById(id);
    }

}