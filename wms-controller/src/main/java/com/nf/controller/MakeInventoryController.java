package com.nf.controller;

import com.nf.commons.uilts.PageInfo;
import com.nf.controller.base.BaseController;
import com.nf.entity.Adjust;
import com.nf.entity.Cargo;
import com.nf.entity.MakeInventory;
import com.nf.service.AdjustService;
import com.nf.service.CargoService;
import com.nf.service.GodownService;
import com.nf.service.MakeInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  盘点单管理controller
 */
@Controller
@RequestMapping("/make")
public class MakeInventoryController extends BaseController {
    @Autowired
    private MakeInventoryService makeInventoryService;

    @Autowired
    private AdjustService adjustService;

    @Autowired
    private CargoService cargoService;

    @Autowired
    private GodownService godownService;


    /** 盘点单页面 */
    @RequestMapping("/selectPage")
    public String selectpage(){
        return "adjustment/makeInventory";
    }


    /**
     * 分页查询
     * @param page
     * @param rows
     * @param m
     * @return
     */
    @RequestMapping("/select")
    @ResponseBody
    public Object select(Integer page, Integer rows, MakeInventory m){
        PageInfo pageInfo = new PageInfo(page,rows);
        Map<String,Object> map = new HashMap<>(16);
        if(m.getMiSkumodel() != null){
            map.put("SKM",m.getMiSkumodel());
        }
        if(m.getCreateState() != null){
            map.put("stateTime",m.getCreateState());
        }
        if(m.getCreateEnd() != null){
            map.put("endTime",m.getCreateEnd());
        }
        pageInfo.setCondition(map);
        makeInventoryService.select(pageInfo);
        return pageInfo;
    }

    /**
     * 新增
     * @param m
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    public Object insert(MakeInventory m){
        int result = makeInventoryService.insert(m);
        if(result  > 0){
            return renderSuccess("添加成功");
        }
        return renderError("添加失败");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Object deleteByPrimaryKey(Integer id){
        int result = makeInventoryService.deleteByPrimaryKey(id);
        if(result > 0){
            return renderSuccess("删除成功");
        }
        return renderError("删除失败");
    }

    /**
     *  更新库存
     * @param id
     * @param miActual
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    //利用商品编号进行误查。
    public Object edit(Integer id,Double miActual,Double miNum,String name,String miSkumodel){
        MakeInventory makeInventory = new MakeInventory();
        MakeInventory makeInventory1 = makeInventoryService.selectBySkumodel(miSkumodel);
        makeInventory.setMiId(id);
        makeInventory.setMiStatus("1");
        makeInventoryService.updateByPrimaryKey(makeInventory);
        Adjust adjust = new Adjust();
        adjust.setJName(makeInventory1.getMiName());
        adjust.setJSkumodel(makeInventory1.getMiSkumodel());
        adjust.setJNum(miActual - miNum);
        adjust.setJNames(name);
        adjust.setJCause("更新库存");
        adjust.setJTime(new Date());
        adjust.setJWhid(makeInventory1.getMiWhid());
        Cargo cargo = cargoService.selectBySkumodel(makeInventory1.getMiSkumodel());
        if(miActual < miNum){
            adjust.setJVolum((cargo.getcTotalvolume()/cargo.getcNum())*((miActual-miNum)*-1));
            cargo.setcTotalvolume(cargo.getcTotalvolume()-(cargo.getcTotalvolume()/cargo.getcNum())*((miActual-miNum)*-1));
            cargo.setcTotalweight(cargo.getcTotalweight()-((cargo.getcTotalweight()/cargo.getcNum())*((miActual-miNum)*-1)));
        }else{
            adjust.setJVolum((cargo.getcTotalvolume()/cargo.getcNum())*(miActual-miNum));
            cargo.setcTotalvolume(cargo.getcTotalvolume()-(cargo.getcTotalvolume()/cargo.getcNum())*(miActual-miNum));
            cargo.setcTotalweight(cargo.getcTotalweight()-((cargo.getcTotalweight()/cargo.getcNum())*(miActual-miNum)));
        }
        System.out.println("adjust.toString" +
                "" +
                "() = " + adjust.toString());
        int result = adjustService.insert(adjust);
        if(result >0){
            cargo.setcNum(miActual.intValue());
            cargo.setcTotalvolume(cargo.getcTotalvolume());
            cargoService.update(cargo);
        }
        return renderSuccess("修改成功");
    }
}
