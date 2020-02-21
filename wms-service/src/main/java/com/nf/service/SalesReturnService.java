package com.nf.service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.nf.commons.uilts.PageInfo;
import com.nf.entity.SalesReturn;

import java.util.List;
import java.util.Map;

public interface SalesReturnService {

    int deleteByPrimaryKey(Integer srId);

    int insert(SalesReturn record);

    SalesReturn selectByPrimaryKey(Integer srId);


    void selectAll(PageInfo pageinfo);

    int updateByPrimaryKey(SalesReturn record);
}
