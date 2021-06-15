package com.simple.bz.service;



import com.simple.bz.dao.DockerImageRepository;
import com.simple.bz.dto.DockerBuildRequest;
import com.simple.bz.dto.DockerImageDto;
import com.simple.bz.model.DockerImageModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//此注解保证变量被DI
@RequiredArgsConstructor
@Service
public class DockerImageService {
    private final ModelMapper modelMapper;


    private final DockerImageRepository dao;
    private final ApplicationDeploymentService deploymentService;

    public DockerImageModel convertToModel(DockerImageDto dto){
        return this.modelMapper.map(dto, DockerImageModel.class);
    }
    public List<DockerImageModel> convertToModels(List<DockerImageDto> dtos){
        List<DockerImageModel> resultList = new ArrayList<DockerImageModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public DockerImageDto convertToDto(DockerImageModel model){
        return this.modelMapper.map(model, DockerImageDto.class);
    }

    public List<DockerImageDto> convertToDtos(List<DockerImageModel> models){
        List<DockerImageDto> resultList = new ArrayList<DockerImageDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<DockerImageDto> findAll(){

        List<DockerImageModel> list =  dao.findAll();
        return  this.convertToDtos(list);
    }

    public List<DockerImageDto> findByApplicationId(Long id){

        List<DockerImageModel> list =  dao.findByApplicationId(id);
        return  this.convertToDtos(list);
    }


    public DockerImageDto findById(Long id){
        DockerImageModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }
    public void save(DockerImageDto item){
        DockerImageModel model = this.convertToModel(item);
        this.dao.save(model);


    }

    public void buildDockerImageAndDeploy (DockerBuildRequest request){
        deploymentService.deployApplicationAuto(request);
    }
    public void buildDockerImage(DockerBuildRequest request){

        deploymentService.buildImage(request);

    }
    public DockerImageDto update(DockerImageDto item){
        Long id = item.getId();
        DockerImageModel model = dao.findById(id).get();
        if(null == model ){return null;}
        this.modelMapper.map(item, model);
        this.dao.save(model);
        return item;
    }


    public void remove(Long id){
        this.dao.deleteById(id);
    }

}