package com.simple.bz.dao;

import com.simple.bz.model.ApplicationDeploymentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationDeploymentRepository extends JpaRepository<ApplicationDeploymentModel, Long> {
    public List<ApplicationDeploymentModel> findByName(String name);
    public  List<ApplicationDeploymentModel> findByApplicationId(Long id);
    public  List<ApplicationDeploymentModel> findByApplicationIdAndEnvType(Long id, String envType);



}
