package com.nf.entity.vo;

import com.nf.commons.uilts.OrderNumberUtil;
import lombok.Data;

import javax.print.DocFlavor;
import java.util.Date;

@Data
public class CargoVo {

    private String cName;
    private String cSkumodel;
    private String cNum;
    private String cTotalvolume;
    private String cWhid;
    private String oderNo;
    private String allocateTime;

    public CargoVo(String cName, String cSkumodel, String cNum, String cTotalvolume, String cWhid, String oderNo, String allocateTime) {
        this.cName = cName;
        this.cSkumodel = cSkumodel;
        this.cNum = cNum;
        this.cTotalvolume = cTotalvolume;
        this.cWhid = cWhid;
        this.oderNo = oderNo;
        this.allocateTime = allocateTime;
    }
}