package com.nf.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.nf.commons.uilts.PageInfo;
import com.nf.dao.AdjustMapper;
import com.nf.entity.Adjust;
import com.nf.service.AdjustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdjustServiceImpl implements AdjustService {

    @Autowired
    private AdjustMapper adjustMapper;

    @Override
    public void select(PageInfo pageInfo) {
        Page<Adjust> page = new Page<>(pageInfo.getNowPage(),pageInfo.getPageSize());
        List<Adjust> list = adjustMapper.select(page,pageInfo.getCondition());
        //显示的行数
        pageInfo.setRows(list);
        //总数
        pageInfo.setTotal(page.getTotal());
    }

    @Override
    public int deleteByPrimaryKey(Integer jId) {
        return adjustMapper.deleteByPrimaryKey(jId);
    }

    @Override
    public int insert(Adjust adjust) {
        return adjustMapper.insert(adjust);
    }
}
