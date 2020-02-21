package com.nf.service.impl;

import com.nf.dao.TariffMapper;
import com.nf.entity.Tariff;
import com.nf.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffServiceImpl implements TariffService {

    @Autowired
    private  TariffMapper tariffMapper;
    @Override
    public List<Tariff> selectByCause() {
        return tariffMapper.selectByCause();
    }
}
