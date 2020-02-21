package com.nf.service;

import com.nf.commons.uilts.PageInfo;
import com.nf.entity.MakeInventory;

import java.util.List;

/**
 * 盘点单管理 service
 */
public interface MakeInventoryService {
    /** 盘点单查询 */
    void select(PageInfo pageInfo);

    /** 添加 */
    int insert(MakeInventory make);

    /** 根据id查询盘点单*/
    MakeInventory selectByPrimaryKey(Integer miId);

    /** 根据货物型号查询*/
    MakeInventory selectBySkumodel(String miSkumodel);

    /** 修改盘点单数据状态 */
    int updateByPrimaryKey(MakeInventory make);

    /** 删除 */
    int deleteByPrimaryKey(Integer miId);
}
