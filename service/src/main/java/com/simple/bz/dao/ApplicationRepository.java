package com.simple.bz.dao;

import com.simple.bz.model.ApplicationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<ApplicationModel, Long> {
    public List<ApplicationModel> findByName(String name);
    public  List<ApplicationModel> findByNameLike(String name);
    public ApplicationModel findOneByName(String name);
    public  List<ApplicationModel> findByProjectId(Long id);
    public ApplicationModel findOneByApplicationName(String applicationName);


}
