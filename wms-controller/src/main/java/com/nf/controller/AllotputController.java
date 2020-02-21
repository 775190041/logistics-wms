package com.nf.controller;

import com.nf.commons.uilts.PageInfo;
import com.nf.commons.uilts.StringUtils;
import com.nf.commons.uilts.TimeUtils;
import com.nf.controller.base.BaseController;
import com.nf.entity.Allotput;
import com.nf.entity.Godown;
import com.nf.service.AllotoutService;
import com.nf.service.AllotputService;
import com.nf.service.GodownService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/allotput")
@Slf4j
public class AllotputController extends BaseController {
    @Autowired
    private AllotputService allotputService;

    @Autowired
    private GodownService godownService;
    /**
     * 调拨入库单页面
     * @return
     */
    @GetMapping("/alloputPage")
    public String alloputPage(){
        return "putstorage/alloputPage";
    }

    /** 模糊分页查询 */
    @PostMapping("/select")
    @ResponseBody
    public Object select(Allotput allotput , Integer page , Integer rows){
        PageInfo pageInfo = new PageInfo(page,rows);
        Map<String,Object> condition = new HashMap<>(16);
        if (StringUtils.isNotBlank(allotput.getApName())){
            condition.put("apName",allotput.getApName());
            log.debug("----------------->  apName = " + allotput.getApName());
        }
        if (StringUtils.isNotBlank(allotput.getApSipping())){
            condition.put("apSipping",allotput.getApSipping());
            log.debug("----------------->  apSipping = " + allotput.getApSipping());
        }
        if (allotput.getCreatedateStart() != null) {
            condition.put("startTime", allotput.getCreatedateStart());
        }
        if (allotput.getCreatedateEnd() != null) {
            condition.put("endTime", allotput.getCreatedateEnd());
        }
        pageInfo.setCondition(condition);
        allotputService.selectAll(pageInfo);
        return pageInfo;
    }

    /** 编辑页面*/
    @GetMapping("/editPage")
    public String  editPage(Integer id , Model model){
        Allotput allotput = allotputService.selectByPrimaryKey(id);
        List<Integer> ids = new ArrayList<>();
        ids.add(allotput.getApId()) ;
        model.addAttribute("roleIds", ids);
        model.addAttribute("allotput", allotput);
        return "putstorage/allotputEdit";
    }

    /** 调拨单修改  */
    @PostMapping("/edit")
    @ResponseBody
    public Object update(Allotput allotput, String time , String oldWhid , String oldVolume){
        //修改日期时间格式
        allotput.setApTime(TimeUtils.updateTime(time));
        System.err.println("time = " + time);
        //转换数据
        double oldv = Double.valueOf(oldVolume);
        System.out.println("oldv = " + oldv);
        int godownRows = 0 , allotputRows = 0;
        /**仓库不变 */
        if (oldWhid.equals(allotput.getApWhid())){
            /** 如果之前体积大于现在体积 */
            if(Double.valueOf(oldVolume) > allotput.getApVolume()){
                Godown  godown = godownService.selectByWhid(allotput.getApWhid());
                //可用容积，可用容积 ＝　可用容积＋（之前体积－现在体积）
                godown.setGoRdvolume(godown.getGoRdvolume() +(oldv-allotput.getApVolume()));
                //已用容积，已用容积＝　已用容积－（之前体积－现在体积）
                godown.setGoUsevolume(godown.getGoUsevolume()-(oldv-allotput.getApVolume()));
                //修改仓库信息
                godownRows = godownService.updateByPrimaryKey(godown);
                //修改调拨信息
                allotputRows = allotputService.updateByPrimaryKey(allotput);
            /**如果之前体积小于现在体积 */
            }else if(Double.valueOf(oldVolume)<allotput.getApVolume()){
                Godown godown = godownService.selectByWhid(allotput.getApWhid());
                //可用容积，可用容积 ＝　可用容积-（现在体积-之前体积）
                godown.setGoRdvolume(godown.getGoRdvolume()-(allotput.getApVolume()-oldv));
                //已用容积，已用容积＝　已用容积+（现在体积-之前体积）
                godown.setGoUsevolume(godown.getGoUsevolume()+(allotput.getApVolume()-oldv));
                godownRows = godownService.updateByPrimaryKey(godown);
                allotputRows = allotputService.updateByPrimaryKey(allotput);
            /** 如果之前现在体积相等 */
            } else {
                allotputRows = allotputService.updateByPrimaryKey(allotput);
                if (allotputRows > 0){
                    return renderSuccess("修改成功！");
                }else{
                    return  renderError("修改失败！");
                }
            }
         /** 仓库改变 */
        }else{
            /**还原以前仓库*/
            Godown go = godownService.selectByWhid(oldWhid);
            //可用容积
            go.setGoRdvolume(go.getGoRdvolume()+oldv);
            //已用容积
            go.setGoUsevolume(go.getGoUsevolume()-oldv);
            //修改仓库
            godownService.updateByPrimaryKey(go);

            /** 修改现在仓库 */
            Godown g = godownService.selectByWhid(allotput.getApWhid());
            //可用容积，可用容积 ＝　可用容积-现在体积
            g.setGoRdvolume(g.getGoRdvolume()-allotput.getApVolume());
            //已用容积，已用容积＝　已用容积+现在体积
            g.setGoUsevolume(g.getGoUsevolume()+allotput.getApVolume());
            allotputRows = godownService.updateByPrimaryKey(g);
            godownRows = allotputService.updateByPrimaryKey(allotput);
        }
        if(allotputRows > 0 && godownRows >0){
            return  renderSuccess("修改成功!");
        }else {
            return renderError("修改失败!");
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public Object delete(int id){
        int rows = allotputService.deleteByPrimaryKey(id);
        log.info("111-----------------------------删除"+id);
        if (rows > 0){
            return  renderSuccess("删除成功！");
        }else{
            return renderError("删除失败!");
        }

    }
}
