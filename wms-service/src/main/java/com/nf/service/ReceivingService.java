package com.nf.service;

import com.nf.commons.uilts.PageInfo;
import com.nf.entity.Receiving;

import java.util.List;

public interface ReceivingService {

    Receiving selectByModel(String model);

    void selectAll(PageInfo pageInfo);

    Receiving selectByPrimaryKey(Integer rId);

    int  updateByPrimaryKey(Receiving receiving);

    int deleteByPrimaryKey(Integer rId);
}
