package com.simple.bz.service;



import com.simple.bz.dao.CategoryRepository;
import com.simple.bz.dao.DictionaryRepository;
import com.simple.bz.dto.CategoryDto;
import com.simple.bz.model.CategoryModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final ModelMapper modelMapper;
    
    private final CategoryRepository dao;


    public CategoryModel convertToModel(CategoryDto dto){
        return this.modelMapper.map(dto, CategoryModel.class);
    }
    public List<CategoryModel> convertToModels(List<CategoryDto> dtos){
        List<CategoryModel> resultList = new ArrayList<CategoryModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public CategoryDto convertToDto(CategoryModel model){
        return this.modelMapper.map(model, CategoryDto.class);
    }

    public List<CategoryDto> convertToDtos(List<CategoryModel> models){
        List<CategoryDto> resultList = new ArrayList<CategoryDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<CategoryDto> findAll(){

        List<CategoryModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }


    public CategoryDto findById(Long id){
        CategoryModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }
    public CategoryDto save(CategoryDto item){
        CategoryModel model = this.convertToModel(item);
        this.dao.save(model);
        return item;
    }

    public CategoryDto update(CategoryDto item){
        Long id = item.getId();
        CategoryModel model = dao.findById(id).get();
        if(null == model ){return null;}
        this.modelMapper.map(item, model);
        this.dao.save(model);
        return item;
    }

    public void remove(Long id){
        this.dao.deleteById(id);
    }

}