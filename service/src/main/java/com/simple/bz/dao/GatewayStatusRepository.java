package com.simple.bz.dao;


import com.simple.bz.model.GatewayDeviceModel;
import com.simple.bz.model.GatewayDeviceStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewayStatusRepository extends JpaRepository<GatewayDeviceStatusModel, Long> {
    public GatewayDeviceStatusModel findOneByLocationTopic(String topic);
}
