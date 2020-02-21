package com.nf.service;

import com.nf.commons.uilts.PageInfo;
import com.nf.entity.Cargo;


public interface CargoService {
    /** 查询所有货物  */
    void select(PageInfo pageInfo);

    /** id查询货物 */
    Cargo selectByPrimaryKey(Integer cId);
    /**修改货物*/
    int updateByPrimaryKey(Cargo record);

   /** 删除货物*/
    int deleteByPrimaryKey(Integer cId);
    /**添加货物*/
    int insert(Cargo record);
    /** 查询货物类型 */
    Cargo selectBySkumodel(String skumodel);

    /** 修改货物数量 */
    int update(Cargo cargo);

   /** 根据货物型号删除*/
    int deleteBySjumodel(String Skumodel);

    /**  修改货物部分信息*/
    int updateByPrimaryKeySelective(Cargo record);

}
