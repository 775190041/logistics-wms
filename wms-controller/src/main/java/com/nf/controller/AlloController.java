package com.nf.controller;

import com.nf.commons.uilts.OrderNumberUtil;

import com.nf.commons.xlsx.ExcelToDisk;
import com.nf.controller.base.BaseController;
import com.nf.entity.Godown;
import com.nf.entity.MakeInventory;
import com.nf.entity.vo.CargoVo;
import com.nf.service.GodownService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.*;


@Controller
@RequestMapping("/allo")
public class AlloController  extends BaseController {

    @Autowired
    private GodownService godownService ;

    @RequestMapping("/allPage")
    public String allPage(){
        return "adjustment/allo";
    }

    @RequestMapping("/verification")
    @ResponseBody
    public Object Verification(@RequestParam("Warehouse") String Warehouse, @RequestParam("cTotalvolume") String cTotalvolume, @RequestParam("cWhid") String cWhid){
        Godown godown =godownService.selectByPrimaryKey(Integer.valueOf(Warehouse));
        if (cWhid.equals(godown.getGoWhid())){
            return renderError("该操作不能在同一仓库进行");
        }else{
            //总容积小于可用容积
            if (Double.valueOf(cTotalvolume) > godown.getGoRdvolume()){
                return renderError("该仓库体积不足");
            }
        }
        return  renderSuccess("打印成功!");
    }


    /**
     * 调拨入库
     * @param data
     * @param resp
     * @return
     */
    @RequestMapping("/ToDiskExcelput")
    public void ToDiskExcelput(@RequestParam("data") String data[], HttpServletResponse resp){
        ExcelToDisk<MakeInventory> ex = new ExcelToDisk<MakeInventory>();
        String [] title = {"货物名称","货物型号","调拨数量","货物体积","仓库名称","调拨单号","调拨时间"};
        String cName = data[0];
        String cSkumodel = data[1];
        String cNum = data[2];
        String cTotalvolume = data[3];
        String cWhid = data[5];
        Godown godown = godownService.selectByPrimaryKey(Integer.valueOf(cWhid));
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String arr [] = {cName,cSkumodel,cNum,cTotalvolume,godown.getGoWhid(), OrderNumberUtil.generateOrderNo(),s.format(new Date())};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        ex.Excel(arr,"调拨入库单"+sdf.format(new Date())+".xlsx",title,resp,"Sheet2");
    }
    /**
     * 调拨出库
     * 目前只能导出一行
     * @param data
     * @param resp
     */
    @RequestMapping("/ToDiskExcelout")
    public void ToDiskExcelout(@RequestParam("data") String[] data, HttpServletResponse resp){
        ExcelToDisk<MakeInventory> ex = new ExcelToDisk<MakeInventory>();
        String [] title = {"货物名称","货物型号","调拨数量","货物体积","仓库名称","调拨单号","调拨时间"};
        String cName = data[0];
        String cSkumodel = data[1];
        String cNum = data[2];
        String cTotalvolume = data[3];
        String Warehouse = data[4];
        Godown godown = godownService.selectByPrimaryKey(Integer.valueOf(Warehouse));
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object[] arr = {cName,cSkumodel,cNum,cTotalvolume,godown.getGoWhid(), OrderNumberUtil.generateOrderNo(),s.format(new Date())};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        ex.Excel(arr,"调拨出库单"+sdf.format(new Date())+".xlsx",title,resp,"Sheet2");
    }
}
