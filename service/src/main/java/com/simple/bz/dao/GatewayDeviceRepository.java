package com.simple.bz.dao;

import com.simple.bz.model.GatewayDeviceModel;
import com.simple.bz.model.RoomModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GatewayDeviceRepository extends JpaRepository<GatewayDeviceModel, Long> {
    public GatewayDeviceModel findOneByName(String name);
    public GatewayDeviceModel findOneByLocationTopic(String topic);
}
