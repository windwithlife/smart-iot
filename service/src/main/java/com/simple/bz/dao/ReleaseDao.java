package com.simple.bz.dao;


import com.simple.bz.dto.ReleaseDetail;
import com.simple.bz.model.ApplicationDeploymentModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface ReleaseDao {

    /**
     * 根据ID查询
     * @param id
     * @return
     * @throws Exception
     */
    ApplicationDeploymentModel findById(@Param("id") Long id) throws Exception;
    ReleaseDetail findDetailById(@Param("id") Long id) throws Exception;


}
