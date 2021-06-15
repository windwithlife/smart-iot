package com.simple.bz.service;



import com.simple.bz.dao.ProjectRepository;
import com.simple.bz.dto.ProjectDto;
import com.simple.bz.model.ProjectModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ModelMapper modelMapper;
    
    private final ProjectRepository dao;


    public ProjectModel convertToModel(ProjectDto dto){
        return this.modelMapper.map(dto, ProjectModel.class);
    }
    public List<ProjectModel> convertToModels(List<ProjectDto> dtos){
        List<ProjectModel> resultList = new ArrayList<ProjectModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public ProjectDto convertToDto(ProjectModel model){
        return this.modelMapper.map(model, ProjectDto.class);
    }

    public List<ProjectDto> convertToDtos(List<ProjectModel> models){
        List<ProjectDto> resultList = new ArrayList<ProjectDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<ProjectDto> findAll(){

        List<ProjectModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }


    public ProjectDto findById(Long id){
        ProjectModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }
    public ProjectDto save(ProjectDto item){
        ProjectModel model = this.convertToModel(item);
        this.dao.save(model);
        return item;
    }

    public ProjectDto update(ProjectDto item){
        Long id = item.getId();
        ProjectModel model = dao.findById(id).get();
        if(null == model ){return null;}
        this.modelMapper.map(item, model);
        System.out.println("project model info ");
        System.out.println(model.toString());
        this.dao.save(model);
        return item;
    }

    public void remove(Long id){
        this.dao.deleteById(id);
    }

}