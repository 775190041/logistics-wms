package com.nf.controller;

import com.nf.commons.uilts.PageInfo;
import com.nf.commons.uilts.StringUtils;
import com.nf.commons.uilts.TimeUtils;
import com.nf.commons.xlsx.ExcelToDisk;
import com.nf.controller.base.BaseController;
import com.nf.entity.*;
import com.nf.entity.component.Search;
import com.nf.service.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shipment")
@Slf4j
public class ShipmentController extends BaseController {

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private CargoService cargoService;

    /** 出货单管理页面 */
    @GetMapping(value = "shipment.html")
    public String getShipmentPage() {
        return "outbound/shipment";
    }


    /** 分页查询 */
    @ResponseBody
    @PostMapping("dataGrid")
    public Object dataGrid(Search search, Integer page, Integer rows) {
        PageInfo pageInfo = new PageInfo(page, rows);
        Map<String, Object> condition = new HashMap<String, Object>();
        log.info("出库表分页查询");
        if (StringUtils.isNotBlank(search.getName())) {
            condition.put("name", search.getName());
        }
        if (search.getStartTime() != null) {
            condition.put("startTime", search.getStartTime());
        }
        if (search.getEndTime() != null) {
            condition.put("endTime", search.getEndTime());
        }
        pageInfo.setCondition(condition);
        shipmentService.selectDataGrid(pageInfo);
        return pageInfo;
    }

    /** 查询所有实际出库表 */
    @ResponseBody
    @GetMapping(value = "shipment")
    public List<Shipment> queryAll() {
        return shipmentService.queryAll();
    }

    /** 进入添加页面 */
    @GetMapping("shipment/insert")
    public String getAddShipmentPage() {
        return "outbound/shipmentAdd";
    }

    /** 添加操作 */
    @ResponseBody
    @PostMapping(value = "shipment")
    public Object addShipment(Shipment shipment, String addtime) {
        shipment.setShTime(TimeUtils.updateTime(addtime));
        int result = shipmentService.addShipment(shipment);
        if (result > 0) {
            return renderSuccess("添加成功!");
        } else {
            return renderError("添加失败!");
        }
    }

    /** 删除操作 */
    @ResponseBody
    @PostMapping("/shipment/delete")
    public Object deleteById(Integer id) {
        System.err.println("id = " + id);
        int result = shipmentService.deleteById(id);
        if (result > 0) {
            return renderSuccess("删除成功!");
        } else {
            return renderError("删除失败!");
        }
    }

    /** 确认已收货操作 */
    @ResponseBody
    @RequestMapping(value = "getEditPage")
    public Object editPage(Integer id) {
        Shipment shipment = new Shipment();
        shipment.setShId(id);
        shipment.setStatus(1);
        int result = shipmentService.updateShipment(shipment);
        if (result > 0) {
            return renderSuccess("修改成功!");
        } else {
            return renderError("修改失败!");
        }
        /* Shipment shipment = shipmentService.selectById(id);
        model.addAttribute("shipment", shipment);
        return "outbound/shipmentEdit";*/
    }
    /** 更新操作 */
    @ResponseBody
    @PostMapping("/shipment/update")
    public Object updateShipment(Shipment shipment, String addtime) {
        shipment.setShTime(TimeUtils.updateTime(addtime));
        int result = shipmentService.updateShipment(shipment);
        if (result > 0) {
            return renderSuccess("更新成功!");
        } else {
            return renderError("更新失败!");
        }
    }
    //后期再改善。
/*
    @GetMapping("importShipment.html")
    public String importPage(){
        return "outbound/shipmentImport";
    }
    @PostMapping("readExcle")
    public String readExcle(@RequestParam("file") MultipartFile file, Model model) {
        String path = (AllotoutController.class.getResource("/").toString()).substring(6);
        if (!file.isEmpty()) {
            try {
                Streams.copy(file.getInputStream(), new FileOutputStream(path + "/" + file.getOriginalFilename()), true);
                URL url = ShipmentController.class.getResource("/" + file.getOriginalFilename());
                List<List<String>> lists = ReadXls.readxls(url.getFile());
                Shipment shipment = new Shipment();
                List<String> objects = lists.get(2);
                for (int i = 0; i < objects.size(); i++) {
                    //货主
                    shipment.setShStoreid(objects.get(0));
                    //时间
                    shipment.setShTime(TimeUtils.updateTime("".equals(objects.get(1)) ? null : objects.get(1)));
                    //号码
                    shipment.setShPhone(objects.get(2));
                    //单号
                    shipment.setShSippingno(objects.get(3));
                    //仓库
                    shipment.setShWhid(objects.get(4));
                    //损坏数量
                    shipment.setShDamage(Integer.valueOf(objects.get(5)));
                    //损坏原因
                    shipment.setShCause(objects.get(6));
                    //型号
                    shipment.setShSkumodel(objects.get(7));
                    //数量
                    shipment.setShShnum(Double.valueOf(objects.get(8)));
                    //毛重
                    shipment.setShTotalweigh(Double.valueOf(objects.get(9)));
                    //体积
                    shipment.setShTotalvolume(Double.valueOf(objects.get(10)));
                }
                model.addAttribute("shipment", shipment);
            } catch (Exception e) {
                model.addAttribute("error", "请导入正确的数据!!!");
                e.printStackTrace();
            }
        }
        return "outbound/shipmentImport";
    }
*/
    @RequestMapping("/toexcel")
    public void excel(String id, HttpServletResponse resp, String reason){
        Shipment shipment = shipmentService.selectById(Integer.valueOf(id));
        Cargo c =  cargoService.selectBySkumodel(shipment.getShSkumodel());
        shipment.setStatus(3);
        shipmentService.updateShipment(shipment);
        ExcelToDisk<Shipment> e = new ExcelToDisk<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String title[] = {"货物名称","货物型号","货主","货主号码","客户托单号","退货体积","退货数量","退货时间","退货原因"};
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Object obj[] = {c.getcName(),shipment.getShSkumodel(),shipment.getShStoreid(),shipment.getShPhone(),c.getcShippingno(),shipment.getShTotalvolume(),shipment.getShShnum(),f.format(new Date()),reason};
        e.Excel(obj,"退货单"+sdf.format(new Date())+".xls",title,resp,"sheet1");
    }
}
