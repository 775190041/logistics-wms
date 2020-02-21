package com.nf.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.nf.commons.uilts.PageInfo;
import com.nf.dao.MakeInventoryMapper;
import com.nf.entity.MakeInventory;
import com.nf.service.MakeInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 盘点单实现类 impl
 */
@Service
public class MakeInventoryServiceImpl  implements MakeInventoryService {

    @Autowired
    private MakeInventoryMapper makeInventoryMapper;

    /** 盘点单分页 */
    @Override
    public void select(PageInfo pageInfo) {
        //当前页   每页显示的记录数
        Page<MakeInventory> page = new  Page<>(pageInfo.getNowPage(),pageInfo.getPageSize());
        //条件查询
        List<MakeInventory> list = makeInventoryMapper.select(page, pageInfo.getCondition());
        //显示的记录
        pageInfo.setRows(list);
        //总数
        pageInfo.setTotal(page.getTotal());
    }

    @Override
    public int insert(MakeInventory make) {
        return makeInventoryMapper.insert(make);
    }

    @Override
    public MakeInventory selectByPrimaryKey(Integer miId) {
        return makeInventoryMapper.selectByPrimaryKey(miId);
    }

    @Override
    public MakeInventory selectBySkumodel(String miSkumodel) {
        return makeInventoryMapper.selectBySkumodel(miSkumodel);
    }

    @Override
    public int updateByPrimaryKey(MakeInventory make) {
        return makeInventoryMapper.updateByPrimaryKey(make);
    }

    @Override
    public int deleteByPrimaryKey(Integer miId) {
        return makeInventoryMapper.deleteByPrimaryKey(miId);
    }
}
