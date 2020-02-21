package com.nf.controller;

import com.nf.controller.base.BaseController;
import com.nf.entity.Resource;
import com.nf.entity.User;
import com.nf.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 资源管理
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {
    @Autowired
    private ResourceService resourceService;

    /**
     * 菜单树
     * @return
     */
    @PostMapping("/tree")
    @ResponseBody
    public Object tree(){
        User currentUser = getCurrentUser();
        return resourceService.selectTree(currentUser);
    }

    /**
     * 二级资源树
     * @return
     */
    @RequestMapping("/allTree")
    @ResponseBody
    public Object allTree(){
        return resourceService.selectAllTree();
    }

    /**
     * 三级资源树
     * @return
     */
    @RequestMapping("/allTrees")
    @ResponseBody
    public Object allTrees(){
        return resourceService.selectAllTrees();
    }



    /**
     * 资源管理页面
     * @return
     */
    @GetMapping("/manager")
    public String manager(){
        return "admin/resource";
    }

    /**
     * 资源管理列表
     * @return
     */
    @PostMapping("treeGrid")
    @ResponseBody
    public Object treeGrid(){
        return resourceService.selectAll();
    }

    /**
     * 添加资源页
     * @return
     */
    @GetMapping("addPage")
    public String addPage(){
        return "admin/resourceAdd";
    }

    /**
     * 添加资源
     * @param resource
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Object add(Resource resource){
        resource.setCreateTime(new Date());
        resourceService.insert(resource);
        return renderSuccess("添加成功");
    }

    /**
     * 编辑资源页
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(Model model ,Long id ){
        //查询资源id
        Resource resource = resourceService.selectById(id);
        model.addAttribute("resource", resource);
        return  "admin/resourceEdit";
    }

    /**
     * 编辑资源
     * @param resource
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Object edit(Resource resource){
        resourceService.updateSelectiveById(resource);
        return  renderSuccess("编辑成功");
    }

    /**
     * 删除资源
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Long id){
        resourceService.deleteById(id);
        return renderSuccess("删除成功");
    }
}
