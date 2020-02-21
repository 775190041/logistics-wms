package com.nf.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Invoice 直接发货单
 */
@Data
public class Invoice {

    @TableId(type = IdType.AUTO)
    @TableField(value = "in_id")
    private Integer inId;

    @TableField(value = "in_name")
    private String inName;

    @TableField(value = "in_SKUmodel")
    private String inSkumodel;

    @TableField(value = "in_num")
    private Double inNum;

    @TableField(value = "in_whid")
    private String inWhid;

    @TableField(value = "in_oddnumber")
    private String inOddnumber;

    @TableField(value = "in_time")
    private Date inTime;

    @TableField(value = "in_volume")
    private Double inVolume;

    @TableField(value = "in_store")
    private String inStore;

    @TableField(value = "in_phone")
    private String inPhone;

    @TableField(value = "in_damage")
    private int inDamage;

    @TableField(value = "in_cause")
    private String inCause;

    @TableField(value = "in_totalweigh")
    private Double inTotalweigh;

    /**
     * status 0:正在确认  1:已确认
     */
    @TableField(value = "in_status")
    private int inStatus;

    private List<Godown> godowns;

}