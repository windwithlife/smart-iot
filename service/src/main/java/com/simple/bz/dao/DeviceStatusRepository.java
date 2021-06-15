package com.simple.bz.dao;


import com.simple.bz.model.DeviceStatusModel;
import com.simple.bz.model.IOTDeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceStatusRepository extends JpaRepository<DeviceStatusModel, Long> {




}
