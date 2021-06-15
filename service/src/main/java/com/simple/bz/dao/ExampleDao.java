package com.simple.bz.dao;


import com.simple.bz.dto.ExampleVO;
import com.simple.bz.model.EndPointModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;


/**
 * 使用Mybatis SQl Java 注解的方式方式进行数据访问的样例
 * @author zhangyq
 * @version v1.0 ExampleRepo.java
 */

@Mapper
public interface ExampleDao {



    //List<ExampleVO> findByName(String name) throws Exception;
    /**
     * 添加
     * @param
     * @throws Exception
     */
    void add(EndPointModel model) throws Exception;

    /**
     * 修改
     * @param
     * @throws Exception
     */
    void update(EndPointModel model) throws Exception;

    /**
     * 分页获取全部总记录数
     * @param paramMap
     * @return
     * @throws Exception
     */
    int getCount(Map<String, Object> paramMap) throws Exception;



    /**
     * 根据ID查询
     * @param id
     * @return
     * @throws Exception
     */
    EndPointModel findById(@Param("id") Integer id) throws Exception;


    /**
     * 删除
     * @param paramMap
     * @throws Exception
     */
    int deleteById(String id) throws Exception;


}
