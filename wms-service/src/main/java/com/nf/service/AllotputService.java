package com.nf.service;

import com.nf.commons.uilts.PageInfo;
import com.nf.entity.Allotput;

/**
 * 调拨入库service
 */

public interface AllotputService {
    /** 模糊分页查询*/
    void selectAll(PageInfo pageinfo);
    /** id查询所有信息*/
    Allotput selectByPrimaryKey(Integer id);
    /** 修改所有信息 */
    int updateByPrimaryKey(Allotput record);
    /** 删除信息 */
    int deleteByPrimaryKey(Integer apId);

/*    *//**根据仓库名称查询信息*//*
    Allotput selectByPrimaryKeyApWhId(String apWhId);*/
}
