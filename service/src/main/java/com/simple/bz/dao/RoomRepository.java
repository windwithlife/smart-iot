package com.simple.bz.dao;

import com.simple.bz.model.RoomModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<RoomModel, Long>{
    public List<RoomModel> findByName(String name);
    public  List<RoomModel> findByNameLike(String name);
    public RoomModel findOneByName(String name);

}
