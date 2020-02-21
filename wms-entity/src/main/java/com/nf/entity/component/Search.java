package com.nf.entity.component;

import lombok.Data;

import java.util.Date;

/**
 *  搜索实体类
 * @author 骚哥神机
 */
@Data
public class Search {

    /** 名称 */
    private String name;

    /** 开始时间 */
    private Date startTime;

    /** 结束时间 */
    private Date endTime;

    /** 单号 */
    private String orderNo;
}
