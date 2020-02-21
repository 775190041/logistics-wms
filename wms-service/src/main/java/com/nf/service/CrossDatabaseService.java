package com.nf.service;


import com.nf.commons.uilts.PageInfo;
import com.nf.entity.CrossDatabase;

/**
 * 越库出货单
 */
public interface CrossDatabaseService {

    /**
     * 越库出货单分页模糊查询
     */
    void selectDataGrid(PageInfo pageInfo);

    /**
     * 添加越库出货单
     */
    int insert(CrossDatabase crossDatabase);

    /**
     * 根据id查询越库出货单信息
     */
    CrossDatabase selectById(Integer cdId);

    /**修改部分越库出货信息*/
    int updateCrossDatabase(CrossDatabase crossDatabase);

    /** 删除越库信息 */
    int deleteById(Integer id);
}
