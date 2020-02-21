package com.nf.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.nf.entity.Notice;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 公告栏dao
 */
@Repository
public interface NoticeMapper {
    int deleteByPrimaryKey(Integer jId);

    int insert(Notice notice);

    int insertSelective(Notice notice);

    Notice selectByPrimaryKey(Integer nid);

    int updateByPrimaryKeySelective(Notice notice);

    int updateByPrimaryKey(Notice notice);

    List<Notice> selectDataGrid(Pagination page, Map<String, Object> params);

    Notice selectByDateUP();
}
