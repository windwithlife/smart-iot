package com.simple.bz.dao;

import com.simple.bz.model.HouseModel;
import com.simple.bz.model.UserHouseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHouseRepository extends JpaRepository<UserHouseModel, Long>{
    public List<UserHouseModel> findByHouseId(Long houseId);
    public List<UserHouseModel> findByUserIdAndHouseId(String userId, Long houseId);


}
