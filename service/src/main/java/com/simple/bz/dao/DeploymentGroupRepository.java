package com.simple.bz.dao;

import com.simple.bz.model.ApplicationModel;
import com.simple.bz.model.DeploymentGroupModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeploymentGroupRepository extends JpaRepository<DeploymentGroupModel, Long> {
    public List<DeploymentGroupModel> findByName(String name);
    public  List<DeploymentGroupModel> findByNameLike(String name);
    public DeploymentGroupModel findOneByName(String name);



}
