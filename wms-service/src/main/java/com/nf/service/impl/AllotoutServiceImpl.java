package com.nf.service.impl;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.nf.commons.uilts.OrderNumberUtil;
import com.nf.commons.uilts.PageInfo;
import com.nf.dao.AllotoutMapper;
import com.nf.entity.Allotout;
import com.nf.service.AllotoutService;
import com.nf.service.GodownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 调拨单IMPL
 */
@Service
public class AllotoutServiceImpl  implements AllotoutService {

    @Autowired
    private AllotoutMapper allotoutMapper;
    @Autowired
    private GodownService godownService;

    /**
     * 分页模糊查询
     * @param pageInfo
     */
    @Override
    public void selectDataGrid(PageInfo pageInfo) {
        Page<Allotout> page = new Page<>(pageInfo.getNowPage(),pageInfo.getPageSize());
        List<Allotout> list = allotoutMapper.selectAllotoutPage(page,pageInfo.getCondition());
        pageInfo.setRows(list);
        pageInfo.setTotal(page.getTotal());
    }

    /**
     *  添加
     * @param allotout
     * @return
     */
    @Override
    public int addAllotout(Allotout allotout) {
        allotout.setAoSippingno(OrderNumberUtil.generateOrderNo());
        return allotoutMapper.insert(allotout);
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Override
    public Allotout queryById(Integer id) {
        return allotoutMapper.selectByPrimaryKey(id);
    }

    /**
     *  查询后删除
     * @param id
     * @return
     */
    @Override
    public int deleteAllotout(Integer id) {
        //查询要删除的是否存在
        Allotout allotout  = queryById(id);
        if (allotout != null){
            return allotoutMapper.deleteByPrimaryKey(id);
        }
        return 0;
    }

    /**
     * 修改
     * @param allotout
     * @return
     */
    @Override
    public int updateAllotout(Allotout allotout) {
        return allotoutMapper.updateByPrimaryKey(allotout);
    }


    /**
     * 添加
     * @param allotout
     * @return
     */

    @Override
    public int importAllotout(Allotout allotout) {
        godownService.reduction(allotout.getAoWhid(), allotout.getAoVolume());
        return allotoutMapper.insert(allotout);
    }
}
