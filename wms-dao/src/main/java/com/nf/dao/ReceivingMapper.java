package com.nf.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.nf.entity.Receiving;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReceivingMapper {

    Receiving selectByModel(String model);

    List<Receiving> selectAll(Pagination page, Map<String, Object> params);

     Receiving selectByPrimaryKey(Integer rId);

    int updateByPrimaryKey(Receiving receiving);

    int deleteByPrimaryKey(Integer rId);
}
