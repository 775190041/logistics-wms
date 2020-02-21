package com.nf.controller;

import com.nf.commons.uilts.PageInfo;
import com.nf.commons.uilts.TimeUtils;
import com.nf.controller.base.BaseController;
import com.nf.entity.Godown;
import com.nf.entity.Invoice;
import com.nf.entity.component.Search;
import com.nf.entity.Shipment;
import com.nf.service.GodownService;
import com.nf.service.InvoiceService;
import com.nf.service.ShipmentService;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 直接发货单
 */
@Controller
@RequestMapping("/invoice")
@Slf4j
public class InvoiceController extends BaseController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private GodownService godownService;

    /** 出货单管理页面 */
    @GetMapping("/invoice.html")
    public String getInvoicePage() {
        return "outbound/invoice";
    }

    /** 分页查询 */
    @ResponseBody
    @PostMapping("/dataGrid")
    public Object dataGrid(Search search, Integer page, Integer rows) {
      PageInfo pageInfo = new PageInfo(page,rows);
      Map<String,Object> map = new HashMap<String,Object>();
      if(search.getName() != null){
          map.put("name",search.getName());
      }
      if(search.getStartTime() != null){
          map.put("startTime",search.getStartTime());
      }
      if(search.getEndTime() != null){
          map.put("endTime",search.getEndTime());
      }
      pageInfo.setCondition(map);
      invoiceService.selectDataGrid(pageInfo);
      return pageInfo;
    }

    /** 查询所有实际出库表 */
    @ResponseBody
    @GetMapping("/invoice")
    public List<Invoice> queryAll() {
        return invoiceService.queryAll();
    }

    @GetMapping("/getAddPage")
    public String getAddShipmentPage() {
        return "outbound/invoiceAdd";
    }

    /** 添加操作 */
    @ResponseBody
    @PostMapping("/invoice")
    public Object addShipment(Invoice invoice, String byTime) {
        invoice.setInTime(TimeUtils.updateTime(byTime));
        int result = invoiceService.addInvoice(invoice);
        if (result > 0) {
            return renderSuccess("添加成功!");
        } else {
            return renderError("添加失败!");
        }
    }

    /**
     * @param file
     * @param req
     */

    /*
    @RequestMapping("/ReadExcel")
    public Object ReadExcel(@RequestParam("file") MultipartFile file, HttpServletRequest req, Model model){
        try {
            String path = (makeinventoryController.class.getResource("/").toString()).substring(6);
            if (!file.isEmpty()) {
                Streams.copy(file.getInputStream(), new FileOutputStream(path + "/" + file.getOriginalFilename()), true);
            }
            URL url = makeinventoryController.class.getResource("/" + file.getOriginalFilename());
            List<List<String>> list = ReadXls.readxls(url.getFile());
            Invoice invoice = new Invoice();
            List<String> data = list.get(0);
            for(int i = 0;i<data.size();i++){
                invoice.setInName(data.get(0));
                invoice.setStore(data.get(1));
                invoice.setPhone(data.get(2));
                invoice.setInOddnumber(data.get(3));
                invoice.setInWhid(data.get(4));
                invoice.setInNum(Double.valueOf(data.get(5)));
                invoice.setInVolume(Double.valueOf(data.get(6)));
                invoice.setTotalweigh(Double.valueOf(data.get(7)));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM");
                invoice.setInTime(sdf.parse(data.get(8)));
                invoice.setInSkumodel(data.get(9));
            }
            model.addAttribute("invoice",invoice);
            }catch (Exception e) {
            e.printStackTrace();
        }
        return "outbound/invoiceImport";
    }*/

    /** 删除操作 */
    @ResponseBody
    @PostMapping("/invoice/delete")
    public Object deleteById(Integer id) {
        int result = invoiceService.deleteById(id);
        if (result > 0) {
            return renderSuccess("删除成功!");
        } else {
            return renderError("删除失败!");
        }
    }

    /** 进入编辑页面 */
    @GetMapping("/getEditPage")
    public String editPage(Model model, @RequestParam(value = "id") Integer id) {
        Invoice invoice = invoiceService.selectById(id);
        System.err.println("invoice.toString() = " + invoice.toString());
        model.addAttribute("invoice", invoice);
        return "outbound/invoiceEdit";
    }

    /** 更新操作 */
    @PostMapping("/invoice/update")
    @ResponseBody
    public Object updateInvoice(Invoice invoice) {
        Shipment shipment = getShipment(invoice);
        int result = shipmentService.addShipment(shipment);
        setGodown(invoice);
        Invoice invoice1 = new Invoice();
        return UpdateInvoice(invoice, result, invoice1);
    }

    private Object UpdateInvoice(Invoice invoice, int result, Invoice invoice1) {
        invoice1.setInStatus(1);
        invoice1.setInId(invoice.getInId());
        invoiceService.updateInvoice(invoice1);
        if (result > 0) {
            return renderSuccess("更新成功!");
        } else {
            return renderError("更新失败!");
        }
    }

    private void setGodown(Invoice invoice) {
        Godown godown;
        godown = godownService.selectByWhid(invoice.getInWhid());
        godown.setGoUsevolume(godown.getGoUsevolume()-invoice.getInVolume());//以用的要加
        godown.setGoRdvolume(godown.getGoRdvolume()+invoice.getInVolume());//可用要减
        godownService.updateByPrimaryKey(godown);
    }

    @NotNull
    private Shipment getShipment(Invoice invoice) {
        Shipment shipment = new Shipment();
        shipment.setShStoreid(invoice.getInStore());
        shipment.setShSkumodel(invoice.getInSkumodel());
        shipment.setShPhone(invoice.getInPhone());
        shipment.setShSippingno(invoice.getInOddnumber());
        shipment.setShWhid(invoice.getInWhid());
        //损害数量
        shipment.setShDamage(invoice.getInDamage());
        shipment.setShCause(invoice.getInCause());
        shipment.setShTime(new Date());
        shipment.setShShnum(invoice.getInNum());
        shipment.setShTotalweigh(invoice.getInTotalweigh());
        shipment.setShTotalvolume(invoice.getInVolume());
        return shipment;
    }

    @GetMapping("/importInvoice.html")
    public String importPage(){
        return "outbound/invoiceImport";
    }

    @ResponseBody
    @PostMapping("/invoice.php")
    public Object importCrossDatabase(Invoice invoice, String byTime){
        invoice.setInTime(TimeUtils.updateTime(byTime));
        invoice.setInCause("");
        invoice.setInDamage(0);
        int result = invoiceService.importInvoice(invoice);
        if (result > 0) {
            return renderSuccess("添加成功!");
        }
        return renderError("添加失败!");
    }

}