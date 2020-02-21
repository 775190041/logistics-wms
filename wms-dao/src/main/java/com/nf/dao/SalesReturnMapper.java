package com.nf.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.nf.commons.uilts.PageInfo;
import com.nf.entity.SalesReturn;

import java.util.List;
import java.util.Map;

public interface SalesReturnMapper {

    int deleteByPrimaryKey(Integer srId);

    int insert(SalesReturn record);

    SalesReturn selectByPrimaryKey(Integer srId);

    List<SalesReturn> selectAll(Pagination page, Map<String, Object> params);

    int updateByPrimaryKey(SalesReturn record);

}
