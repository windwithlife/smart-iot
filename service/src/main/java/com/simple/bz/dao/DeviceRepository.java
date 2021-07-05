package com.simple.bz.dao;


import com.simple.bz.model.DeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<DeviceModel, Long> {

    public  DeviceModel findOneByGatewayIdAndAddress(Long gatewayId, String address);


}
