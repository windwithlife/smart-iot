package com.simple.bz.dao;


import com.simple.bz.model.DeviceClusterModel;
import com.simple.bz.model.DeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceClusterRepository extends JpaRepository<DeviceClusterModel, Long> {

    public  DeviceClusterModel findOneByIeee(String ieee);

}
