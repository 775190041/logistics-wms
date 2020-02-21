package com.nf.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 调拨入库
 */
@Data
public class Allotput {

    @TableId(type = IdType.AUTO)
    @TableField(value = "ap_id")
    private Integer apId;

    @TableField(value = "ap_name")
    private String apName;

    @TableField(value = "ap_SKUmodel")
    private String apSkumodel;

    @TableField(value = "ap_num")
    private Double apNum;

    @TableField(value = "ap_whid")
    private String apWhid;

    @TableField(value = "ap_sipping")
    private String apSipping;

    @TableField(value = "ap_time")
    private Date apTime;

    @TableField(value = "ap_volume")
    private Double apVolume;

    @TableField(value = "ap_address")
    private String apAddress;

    private Date createdateStart;
    private Date createdateEnd;

    private List<Godown> godowns;

}
