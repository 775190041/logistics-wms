package com.nf.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 价目表
 */
@Data
public class Tariff {
	
	@TableId(type = IdType.AUTO)
	private int id;
    /**收费原因*/
	private String cause;
    /**收费范围*/
	private int scope;
    /**收费金额*/
	private BigDecimal money;
    /**收费单位*/
	private String unit;

}
