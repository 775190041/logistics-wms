package com.nf.service;

import com.nf.entity.Tariff;

import java.util.List;

public interface TariffService {

    List<Tariff> selectByCause();
}
