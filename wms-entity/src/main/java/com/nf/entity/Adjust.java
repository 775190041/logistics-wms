package com.nf.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * adjust 调整入库/出库单
 * @author 骚哥神机
 */
@Data
public class Adjust {

    @JsonProperty
    @TableId(type = IdType.AUTO)
    @TableField(value = "j_id")
    private Integer jId;

    @JsonProperty
    @TableField(value = "j_name")
    private String jName;

    @JsonProperty
    @TableField(value = "j_SKUmodel")
    private String jSkumodel;

    @JsonProperty
    @TableField(value = "j_num")
    private Double jNum;

    @JsonProperty
    @TableField(value = "j_names")
    private String jNames;

    @JsonProperty
    @TableField(value = "j_cause")
    private String jCause;

    @JsonProperty
    @TableField(value = "j_time")
    private Date jTime;

    @JsonProperty
    @TableField(value = "j_whid")
    private String jWhid;

    @JsonProperty
    @TableField(value = "j_volum")
    private Double jVolum;

    private Date createdateStart;
    private Date createdateEnd;

    private List<Godown> godowns;

}