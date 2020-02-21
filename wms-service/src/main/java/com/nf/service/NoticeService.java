package com.nf.service;

import com.nf.commons.uilts.PageInfo;
import com.nf.entity.Notice;

public interface NoticeService  {

    int deleteByPrimaryKey(Integer jId);

    int insert(Notice notice);

    int insertSelective(Notice notice);

    Notice selectByPrimaryKey(Integer nid);

    int updateByPrimaryKeySelective(Notice notice);

    int updateByPrimaryKey(Notice notice);

    void selectDataGrid(PageInfo pageInfo);

    Notice selectByDateUP();
}
