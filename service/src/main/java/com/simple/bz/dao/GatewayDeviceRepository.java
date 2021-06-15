package com.simple.bz.dao;

import com.simple.bz.model.CategoryModel;
import com.simple.bz.model.GatewayDeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GatewayDeviceRepository extends JpaRepository<GatewayDeviceModel, Long> {




}
