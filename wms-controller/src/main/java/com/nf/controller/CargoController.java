package com.nf.controller;

import com.google.inject.internal.cglib.core.$ClassInfo;
import com.nf.commons.uilts.PageInfo;
import com.nf.controller.base.BaseController;
import com.nf.entity.Cargo;
import com.nf.service.CargoService;
import com.nf.service.GodownService;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 货物控制器
 */
@Controller
@RequestMapping("/cargo")
public class CargoController extends BaseController {

    @Autowired
    private CargoService cargoService;

    @Autowired
    private GodownService godownService;

    /**
     * 货物页面
     * @return
     */
    @GetMapping("/cargoPage")
    public String cargoPage(){
        return "adjustment/cargo";
    }

    /**
     * 模糊分页查询
     * @param cargo
     * @param rows
     * @param page
     * @return
     */
    @RequestMapping("/select")
    @ResponseBody
    public Object select(Cargo cargo,Integer rows, Integer page){
        PageInfo pageInfo = new PageInfo(page,rows);
        Map<String,Object> condition = new HashMap<String,Object>();
        if(cargo.getcSkumodel() != null){
            condition.put("SKM",cargo.getcSkumodel());
        }
        if(cargo.getCreatedateStart() != null){
            condition.put("start" ,cargo.getCreatedateStart());
        }
        if(cargo.getCreatedateEnd() != null){
            condition.put("end" ,cargo.getCreatedateEnd());
        }
        pageInfo.setCondition(condition);
        cargoService.select(pageInfo);
        return pageInfo;
    }

    /**
     * 修改货物页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/cargoEditPage")
    public String cargoEditPage(Integer id, Model model){
        Cargo cargo = cargoService.selectByPrimaryKey(id);
        model.addAttribute("cargo",cargo);
        return "adjustment/cargoEdit";
    }

    /**
     * 修改货物
     * @param cargo
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public Object  update(Cargo cargo){
        int result = cargoService.updateByPrimaryKey(cargo);
        if(result > 0){
            return renderSuccess("修改成功!");
        }else{
            return renderError("修改失败!");
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public Object delete(int id){
        int result = cargoService.deleteByPrimaryKey(id);
        if (result > 0){
            return  renderSuccess("删除成功!");
        }else{
            return renderError("删除失败！");
        }
    }

    @GetMapping("/add")
    public String add(){
        return "adjustment/cargoAdd";
    }

    @PostMapping("/insert")
    @ResponseBody
    public Object insert(Cargo cargo){
        int result = cargoService.insert(cargo);
        if(result > 0 ){
            return renderSuccess("添加成功!");
        }else{
            return  renderError("添加失败!");
        }
    }
}
