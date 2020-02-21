package com.nf.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.nf.commons.uilts.PageInfo;
import com.nf.dao.CargoMapper;
import com.nf.entity.Cargo;
import com.nf.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 货物实现类
 */
@Service
public class CargoServiceImpl implements CargoService {

    @Autowired
    private CargoMapper cargoMapper;

    @Override
    public void select(PageInfo pageInfo) {
        Page<Cargo> page = new Page<>(pageInfo.getNowPage(),pageInfo.getSize());
        List<Cargo> list = cargoMapper.select(page,pageInfo.getCondition());
        pageInfo.setRows(list);
        pageInfo.setTotal(pageInfo.getTotal());
    }

    @Override
    public Cargo selectByPrimaryKey(Integer cId) {
        return cargoMapper.selectByPrimaryKey(cId);
    }

    @Override
    public int updateByPrimaryKey(Cargo record) {
        return cargoMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer cId) {
        return cargoMapper.deleteByPrimaryKey(cId);
    }

    @Override
    public int insert(Cargo record) {
        return cargoMapper.insert(record);
    }

    @Override
    public Cargo selectBySkumodel(String cSkumodel) {
        return cargoMapper.selectBySkumodel(cSkumodel);
    }

    @Override
    public int update(Cargo cargo) {
        return cargoMapper.update(cargo);
    }

    @Override
    public int deleteBySjumodel(String Skumodel) {
        return cargoMapper.deleteBySkumodel(Skumodel);
    }

    @Override
    public int updateByPrimaryKeySelective(Cargo record) {
        return cargoMapper.updateByPrimaryKeySelective(record);
    }
}
