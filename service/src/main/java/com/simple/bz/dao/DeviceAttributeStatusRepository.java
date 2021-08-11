package com.simple.bz.dao;


import com.simple.bz.model.DeviceStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceAttributeStatusRepository extends JpaRepository<DeviceStatusModel, Long> {

    public DeviceStatusModel findOneByIeeeAndClusterAttribute(String ieee, String clusterAttr);
    public DeviceStatusModel findOneByDeviceIdAndClusterAttribute(Long deviceId, String clusterAttr);
    public List<DeviceStatusModel> findOneByDeviceId(Long deviceId);
    public List<DeviceStatusModel> findByDeviceId(Long deviceId);
    public void deleteByDeviceId(Long deviceId);


}
