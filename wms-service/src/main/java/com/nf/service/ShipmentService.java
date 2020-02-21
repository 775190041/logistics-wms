package com.nf.service;

import com.nf.commons.uilts.PageInfo;
import com.nf.entity.Shipment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实际入库service
 */

public interface ShipmentService {
    /**添加 */
    int addShipment(Shipment shipment);

    /**模糊分页查询 */
    void selectDataGrid(PageInfo pageInfo);

    /**删除 */
    int deleteById(Integer id);

  /**  查询所有不带条件*/
    List<Shipment> queryAll();

    /** 修改 */
    int updateShipment(Shipment shipment);

    int importShipment(Shipment shipment);


    Shipment selectById(Integer id);
}
