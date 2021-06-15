package com.simple.bz.dao;

import com.simple.bz.model.EndPointModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * 使用Mybatis SQl Java 注解的方式方式进行数据访问的样例
 * @author zhangyq
 * @version v1.0 ExampleRepo.java
 */

@Mapper
public interface ExampleExDao {

    @Select("SELECT * FROM endpoint where id=#id")
    EndPointModel findByState(@Param("id") String id);
}
