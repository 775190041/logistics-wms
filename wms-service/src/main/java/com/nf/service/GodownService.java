package com.nf.service;

import com.nf.commons.uilts.PageInfo;
import com.nf.entity.Godown;
import com.nf.entity.vo.UserVo;

import java.util.List;

/**
 * 仓库service
 */
public interface GodownService {

    /** 仓库EasyUI下拉框查询 */
    List<Godown> godownComboBox();

    /** 根据id查询仓库所有信息*/
    Godown selectByPrimaryKey(Integer goId);

    Godown selectById(int id);

    /**修改仓库信息*/
    int updateByPrimaryKey(Godown record);

    /**
     * @param id     仓库编号
     * @param volume 货物体积
     * @return
     * @deprecated 仓库增加仓库容量操作
     */
    int reduction(String id, Double volume);

    /** 分页查询 */
    void selectDataGrid(PageInfo pageInfo);

    /** 添加仓库 */
    int insert(Godown record);

    /** 停用启用仓库 */
    int updateStatus(Godown godown);

    /**根据仓库名称查询仓库所有信息*/
    Godown selectByWhid(String whid);
}
