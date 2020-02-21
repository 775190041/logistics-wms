package com.nf.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Income {
	@TableId(type = IdType.AUTO)
    private Integer iid;

    private String icause;

    private Date itime;

    private String iabout;

    private BigDecimal icount;

    private BigDecimal iincome;

    private BigDecimal ibalance;
}