package com.nf.service;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.nf.commons.uilts.PageInfo;
import com.nf.entity.Allotout;

/**
 *
 * 调拨单Service
 * @author 骚哥神机
 */
public interface AllotoutService  {

    /**分页模糊查询 */
    void selectDataGrid(PageInfo pageInfo);
    /** 添加 */
    int addAllotout(Allotout allotout);

    /** 根据ID查询 */
    Allotout queryById(Integer id);

    /** 删除 */
    int deleteAllotout(Integer id);

    /** 修改 */
    int updateAllotout(Allotout allotout);

    /** 读取文件添加*/
    int importAllotout(Allotout allotout);

}
