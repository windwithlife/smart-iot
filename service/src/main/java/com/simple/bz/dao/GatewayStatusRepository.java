package com.simple.bz.dao;


import com.simple.bz.model.GatewayStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewayStatusRepository extends JpaRepository<GatewayStatusModel, Long> {
    public GatewayStatusModel findOneByLocationTopic(String topic);
}
