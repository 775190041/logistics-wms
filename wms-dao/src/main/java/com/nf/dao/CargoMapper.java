package com.nf.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.nf.entity.Cargo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 货物 mapper
 */
@Repository
public interface CargoMapper {

    /** 查询所有货物  */
    List<Cargo> select(Pagination page, Map<String,Object> map);

    /** id查询货物信息 */
    Cargo selectByPrimaryKey(Integer cId);
    /** 修改货物所有信息 */
    int updateByPrimaryKey(Cargo record);

    /**删除货物*/
    int deleteByPrimaryKey(Integer cId);

    /**添加货物*/
    int insert(Cargo record);

    /** 查询货物类型 */
    Cargo selectBySkumodel(String cSkumodel);

    /** 修改货物数量 */
    int update(Cargo cargo);

   /**根据货物型号删除*/
    int deleteBySkumodel(String Skumodel);

  /**  修改货物部分信息*/
    int updateByPrimaryKeySelective(Cargo record);
}
