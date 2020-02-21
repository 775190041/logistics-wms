package com.nf.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.nf.commons.uilts.PageInfo;
import com.nf.dao.ReceivingMapper;
import com.nf.entity.Receiving;
import com.nf.service.ReceivingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceivingServiceImpl implements ReceivingService {
    @Autowired
    private ReceivingMapper receivingMapper;

    @Override
    public Receiving selectByModel(String model) {
        return receivingMapper.selectByModel(model);
    }

    @Override
    public void selectAll(PageInfo pageInfo) {
        Page<Receiving> page = new Page<>(pageInfo.getNowPage(),pageInfo.getPageSize());
        List<Receiving> list = receivingMapper.selectAll(page,pageInfo.getCondition());
        pageInfo.setRows(list);
        pageInfo.setTotal(page.getTotal());
    }

    @Override
    public Receiving selectByPrimaryKey(Integer rId) {
        return  receivingMapper.selectByPrimaryKey(rId);
    }

    @Override
    public int updateByPrimaryKey(Receiving receiving) {
        return receivingMapper.updateByPrimaryKey(receiving);
    }

    @Override
    public int deleteByPrimaryKey(Integer rId) {
        return receivingMapper.deleteByPrimaryKey(rId);
    }
}
