package com.nf.service;

import com.nf.commons.uilts.PageInfo;
import com.nf.entity.Adjust;
import org.springframework.stereotype.Service;

public interface AdjustService {

    void select(PageInfo pageInfo);

    int deleteByPrimaryKey(Integer jId);

    int insert(Adjust adjust);
}
