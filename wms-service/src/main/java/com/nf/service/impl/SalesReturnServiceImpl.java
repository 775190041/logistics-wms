package com.nf.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.nf.commons.uilts.PageInfo;
import com.nf.dao.SalesReturnMapper;
import com.nf.entity.SalesReturn;
import com.nf.service.SalesReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesReturnServiceImpl implements SalesReturnService {
    @Autowired
    private SalesReturnMapper salesReturn;

    @Override
    public int deleteByPrimaryKey(Integer srId) {
        return salesReturn.deleteByPrimaryKey(srId);
    }

    @Override
    public int insert(SalesReturn record) {
        return salesReturn.insert(record);
    }

    @Override
    public SalesReturn selectByPrimaryKey(Integer srId) {
        return salesReturn.selectByPrimaryKey(srId);
    }

    @Override
    public void selectAll(PageInfo pageinfo) {
        Page<SalesReturn> page  = new Page<SalesReturn>(pageinfo.getNowPage(),pageinfo.getPageSize());
        List<SalesReturn> list = salesReturn.selectAll(page,pageinfo.getCondition());
        pageinfo.setRows(list);
        pageinfo.setTotal(page.getTotal());
    }
    @Override
    public int updateByPrimaryKey(SalesReturn record) {
        return salesReturn.updateByPrimaryKey(record);
    }

}
