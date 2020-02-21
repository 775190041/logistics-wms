package com.nf.controller;


import com.nf.commons.uilts.PageInfo;
import com.nf.commons.uilts.StringUtils;
import com.nf.commons.xlsx.ReadXls;
import com.nf.controller.base.BaseController;
import com.nf.entity.Godown;
import com.nf.entity.SalesReturn;
import com.nf.service.GodownService;
import com.nf.service.SalesReturnService;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/salesreturn")
public class SalesReturnController extends BaseController {

    @Autowired
    private SalesReturnService salesReturnService;

    @Autowired
    private GodownService godownService;

    @RequestMapping("/salesreturnAddPage")
    public String salesreturnAddPage(){
        return "putstorage/salesreturnAddPage";
    }

    @GetMapping("/salesreturnPage")
    public String salesreturnPage(){
        return "putstorage/salesreturnPage";
    }

    @RequestMapping("/readExcle")
    public String Excle(@RequestParam("file") MultipartFile file, HttpServletRequest request, Model model){
        try {
            String path = (SalesReturnController.class.getResource("/").toString()).substring(6);
            if(!file.isEmpty()){
                Streams.copy(file.getInputStream(),new FileOutputStream(path+"/"+file.getOriginalFilename()),true);
            }
            URL url = SalesReturnController.class.getResource("/"+file.getOriginalFilename());
            List<List<String>> list = ReadXls.readxls(url.getFile());
            System.out.println(list);
            SalesReturn s = new SalesReturn();
            List<String> obj = list.get(1);
            for(int j = 0; j < obj.size(); j++){
                s.setSrName(obj.get(0));//货物名称
                s.setSrSkumodel(obj.get(1));//货物型号
                s.setSrStorerid(obj.get(2));//货主
                s.setSrPhone(obj.get(3));//号码
                s.setSrSipping(obj.get(4));//客户托单号
                s.setSrVolume(Double.valueOf(obj.get(5)));//退货体积
                if("".equals(obj.get(6))){
                    s.setSrNum(Double.valueOf(0));
                }else{
                    s.setSrNum(Double.valueOf(obj.get(6).trim()));//数量
                }
                s.setSrTime(updateTime("".equals(obj.get(7))?null:obj.get(7)));//时间
                s.setSrCause(obj.get(8));//退货原因
            }
            model.addAttribute("salesreturn",s );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "putstorage/salesreturnAddPage";
    }

    @PostMapping("/save")
    @ResponseBody
    public Object save(SalesReturn salesreturn, String time,String nameWhid){
        salesreturn.setSrTime(updateTime(time));
        int a = salesReturnService.insert(salesreturn);
        Godown g = godownService.selectByWhid(salesreturn.getSrWhid());
        g.setGoRdvolume(g.getGoRdvolume()-salesreturn.getSrVolume());//可以容积
        g.setGoUsevolume(g.getGoUsevolume()+salesreturn.getSrVolume());//已用容积
        int b = godownService.updateByPrimaryKey(g);
        if(a>0&&b>0){
            return renderSuccess("添加成功");
        }
        return renderError("添加失败");
    }

    @PostMapping("/select")
    @ResponseBody
    public Object select(SalesReturn sales, Integer page, Integer rows){
        PageInfo pageInfo = new PageInfo(page, rows);
        Map<String, Object> condition = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(sales.getSrName())) {
            String str = "%"+sales.getSrName()+"%";
            condition.put("srname",str );
        }
        if(StringUtils.isNotBlank(sales.getSrSipping())){
            condition.put("srsipping", sales.getSrSipping());
        }
        if (sales.getCreatedateStart() != null) {
            condition.put("startTime", sales.getCreatedateStart());
        }
        if (sales.getCreatedateEnd() != null) {
            condition.put("endTime", sales.getCreatedateEnd());
        }
        pageInfo.setCondition(condition);
        salesReturnService.selectAll(pageInfo);
        return pageInfo;
    }


    @GetMapping("/editPage")
    public String editPage(Integer id, Model model) {
        SalesReturn sales = salesReturnService.selectByPrimaryKey(id);
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(sales.getSrId());
        model.addAttribute("roleIds", ids);
        model.addAttribute("salesreturn", sales);
        return "putstorage/salesreturnEdit";
    }

    @PostMapping("/update")
    @ResponseBody
    public Object update(SalesReturn salesreturn,String time,String oldWhid,String oldVolume){
        salesreturn.setSrTime(updateTime(time));
        double oldv = Double.valueOf(oldVolume);
        int a =0,b=0;
        if(oldWhid == salesreturn.getSrWhid()){//仓库不变
            if(Double.valueOf(oldVolume)>salesreturn.getSrVolume()){//如果之前体积大于现在体积
                Godown g = godownService.selectByPrimaryKey(Integer.valueOf(salesreturn.getSrWhid()));
                g.setGoRdvolume(g.getGoRdvolume()+(oldv-salesreturn.getSrVolume()));//可用容积，可用容积 ＝　可用容积＋（之前体积－现在体积）
                g.setGoUsevolume(g.getGoUsevolume()-(oldv-salesreturn.getSrVolume()));//已用容积，已用容积＝　已用容积－（之前体积－现在体积）
                a = godownService.updateByPrimaryKey(g);
                b = salesReturnService.updateByPrimaryKey(salesreturn);
            }else if(Double.valueOf(oldVolume)<salesreturn.getSrVolume()){//如果之前体积小于现在体积
                Godown g = godownService.selectByPrimaryKey(Integer.valueOf(salesreturn.getSrWhid()));
                g.setGoRdvolume(g.getGoRdvolume()-(salesreturn.getSrVolume()-oldv));//可用容积，可用容积 ＝　可用容积-（现在体积-之前体积）
                g.setGoUsevolume(g.getGoUsevolume()+(salesreturn.getSrVolume()-oldv));//已用容积，已用容积＝　已用容积+（现在体积-之前体积）
                a = godownService.updateByPrimaryKey(g);
                b = salesReturnService.updateByPrimaryKey(salesreturn);
            }else{//如果之前现在体积相等
                b = salesReturnService.updateByPrimaryKey(salesreturn);
                if(b>0){
                    return renderSuccess("修改成功");
                }else{
                    return renderError("修改失败");
                }
            }
        }else{//仓库改变
            /**
             * 还原以前仓库
             */
            Godown go = godownService.selectByWhid(oldWhid);
            go.setGoRdvolume(go.getGoRdvolume()+oldv);//可用容积
            go.setGoUsevolume(go.getGoUsevolume()-oldv);//已用容积
            godownService.updateByPrimaryKey(go);
            /**
             * 修改现在仓库
             */
            Godown g = godownService.selectByWhid(salesreturn.getSrWhid());
            g.setGoRdvolume(g.getGoRdvolume()-salesreturn.getSrVolume());//可用容积，可用容积 ＝　可用容积-现在体积
            g.setGoUsevolume(g.getGoUsevolume()+salesreturn.getSrVolume());//已用容积，已用容积＝　已用容积+现在体积
            a = godownService.updateByPrimaryKey(g);
            b = salesReturnService.updateByPrimaryKey(salesreturn);
        }
        if(a>0&&b>0){
            return renderSuccess("修改成功");
        }
        return renderError("修改失败");
    }

    @PostMapping("/delete")
    @ResponseBody
    public Object delete(int id){
        int row = salesReturnService.deleteByPrimaryKey(id);
        if(row>0){
            return renderSuccess("删除成功");
        }
        return renderError("删除失败");
    }

    /**
     * 把字符串时间转成Date类型
     *
     * @param time
     * @return
     */
    private Date updateTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}