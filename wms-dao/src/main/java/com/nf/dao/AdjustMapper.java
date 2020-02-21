package com.nf.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.nf.entity.Adjust;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 库存调整dao
 */
@Repository
public interface AdjustMapper {

    /** 查询库存调整所有数据 */
    List<Adjust> select(Pagination pagination, Map<String,Object> map);
    /** 删除库存 */
    int deleteByPrimaryKey(Integer jId);
    /** 添加库存 */
    int insert(Adjust adjust);

}
