package com.simple.bz.dao;

import com.simple.bz.model.GatewayModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewayRepository extends JpaRepository<GatewayModel, Long> {
    public GatewayModel findOneByName(String name);
    public GatewayModel findOneByLocationTopic(String topic);
}
