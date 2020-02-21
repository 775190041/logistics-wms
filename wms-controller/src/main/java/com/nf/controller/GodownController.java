package com.nf.controller;

import com.nf.commons.uilts.PageInfo;
import com.nf.controller.base.BaseController;
import com.nf.entity.component.ComboBox4EasyUI;
import com.nf.entity.Godown;
import com.nf.entity.User;
import com.nf.entity.vo.UserVo;
import com.nf.service.GodownService;
import com.nf.service.UserService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 仓库controller
 *
 */
@Controller
@RequestMapping("/godown")
public class GodownController extends BaseController {

    @Autowired
    private GodownService godownService;

    @Autowired
    private UserService userService;

    /**
     * 仓库EasyUI下拉框
     * @return
     */
    @RequestMapping("/godownComboBox")
    @ResponseBody
    public List<ComboBox4EasyUI> listComboBox4EasyUi(){
        List<ComboBox4EasyUI> comboBox4EasyUIS = new ArrayList<>();
        //仓库EasyUI下拉框查询
        List<Godown> godowns = godownService.godownComboBox();
        for(Godown godown : godowns){
            //存入EasyUI下拉框实体类
            ComboBox4EasyUI comboBox4EasyUI = new ComboBox4EasyUI();
            //下拉框的value值 是用 valueField ：id获取的
            comboBox4EasyUI.setId(godown.getGoWhid());
            //下拉框text值 是用 textField: text 得到的
            comboBox4EasyUI.setText(godown.getGoWhid()+"(可用容积:"+godown.getGoRdvolume()+")");
            comboBox4EasyUIS.add(comboBox4EasyUI);
        }
        return comboBox4EasyUIS;
    }


    /**仓库页面 */
    @GetMapping("/page")
    public String page(){
        return "data/godown";
    }

    /** 分页查询 */
    @PostMapping("/dataGrid")
    @ResponseBody
    public Object dataGrid(Godown godown, User user, Integer page, Integer rows){
        PageInfo pageInfo  = new PageInfo(page,rows);
        Map<String, Object> condition = new HashMap<String, Object>(16);
        if (godown.getGoWhid() != null){
            condition.put("goWhid",godown.getGoWhid());
        }
        if (user.getName() != null){
            condition.put("name",user.getName());
        }
        pageInfo.setCondition(condition);
        godownService.selectDataGrid(pageInfo);
        return pageInfo;
    }

    /** 添加页面 */
    @GetMapping("/addPage")
    public String addPage(){
        return  "data/godownAdd";
    }

    /**
     * 添加仓库
     * @param godown
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public Object add(Godown godown){
        godown.setGoUsevolume(0.0D);
        godown.setGoRdvolume(godown.getGoVolume());
        //查询仓库下拉框
        List<Godown> godowns = godownService.godownComboBox();
        if (godowns.size() != 0){
            for (Godown go : godowns){
                if (godown.getGoWhid().equals(go.getGoWhid())){
                    return renderError("仓库名已存在，不能添加相同仓库！");
                }else{
                    int rows =godownService.insert(godown);
                    if (rows > 0){
                        return  renderSuccess("仓库添加成功！");
                    }
                }
            }
        }else {
            int rows = godownService.insert(godown);
            if (rows > 0){
                return renderSuccess("仓库添加成功!");
            }
        }
        return renderError("添加失败");
    }

    /**
     * 查询用户类型是用户
     * @return
     */
    @PostMapping("/userCombobox")
    @ResponseBody
    public Object getUserName(){
        List<UserVo> userList = userService.selectByRole();
        List<HashMap<String, String>> list = new ArrayList<>();
        if(userList.size() != 0){
            for(UserVo lists : userList){
                HashMap<String, String> map = new HashMap<>(16);
                map.put("userId", lists.getId().toString());
                map.put("userName", lists.getName());
                list.add(map);
            }
        }
        //转换为json数组
        JSONArray json = JSONArray.fromObject(list);
        return json;
    }

    /**
     * 启用或停用仓库
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public Object delete(Integer id , String status) {
        Godown godowns = new Godown();
        godowns.setGoId(id);
        if(status.equals("0")){
            godowns.setGoStatus(1);
            int a = godownService.updateStatus(godowns);
            if(a>0){
                return renderSuccess("操作成功");
            }
        }else if(status.equals("1")){
            godowns.setGoStatus(0);
            int a = godownService.updateStatus(godowns);
            if(a>0){
                return renderSuccess("操作成功");
            }
        }
        return renderSuccess("操作成败");
    }

    /**
     * 编辑页面
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/editPage")
    public String editPage(Model model, @RequestParam(value = "id") Integer id){
        Godown godown = godownService.selectById(id);
        model.addAttribute("godown",godown);
        return "data/godownEdit" ;
    }

    /**
     *  修改扩建仓库
     * @param godown
     * @param add
     * @return
     */
    @PostMapping("/edit")
    @ResponseBody
    public Object edit(Godown godown,@RequestParam(value = "add") Double add){
        Godown godowns = new Godown();
        godowns.setGoId(godown.getGoId());
        godowns.setGoVolume(godown.getGoVolume()+add);
        godowns.setGoRdvolume(godown.getGoRdvolume()+add);
        int row = godownService.updateStatus(godowns);
        if ( row > 0){
            return  renderSuccess("扩建成功！");
        }
        return  renderError("扩建失败!");
    }


    @SuppressWarnings("unused")
    @ResponseBody
    @GetMapping("godownComboBoxs")
    public List<ComboBox4EasyUI> listComboBox4EasyUi(String volume) {
        List<ComboBox4EasyUI> comboBox4EasyUIS = new ArrayList<>();
        List<Godown> godowns = godownService.godownComboBox();
        for (Godown godown : godowns) {
            ComboBox4EasyUI comboBox4EasyUI = new ComboBox4EasyUI();
            if("".equals(volume) || godown.getGoRdvolume()>Double.valueOf(volume)){
                comboBox4EasyUI.setId(String.valueOf(godown.getGoId()));
                comboBox4EasyUI.setText(godown.getGoWhid()+"(可用容积:"+godown.getGoRdvolume()+")");
                comboBox4EasyUIS.add(comboBox4EasyUI);
            }
        }
        if(comboBox4EasyUIS == null){
            ComboBox4EasyUI comboBox4EasyUI = new ComboBox4EasyUI();
            comboBox4EasyUI.setId(0+"");
            comboBox4EasyUI.setText("没有可用仓库");
            comboBox4EasyUIS.add(comboBox4EasyUI);
        }
        return comboBox4EasyUIS;
    }

}

