package com.simple.bz.dao;


import com.simple.bz.model.DeviceClusterModel;
import com.simple.bz.model.DeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceClusterRepository extends JpaRepository<DeviceClusterModel, Long> {

    public List<DeviceClusterModel> findByIeee(String ieee);
    public List<DeviceClusterModel> findByDeviceId(Long deviceId);

}
