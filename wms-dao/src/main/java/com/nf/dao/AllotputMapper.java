package com.nf.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.nf.entity.Allotput;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 调拨入库dao
 */
@Repository
public interface AllotputMapper {

    /** 分页模糊查询 */
    List<Allotput> selectAll(Pagination page, Map<String, Object> params);

    /** id查询所有信息*/
    Allotput selectByPrimaryKey(Integer apId);

    /** 修改所有信息 */
    int updateByPrimaryKey(Allotput record);
    /**删除信息 */
    int deleteByPrimaryKey(Integer apId);

 /*   Allotput selectByPrimaryKeyApWhId(String apWhId);*/

}
