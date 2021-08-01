package com.simple.bz.dao;


import com.simple.bz.model.DeviceAttributeStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceStatusAttributeRepository extends JpaRepository<DeviceAttributeStatusModel, Long> {

    public DeviceAttributeStatusModel findOneByIeeeAndClusterAttribute(String ieee, String clusterAttr);
    public DeviceAttributeStatusModel findOneByDeviceIdAndClusterAttribute(Long deviceId, String clusterAttr);


}
