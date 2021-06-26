package com.simple.bz.service;

import com.simple.bz.dao.ContextQuery;
import com.simple.bz.dao.RoomRepository;
import com.simple.bz.dto.HouseDto;
import com.simple.bz.dto.RoomDto;
import com.simple.bz.model.RoomModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final ModelMapper modelMapper;
    
    private final RoomRepository dao;
    private final ContextQuery contextQuery;

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

//    public List<RoomDto> findAllPages(){
//        List<RoomDto> list = dao.findList("select * from tbl_room", null,this.entityManager, RoomDto.class);
//        return  list;
//    }
//    public List<RoomDto> findPage(int pageIndex, int pageSize){
//        List<RoomDto> listPage = dao.findPage("select * from tbl_room", null, this.entityManager,pageIndex,pageSize,RoomDto.class);
//        return  listPage;
//    }
    public List<RoomDto> queryAll(){
        List<RoomDto> list = contextQuery.findList("select * from tbl_house", RoomDto.class);
        return  list;
    }
    public List<RoomDto> queryPage(int pageIndex, int pageSize){
        List<RoomDto> listPage = contextQuery.findPage("select * from tbl_house", pageIndex,pageSize, RoomDto.class);
        return  listPage;
    }
    public RoomDto save(RoomDto item){
        RoomModel model = this.convertToModel(item);
        this.dao.save(model);
        return item;
    }


    public List<RoomDto> queryByHouseId(Long houseId){
        List<RoomDto> list = contextQuery.findList("select * from tbl_room where houseId=" + String.valueOf(houseId) + "", RoomDto.class);
        return  list;
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