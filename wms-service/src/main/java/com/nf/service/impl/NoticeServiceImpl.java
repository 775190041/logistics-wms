package com.nf.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.nf.commons.uilts.PageInfo;
import com.nf.dao.NoticeMapper;
import com.nf.entity.Notice;
import com.nf.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;


    @Override
    public int deleteByPrimaryKey(Integer jId) {
        return noticeMapper.deleteByPrimaryKey(jId);
    }

    @Override
    public int insert(Notice notice) {
        return noticeMapper.insert(notice);
    }

    @Override
    public int insertSelective(Notice notice) {
        return noticeMapper.insertSelective(notice);
    }

    @Override
    public Notice selectByPrimaryKey(Integer nid) {
        return noticeMapper.selectByPrimaryKey(nid);
    }

    @Override
    public int updateByPrimaryKeySelective(Notice notice) {
        return noticeMapper.updateByPrimaryKeySelective(notice);
    }

    @Override
    public int updateByPrimaryKey(Notice notice) {
        return noticeMapper.updateByPrimaryKey(notice);
    }

    @Override
    public void selectDataGrid( PageInfo pageInfo) {
        Page<Notice> page = new Page<Notice>(pageInfo.getNowPage(), pageInfo.getSize());
        List<Notice> list = noticeMapper.selectDataGrid(page,pageInfo.getCondition());
        pageInfo.setRows(list);
        pageInfo.setTotal(page.getTotal());
    }

    @Override
    public Notice selectByDateUP() {
        return noticeMapper.selectByDateUP();
    }
}
