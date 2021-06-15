package com.simple.bz.dao;

import com.simple.bz.model.ApplicationModel;
import com.simple.bz.model.ApplicationTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationTypeRepository extends JpaRepository<ApplicationTypeModel, Long> {
    public List<ApplicationTypeModel> findByName(String name);
    public  List<ApplicationTypeModel> findByNameLike(String name);
    public ApplicationTypeModel findOneByName(String name);



}
