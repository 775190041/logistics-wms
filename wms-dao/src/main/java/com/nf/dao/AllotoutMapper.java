package com.nf.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.nf.entity.Allotout;

import java.util.List;
import java.util.Map;

/**
 * 调拨出库单
 * @author 骚哥神机
 */
public interface AllotoutMapper {
    /** 查询所有 */
    List<Allotout> selectAllotoutPage(Pagination pagination , Map<String,Object> condition);

    /** 根据ID查询 */
    Allotout selectByPrimaryKey(Integer aoId);

    /** 删除 */
    int deleteByPrimaryKey(Integer aoId);

    /** 修改 */
    int updateByPrimaryKey(Allotout record);

    /** 添加 */
    int insert(Allotout record);

    /** 读取文件添加*/
   int insertSelective(Allotout record);

   //int updateByPrimaryKeySelective(Allotout record);
}
