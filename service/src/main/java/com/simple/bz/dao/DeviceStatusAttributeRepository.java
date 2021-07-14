package com.simple.bz.dao;


import com.simple.bz.model.DeviceClusterModel;
import com.simple.bz.model.StatusAttributeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceStatusAttributeRepository extends JpaRepository<StatusAttributeModel, Long> {

    public  StatusAttributeModel findOneByIeeeAndClusterAttribute(String ieee,String clusterAttr);

}
