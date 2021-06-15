package com.simple.bz.service;

import com.simple.bz.dao.ApplicationRepository;
import com.simple.bz.dao.DeploymentConfigRepository;

import com.simple.bz.dto.DeploymentConfigDto;
import com.simple.bz.model.ApplicationModel;
import com.simple.bz.model.DeploymentConfigModel;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//此注解保证变量被DI
@RequiredArgsConstructor
@Service
public class DeploymentConfigService {
    private final ModelMapper modelMapper;


    private final DeploymentConfigRepository dao;

    private final ApplicationRepository applicationDao;

    public DeploymentConfigModel convertToModel(DeploymentConfigDto dto){
        return this.modelMapper.map(dto, DeploymentConfigModel.class);
    }
    public List<DeploymentConfigModel> convertToModels(List<DeploymentConfigDto> dtos){
        List<DeploymentConfigModel> resultList = new ArrayList<DeploymentConfigModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public DeploymentConfigDto convertToDto(DeploymentConfigModel model){
        return this.modelMapper.map(model, DeploymentConfigDto.class);
    }

    public List<DeploymentConfigDto> convertToDtos(List<DeploymentConfigModel> models){
        List<DeploymentConfigDto> resultList = new ArrayList<DeploymentConfigDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<DeploymentConfigDto> findAll(){

        List<DeploymentConfigModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }



    public DeploymentConfigDto findById(Long id){
        DeploymentConfigModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }
    public void save(DeploymentConfigDto item){
        DeploymentConfigModel model = this.convertToModel(item);
        this.dao.save(model);
        ApplicationModel application = this.applicationDao.findById(model.getApplicationId()).get();
        application.setDeploymentConfigId(model.getId());
        this.applicationDao.save(application);

    }

    public DeploymentConfigDto update(DeploymentConfigDto item){
        Long id = item.getId();
        DeploymentConfigModel model = dao.findById(id).get();
        if(null == model ){return null;}
        this.modelMapper.map(item, model);
        this.dao.save(model);
        return item;
    }


    public void remove(Long id){
        this.dao.deleteById(id);
    }

}