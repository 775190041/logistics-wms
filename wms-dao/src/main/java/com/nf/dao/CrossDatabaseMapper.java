package com.nf.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.nf.entity.CrossDatabase;

import java.util.List;
import java.util.Map;

public interface CrossDatabaseMapper {
    /** 越库出货分页模糊查讯*/
    List<CrossDatabase> selectDataGrid(Pagination pagination, Map<String,Object> condition);

    /**添加越库出货*/
    int insert(CrossDatabase crossDatabase);

   /** 根据id查询越库出货单信息 */
   CrossDatabase selectById(Integer id);

    /** 修改部分越库出货信息 */
    int updateCrossDatabase(CrossDatabase crossDatabase);

   /** 删除越库信息*/
    int deleteById(Integer cdId);
}
