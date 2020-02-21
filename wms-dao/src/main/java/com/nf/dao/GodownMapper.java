package com.nf.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.nf.entity.Godown;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 仓库dao
 */
@Repository
public interface GodownMapper {
    /** 仓库EasyUI下拉框查询 */
    List<Godown> findByComboBox4EasyUi();

    /** 根据id查询仓库所有信息 */
    Godown selectByPrimaryKey(Integer goId);

    /**根据仓库名称查询仓库所有信息*/
    Godown selectByWhid(String whid);

    /**修改仓库信息*/
    int updateByPrimaryKey(Godown record);

    /** 分页查询  */
    List<Godown> selectDataGrid(Pagination page, Map<String, Object> params);

    /** 添加仓库 */
    int insert(Godown record);

    /** 停用启用仓库 */
    int updateByPrimaryKeySelective(Godown record);
}
