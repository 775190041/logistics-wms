package com.nf.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * cross_database 越库出货单
 */
@Data
public class CrossDatabase {

    @TableId(type = IdType.AUTO)
    @TableField(value = "cd_id")
    private Integer cdId;

    @TableField(value = "cd_name")
    private String cdName;

    @TableField(value = "cd_SKUmodel")
    private String cdSkumodel;

    @TableField(value = "cd_num")
    private Double cdNum;

    @TableField(value = "cd_whid")
    private String cdWhid;

    @TableField(value = "cd_oddnumbers")
    private String cdOddnumbers;

    @TableField(value = "cd_time")
    private Date cdTime;

    @TableField(value = "cd_volume")
    private Double cdVolume;

    @TableField(value = "cd_store")
    private String cdStore;

    @TableField(value = "cd_phone")
    private String cdPhone;

    @TableField(value = "cd_damage")
    private int cdDamage;

    @TableField(value = "cd_cause")
    private String cdCause;

    @TableField(value = "cd_totalweigh")
    private Double cdTotalweigh;

    /**
     * status 0:正在确认  1:已确认
     */
    @TableField(value = "cd_status")
    private int cdStatus;

    private List<Godown> godowns;

}
