package com.simple.bz.dao;

import com.simple.bz.model.HouseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HouseRepository extends JpaRepository<HouseModel, Long> {
    public List<HouseModel> findByName(String name);
    public  List<HouseModel> findByNameLike(String name);
    public HouseModel findOneByName(String name);

}
