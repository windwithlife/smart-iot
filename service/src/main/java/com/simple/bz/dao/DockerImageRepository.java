package com.simple.bz.dao;


import com.simple.bz.model.DockerImageModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DockerImageRepository extends JpaRepository<DockerImageModel, Long> {
    public List<DockerImageModel> findByApplicationId(Long id);
}
