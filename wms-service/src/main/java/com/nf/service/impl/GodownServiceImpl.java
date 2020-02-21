package com.nf.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.nf.commons.uilts.PageInfo;
import com.nf.dao.GodownMapper;
import com.nf.entity.Godown;
import com.nf.service.GodownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GodownServiceImpl implements GodownService {

    @Autowired
    private GodownMapper godownMapper;

    /** 仓库EasyUI下拉框查询 */
    @Override
    public List<Godown> godownComboBox() {
        return godownMapper.findByComboBox4EasyUi();
    }

    /** 根据id查询仓库所有信息*/
    @Override
    public Godown selectByPrimaryKey(Integer goId) {
        return godownMapper.selectByPrimaryKey(goId);
    }

    /** 根据id查询仓库所有信息*/
    @Override
    public Godown selectById(int id) {
        return godownMapper.selectByPrimaryKey(id);
    }

    /** 修改仓库信息 */
    @Override
    public int updateByPrimaryKey(Godown record) {
        return godownMapper.updateByPrimaryKey(record);
    }


    /** 修改仓库 */
    @Override
    public int reduction(String id, Double volume) {
        //根据仓库名称查询仓库所有信息
        Godown godowns = godownMapper.selectByWhid(id);
        //根据id查询仓库所有信息
        Godown godown = selectById(godowns.getGoId());
        // 以用容积 = 已用体积 - 仓库容积
        godown.setGoUsevolume(godown.getGoUsevolume() - volume);
        //可用容积 = 可用容积 + 仓库容积
        godown.setGoRdvolume(godown.getGoRdvolume() + volume);

        return updateByPrimaryKey(godown);
    }

    /** 分页查询 */
    @Override
    public void selectDataGrid(PageInfo pageInfo) {
        //当前页   每页显示的记录数
        Page<Godown> page  = new Page<>(pageInfo.getNowPage(),pageInfo.getPageSize());
        //条件查询
        List<Godown> list = godownMapper.selectDataGrid(page,pageInfo.getCondition());
        //显示的记录
        pageInfo.setRows(list);
        //总数
        pageInfo.setTotal(page.getTotal());
    }
    /** 仓库添加 */
    @Override
    public int insert(Godown record) {
        return godownMapper.insert(record);
    }

    /**停用或启用仓库 */
    @Override
    public int updateStatus(Godown godown) {
        return godownMapper.updateByPrimaryKeySelective(godown);
    }

    /**根据仓库名称查询仓库所有信息*/
    @Override
    public Godown selectByWhid(String whid) {
        return godownMapper.selectByWhid(whid);
    }

}
