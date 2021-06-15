package com.simple.bz.dao;


import com.simple.bz.model.BuildRecord;
import com.simple.bz.model.EndPointModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;






@Mapper
public interface BuiildRecordDao {


    /**
     * 添加
     * @param
     * @throws Exception
     */
    void add(BuildRecord model) throws Exception;

//    /**
//     * 修改
//     * @param
//     * @throws Exception
//     */
//    void update(EndPointModel model) throws Exception;
//
//    /**
//     * 分页获取全部总记录数
//     * @param paramMap
//     * @return
//     * @throws Exception
//     */
//    int getCount(Map<String, Object> paramMap) throws Exception;


//
//    /**
//     * 根据ID查询
//     * @param id
//     * @return
//     * @throws Exception
//     */
//    EndPointModel findById(@Param("id") Integer id) throws Exception;





}
