package com.simple.bz.dao;


import com.simple.bz.model.DeploymentGroupModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 使用Mybatis SQl Java 注解的方式方式进行数据访问的样例
 * @author zhangyq
 * @version v1.0 ExampleRepo.java
 */

@Mapper
public interface ExecutePointDao {

    /**
     * 根据ID查询
     * @param id
     * @return
     * @throws Exception
     */
    DeploymentGroupModel findById(@Param("id") Long id) throws Exception;


}
