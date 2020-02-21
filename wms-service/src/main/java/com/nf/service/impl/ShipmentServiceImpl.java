package com.nf.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.nf.commons.uilts.PageInfo;
import com.nf.dao.ShipmentMapper;
import com.nf.entity.Shipment;
import com.nf.service.GodownService;
import com.nf.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实际入库impl
 */
@Service
public class ShipmentServiceImpl implements ShipmentService {
    @Autowired
    private ShipmentMapper shipmentMapper;

    @Autowired
    private GodownService godownService;

    /** 添加 */
    @Override
    public int addShipment(Shipment shipment) {
        return shipmentMapper.addShipment(shipment) ;
    }

    /** 分页模糊查询 */
    @Override
    public void selectDataGrid(PageInfo pageInfo) {
        Page<Shipment> page = new Page<Shipment>(pageInfo.getNowPage(), pageInfo.getSize());
        List<Shipment> list = shipmentMapper.selectShipmentPage(page, pageInfo.getCondition());
        pageInfo.setRows(list);
        pageInfo.setTotal(page.getTotal());
    }

    /** 删除 */
    @Override
    public int deleteById(Integer id) {
        Shipment shipment = shipmentMapper.selectByPrimaryKey(id);
        System.err.println("shipment = " + shipment);
        if (shipment != null){
            System.err.println("0------------------------0");
                return  shipmentMapper.deleteByPrimaryKey(id);
        }else{
            //没有此id
            System.err.println("432fds0--------ewgasdg");
            return  0;
        }
    }

    /** 查询所有不带条件*/
    @Override
    public List<Shipment> queryAll() {
        return shipmentMapper.queryAll();
    }

    @Override
    public int updateShipment(Shipment shipment) {
        return shipmentMapper.updateByPrimaryKeySelective(shipment);
    }


    @Override
    public int importShipment(Shipment shipment) {
        godownService.reduction(shipment.getShWhid(), shipment.getShTotalvolume());
        return shipmentMapper.insertByPrimaryKey(shipment);
    }

    @Override
    public Shipment selectById(Integer id) {
        return shipmentMapper.selectByPrimaryKey(id);
    }
}
