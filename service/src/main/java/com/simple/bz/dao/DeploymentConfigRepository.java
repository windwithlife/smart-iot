package com.simple.bz.dao;

import com.simple.bz.model.ApplicationModel;
import com.simple.bz.model.DeploymentConfigModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeploymentConfigRepository extends JpaRepository<DeploymentConfigModel, Long> {

}
