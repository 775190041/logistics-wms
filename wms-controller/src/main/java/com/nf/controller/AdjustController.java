package com.nf.controller;

import com.nf.commons.uilts.PageInfo;
import com.nf.controller.base.BaseController;
import com.nf.entity.Adjust;
import com.nf.service.AdjustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/adjust")
public class AdjustController extends BaseController {

    @Autowired
    private AdjustService adjustService;

    /**
     * 跳转到库存调整单页面
     * @return
     */
    @GetMapping("/page")
    public String page(){
        return "adjustment/adjust";
    }

    /**
     *  分页查询
     * @param adjust
     * @param page 当前页
     * @param rows 每页显示的记录数
     * @return
     */
    @PostMapping("/select")
    @ResponseBody
    public Object select(Adjust adjust , Integer page, Integer rows){
        PageInfo pageInfo = new PageInfo(page,rows);
        //条件查询
        Map<String,Object> map = new HashMap<>(16);
        if (adjust.getJSkumodel() != null){
            map.put("num",adjust.getJSkumodel());
            System.err.println("adjust = " + adjust.getJSkumodel());
        }
        if (adjust.getCreatedateStart() != null){
            map.put("startTime",adjust.getCreatedateStart());
        }
        if (adjust.getCreatedateEnd() != null){
            map.put("endTime",adjust.getCreatedateEnd());
        }
        //存入条件
        pageInfo.setCondition(map);
        //执行查询
        adjustService.select(pageInfo);
        //返回对象
        return pageInfo;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Object delete(Integer id){
        int result = adjustService.deleteByPrimaryKey(id);
        System.err.println("result = " + result);
        if (result > 0 ){
            return renderSuccess("删除成功");
        }
        return renderSuccess("删除失败！");
    }
}
