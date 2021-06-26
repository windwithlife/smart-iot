package com.simple.bz.dao;


import com.simple.bz.model.DeviceStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceStatusRepository extends JpaRepository<DeviceStatusModel, Long> {
    public DeviceStatusModel findByDeviceId(Long deviceId);



}
