package com.nf.dao;

import com.nf.entity.Tariff;

import java.util.List;

public interface TariffMapper {

    /** 查询收费表 */
    List<Tariff> selectByCause ();

}
