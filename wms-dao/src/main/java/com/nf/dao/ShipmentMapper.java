package com.nf.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.nf.entity.Shipment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 实际入库dao
 */
@Repository
public interface ShipmentMapper {
    /** 查询所有不带条件 */
    List<Shipment> queryAll();
    /** 分页模糊查询*/
    List<Shipment> selectShipmentPage(Pagination page, Map<String, Object> params);
    /**添加实际入库 */
    int addShipment(Shipment shipment);
    /**根据ID删除*/
    int deleteByPrimaryKey(Integer shId);
    /**根据ID查询*/
    Shipment selectByPrimaryKey(Integer shId);

    int updateByPrimaryKeySelective(Shipment record);

    int insertByPrimaryKey(Shipment record);

}
