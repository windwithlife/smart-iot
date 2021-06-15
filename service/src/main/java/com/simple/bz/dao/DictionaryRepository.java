package com.simple.bz.dao;

import com.simple.bz.model.DictionaryModel;
import com.simple.bz.model.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DictionaryRepository extends JpaRepository<DictionaryModel, Long> {
    public List<DictionaryModel> findByName(String name);
    public  List<DictionaryModel> findByNameLike(String name);
    public DictionaryModel findOneByName(String name);
    public List<DictionaryModel> findByCategoryId(Long id);


}
