package com.nf.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 调拨出库单
 * @author 骚哥神机
 */
@Data
public class Allotout {

    @TableId(type = IdType.AUTO)
    @TableField(value = "ao_id")
    private Integer aoId;

    //货物名称
    @TableField(value = "ao_name")
    private String aoName;

    //货物型号
    @TableField(value = "ao_SKUmodel")
    private String aoSkumodel;

    //调拨数量
    @TableField(value = "ao_num")
    private Integer aoNum;

    //仓库编号
    @TableField(value = "ao_whid")
    private String aoWhid;

    //调拨单号
    @TableField(value = "ao_sippingNO")
    private String aoSippingno;

    //调拨时间
    @TableField(value = "ao_time")
    private Date aoTime;

    //货物体积
    @TableField(value = "ao_volume")
    private Double aoVolume;

    private List<Godown> godowns;


    public void setAoName(String aoName) {
        this.aoName = aoName == null ? null : aoName.trim();
    }

    public void setAoSkumodel(String aoSkumodel) {
        this.aoSkumodel = aoSkumodel == null ? null : aoSkumodel.trim();
    }

    public void setAoWhid(String aoWhid) {
        this.aoWhid = aoWhid == null ? null : aoWhid.trim();
    }

    public void setAoSippingno(String aoSippingno) {
        this.aoSippingno = aoSippingno == null ? null : aoSippingno.trim();
    }

    @Override
    public String toString() {
        return "Allotout {" +
                "aoId=" + aoId +
                ", aoName='" + aoName + '\'' +
                ", aoSkumodel='" + aoSkumodel + '\'' +
                ", aoNum=" + aoNum +
                ", aoWhid='" + aoWhid + '\'' +
                ", aoSippingno='" + aoSippingno + '\'' +
                ", aoTime=" + aoTime +
                ", aoVolume=" + aoVolume +
                ", godowns=" + godowns +
                '}';
    }
}
