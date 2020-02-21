package com.nf.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.nf.commons.uilts.PageInfo;
import com.nf.dao.AllotputMapper;
import com.nf.entity.Allotput;
import com.nf.service.AllotputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 调拨入库实现类
 */
@Service
public class AllotputServiceImpl  implements AllotputService {

    @Autowired
    private AllotputMapper allotputMapper;

    /**模糊分页查询*/
    @Override
    public void selectAll(PageInfo pageinfo) {
        // page(记录数) = 当前页+每页显示的记录数
        Page<Allotput> page = new Page<>(pageinfo.getNowPage(),pageinfo.getSize());
        List<Allotput> list = allotputMapper.selectAll(page,pageinfo.getCondition()) ;
        /*显示的记录*/
        pageinfo.setRows(list);
        /*总数*/
        pageinfo.setTotal(page.getTotal());
    }
    /** id查询所有信息 */
    @Override
    public Allotput selectByPrimaryKey(Integer apId) {
        return allotputMapper.selectByPrimaryKey(apId);
    }
    /** 修改所有信息 */
    @Override
    public int updateByPrimaryKey(Allotput record) {
        return allotputMapper.updateByPrimaryKey(record);
    }

    /**删除 */
    @Override
    public int deleteByPrimaryKey(Integer apId) {
        return allotputMapper.deleteByPrimaryKey(apId);
    }

}
