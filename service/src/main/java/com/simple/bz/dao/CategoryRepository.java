package com.simple.bz.dao;

import com.simple.bz.model.CategoryModel;
import com.simple.bz.model.DictionaryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
    public List<CategoryModel> findByName(String name);
    public  List<CategoryModel> findByNameLike(String name);
    public CategoryModel findOneByName(String name);



}
