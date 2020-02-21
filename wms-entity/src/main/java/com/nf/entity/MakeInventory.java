package com.nf.entity;


import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

/**
 * make_inventory 物品盘点表
 */
@Data
public class MakeInventory {

    @TableId(type = IdType.AUTO)
    @TableField(value = "mi_id")
    private Integer miId;

    @TableField(value = "mi_name")
    private String miName;

    @TableField(value = "mi_SKUmodel")
    private String miSkumodel;

    @TableField(value = "mi_num")
    private Double miNum;

    @TableField(value = "mi_Actual")
    private Double miActual;

    @TableField(value = "mi_order")
    private String miOrder;

    @TableField(value = "mi_names")
    private String miNames;

    @TableField(value = "mi_whid")
    private String miWhid;

    @TableField(value = "mi_time")
    private Date miTime;

    @TableField(value = "mi_status")
    private String miStatus;

    private List<Godown> godowns;

    private Date createState;

    private Date createEnd;


}