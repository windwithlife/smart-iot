package com.simple.bz.service;



import com.simple.bz.dao.CategoryRepository;
import com.simple.bz.dao.DictionaryRepository;
import com.simple.bz.dao.ProjectRepository;
import com.simple.bz.dto.DictionaryDto;
import com.simple.bz.model.CategoryModel;
import com.simple.bz.model.DictionaryModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DictionaryService {
    private final ModelMapper modelMapper;
    
    private final DictionaryRepository dao;
    private final CategoryRepository categoryDao;

    public DictionaryModel convertToModel(DictionaryDto dto){
        return this.modelMapper.map(dto, DictionaryModel.class);
    }
    public List<DictionaryModel> convertToModels(List<DictionaryDto> dtos){
        List<DictionaryModel> resultList = new ArrayList<DictionaryModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public DictionaryDto convertToDto(DictionaryModel model){
        return this.modelMapper.map(model, DictionaryDto.class);
    }

    public List<DictionaryDto> convertToDtos(List<DictionaryModel> models){
        List<DictionaryDto> resultList = new ArrayList<DictionaryDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<DictionaryDto> findAll(){

        List<DictionaryModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }
    public List<DictionaryDto> findByCategoryName(String name){
        CategoryModel categoryModel = categoryDao.findOneByName(name);

        List<DictionaryModel> list =  dao.findByCategoryId(categoryModel.getId());
        return  this.convertToDtos(list);
    }


    public DictionaryDto findById(Long id){
        DictionaryModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }
    public DictionaryDto save(DictionaryDto item){
        DictionaryModel model = this.convertToModel(item);
        this.dao.save(model);
        return item;
    }

    public DictionaryDto update(DictionaryDto item){
        Long id = item.getId();
        DictionaryModel model = dao.findById(id).get();
        if(null == model ){return null;}
        this.modelMapper.map(item, model);
        this.dao.save(model);
        return item;
    }

    public void remove(Long id){
        this.dao.deleteById(id);
    }

}