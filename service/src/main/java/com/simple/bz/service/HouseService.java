package com.simple.bz.service;


import com.simple.bz.dao.ContextQuery;
import com.simple.bz.dao.HouseRepository;
import com.simple.bz.dto.HouseDto;
import javax.persistence.EntityManager;
import com.simple.bz.model.HouseModel;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HouseService {
    private final ModelMapper modelMapper;
    private final HouseRepository dao;
    private final ContextQuery contextQuery;

    public HouseModel convertToModel(HouseDto dto){
        return this.modelMapper.map(dto, HouseModel.class);
    }
    public List<HouseModel> convertToModels(List<HouseDto> dtos){
        List<HouseModel> resultList = new ArrayList<HouseModel>();
        for (int i=0; i < dtos.size(); i++){
            resultList.add(this.convertToModel(dtos.get(i)));
        }
        return resultList;
    }
    public HouseDto convertToDto(HouseModel model){
        return this.modelMapper.map(model, HouseDto.class);
    }

    public List<HouseDto> convertToDtos(List<HouseModel> models){
        List<HouseDto> resultList = new ArrayList<HouseDto>();
        for (int i=0; i < models.size(); i++){
            resultList.add(this.convertToDto(models.get(i)));
        }
        return resultList;

    }
    public List<HouseDto> findAll(){

        List<HouseModel> list =   dao.findAll();
        return  this.convertToDtos(list);
    }


    public HouseDto findById(Long id){
        HouseModel model =  dao.findById(id).get();
        return this.convertToDto(model);
    }
    @ApiOperation(value="获取用户信息",tags={"获取用户信息copy"},notes="注意问题点")
    public HouseDto save(HouseDto item){
        HouseModel model = this.convertToModel(item);
        this.dao.save(model);
        return item;
    }

    public HouseDto update(HouseDto item){
        Long id = item.getId();
        HouseModel model = dao.findById(id).get();
        if (null == model ){return null;}
        this.modelMapper.map(item, model);
        System.out.println("Iot device model info ");
        System.out.println(model.toString());
        this.dao.save(model);
        return item;
    }
    public List<HouseDto> queryAll(){
        List<HouseDto> list = contextQuery.findList("select * from tbl_house", HouseDto.class);
        return  list;
    }
    public List<HouseDto> queryPage(int pageIndex, int pageSize){
        List<HouseDto> listPage = contextQuery.findPage("select * from tbl_house", pageIndex,pageSize, HouseDto.class);
        return  listPage;
    }
    public void remove(Long id){
        this.dao.deleteById(id);
    }

}