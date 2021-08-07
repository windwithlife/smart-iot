package com.simple.bz.dao;


import com.simple.bz.model.DeviceStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceAttributeStatusRepository extends JpaRepository<DeviceStatusModel, Long> {

    public DeviceStatusModel findOneByIeeeAndClusterAttribute(String ieee, String clusterAttr);
    public DeviceStatusModel findOneByDeviceIdAndClusterAttribute(Long deviceId, String clusterAttr);
    public void deleteByDeviceId(Long deviceId);


}
