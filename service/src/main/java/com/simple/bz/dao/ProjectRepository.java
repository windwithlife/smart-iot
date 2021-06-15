package com.simple.bz.dao;

import com.simple.bz.model.ApplicationTypeModel;
import com.simple.bz.model.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectModel, Long> {
    public List<ProjectModel> findByName(String name);
    public  List<ProjectModel> findByNameLike(String name);
    public ProjectModel findOneByName(String name);



}
