package com.nf.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 实际出库表
 */
@Data
public class Shipment {

    @TableId(type = IdType.AUTO)
    @TableField(value = "sh_id")
    private Integer shId;

    @TableField(value = "sh_storeid")
    private String shStoreid;

    @TableField(value = "sh_time")
    private Date shTime;

    @TableField(value = "sh_phone")
    private String shPhone;

    @TableField(value = "sh_sippingNO")
    private String shSippingno;

    @TableField(value = "sh_whid")
    private String shWhid;

    @TableField(value = "sh_damage")
    private Integer shDamage;

    @TableField(value = "sh_cause")
    private String shCause;

    //货物型号
    @TableField(value = "sh_SKUmodel")
    private String shSkumodel;

    @TableField(value = "sh_shnum")
    private Double shShnum;

    @TableField(value = "sh_totalweigh")
    private Double shTotalweigh;

    @TableField(value = "sh_totalvolume")
    private Double shTotalvolume;
    /**
     * status 0:已收货  1：付款  2:已付款  3:已退货
     */
    private int status;

    private List<Godown> godowns;

}