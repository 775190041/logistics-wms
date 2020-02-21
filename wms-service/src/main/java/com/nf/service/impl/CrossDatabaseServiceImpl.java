package com.nf.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.nf.commons.uilts.OrderNumberUtil;
import com.nf.commons.uilts.PageInfo;
import com.nf.dao.CrossDatabaseMapper;
import com.nf.dao.GodownMapper;
import com.nf.entity.CrossDatabase;
import com.nf.service.CrossDatabaseService;
import com.nf.service.GodownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class CrossDatabaseServiceImpl implements CrossDatabaseService {

    @Autowired
    private CrossDatabaseMapper crossDatabaseMapper;

    @Autowired
    private GodownService godownService;

    @Override
    public void selectDataGrid(PageInfo pageInfo) {
        Page<CrossDatabase> page = new Page<>(pageInfo.getNowPage(),pageInfo.getSize());
        List<CrossDatabase> list = crossDatabaseMapper.selectDataGrid(page,pageInfo.getCondition());
        pageInfo.setRows(list);
        pageInfo.setTotal(page.getTotal());
    }

    @Transactional
    @Override
    public int insert(CrossDatabase crossDatabase) {
        crossDatabase.setCdOddnumbers(OrderNumberUtil.generateOrderNo());
        godownService.reduction(crossDatabase.getCdWhid(),crossDatabase.getCdVolume());
        return crossDatabaseMapper.insert(crossDatabase);
    }

    @Override
    public CrossDatabase selectById(Integer cdId) {
        return crossDatabaseMapper.selectById(cdId);
    }

    @Override
    public int updateCrossDatabase(CrossDatabase crossDatabase) {
        return  crossDatabaseMapper.updateCrossDatabase(crossDatabase);
    }

    @Override
    public int deleteById(Integer cdId) {
        return crossDatabaseMapper.deleteById(cdId);
    }
}
