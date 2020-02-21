package com.nf.dao;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.nf.entity.MakeInventory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 盘点单管理 dao
 */
@Repository
public interface MakeInventoryMapper {
    /** 分页查询盘点单信息*/
    List<MakeInventory> select(Pagination pagination, Map<String,Object> condition);
    /**根据id查询盘点单信息*/
    MakeInventory selectByPrimaryKey(Integer miId);

    /** 根据货物型号查询*/
    MakeInventory selectBySkumodel(String miSkumodel);

    /**添加盘点单信息*/
    int insert (MakeInventory make);
    /**修改盘点单数据状态*/
    int updateByPrimaryKey(MakeInventory make);
    /**删除盘点单信息*/
    int  deleteByPrimaryKey(Integer miId);
}
