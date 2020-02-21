package com.nf.controller;


import com.nf.commons.uilts.PageInfo;
import com.nf.commons.uilts.StringUtils;
import com.nf.commons.uilts.TimeUtils;
import com.nf.controller.base.BaseController;
import com.nf.entity.Godown;
import com.nf.entity.Receiving;
import com.nf.entity.User;
import com.nf.entity.vo.UserVo;
import com.nf.service.GodownService;
import com.nf.service.ReceivingService;
import com.nf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/receiving")
public class ReceivingController  extends BaseController {

    @Autowired
    private ReceivingService receivingService;

    @Autowired
    private GodownService godownService;

    @Autowired
    private UserService userService;

    @GetMapping("/receivingPage")
    public String receivingPage(){
        return "putstorage/receivingPage";
    }

    @PostMapping("/select")
    @ResponseBody
    public Object select(Receiving receiving , Integer rows , Integer page ){

        PageInfo pageInfo = new PageInfo(page,rows);
        Map<String , Object> condition = new HashMap<>(16);
        if (StringUtils.isNotBlank(receiving.getrName())) {
            String str = "%"+receiving.getrName()+"%";
            condition.put("rname",str );
        }
        if(StringUtils.isNotBlank(receiving.getrSkumodel())){
            condition.put("rskumodel", receiving.getrSkumodel());
        }
        if (receiving.getCreatedateStart() != null) {
            condition.put("startTime", receiving.getCreatedateStart());
        }
        if (receiving.getCreatedateEnd() != null) {
            condition.put("endTime", receiving.getCreatedateEnd());
        }
        if(StringUtils.isNotBlank(receiving.getrSupplierid())){
            condition.put("rSupplierid", receiving.getrSupplierid());
        }
        if(StringUtils.isNotBlank(receiving.getrCrossflag())){
            condition.put("rCrossflag", receiving.getrCrossflag());
        }
        if(StringUtils.isNotBlank(receiving.getrDirectflag())){
            condition.put("rDirectflag", receiving.getrDirectflag());
        }
        pageInfo.setCondition(condition);
        receivingService.selectAll(pageInfo);
        return pageInfo;
    }

    /** 编辑页面*/
    @GetMapping("/editPage")
    public String editPage(Integer id , Model model){
        //根据id查询收货信息
        Receiving receiving =receivingService.selectByPrimaryKey(id);
        //根据收货管理员查询用户
        User user = userService.selectById(receiving.getrAdminid());
        //存入用户的登录名
        receiving.setAdminname(user.getLoginName());
        model.addAttribute("receiving", receiving);
        return "putstorage/receivingEdit";
    }

    @PostMapping("/update")
    @ResponseBody
    public Object update(Receiving receiving, String time,String oldWhid,String oldCrossflag,String oldVolume){
        //把字符串转换成Data类型。
        receiving.setrTime(TimeUtils.updateTime(time));
        //查询登录名得到用户数据
        UserVo user = userService.selectByLoginName(receiving.getAdminname());
        //把用户id储存收货入库管理员
        receiving.setrAdminid(user.getId().intValue());
        // 仓库容积(之前体积)
        double volume = Double.valueOf(oldVolume);
        int godownRow = 0 , receivingRow = 0 ;
        /**当仓库不变时*/
        if(receiving.getrWhid().equals(oldWhid)) {
            /**如果之前不越库，现在也不越库*/
            if("1".equals(oldCrossflag) && "1".equals(receiving.getrCrossflag())){
               //如果之前体积大于现在体积
                if(Double.valueOf(oldVolume) > receiving.getrNum()){
                     //根据物品存放的仓库查询 该仓库的信息。
                    Godown godown = godownService.selectByWhid(receiving.getrWhid());
                     //可用容积，可用容积 ＝　可用容积＋（之前体积－现在体积）
                    godown.setGoRdvolume(godown.getGoRdvolume() + (volume - receiving.getrNum()));
                     //已用容积，已用容积＝　已用容积－（之前体积－现在体积）
                    godown.setGoUsevolume(godown.getGoUsevolume() - (volume-receiving.getrNum()));
                     //修改仓库信息
                    godownRow  = godownService.updateByPrimaryKey(godown);
                    //修改收货入库物品信息。
                    receivingRow  = receivingService.updateByPrimaryKey(receiving);
                //如果之前体积小于现在体积
                }else if (Double.valueOf(oldVolume) < receiving.getrNum()) {
                    //根据物品存放的仓库查询 该仓库的信息。
                    Godown godown = godownService.selectByWhid(receiving.getrWhid());
                    //可用容积，可用容积 ＝　可用容积-（现在体积-之前体积）
                    godown.setGoRdvolume(godown.getGoRdvolume() - (receiving.getrNum() - volume));
                    //已用容积，已用容积＝　已用容积+（现在体积-之前体积）
                    godown.setGoUsevolume(godown.getGoUsevolume() + (receiving.getrNum() - volume));
                    //修改仓库信息
                    godownRow  = godownService.updateByPrimaryKey(godown);
                    //修改收货入库物品信息。
                    receivingRow  = receivingService.updateByPrimaryKey(receiving);
                //如果之前现在体积相等
                }else{
                    //修改收货入库物品信息。
                    receivingRow = receivingService.updateByPrimaryKey(receiving);
                    if (receivingRow > 0 ){
                        return renderSuccess("修改成功！");
                    }else{
                        return renderError("修改失败！");
                    }
                }
            /** 如果之前不越库，现在越库 */
            }else if("1".equals(oldCrossflag) && "0".equals(receiving.getrCrossflag())){
                //根据物品存放的仓库查询 该仓库的信息。
                Godown godown = godownService.selectByWhid(receiving.getrWhid());
                //可用容积，可用容积 ＝　可用容积+之前体积
                godown.setGoRdvolume(godown.getGoRdvolume() + volume);
                //已用容积，已用容积＝　已用容积-之前体积
                godown.setGoUsevolume(godown.getGoUsevolume() - volume);
                //修改仓库信息
                godownRow  = godownService.updateByPrimaryKey(godown);
                //修改收货入库物品信息。
                receivingRow  = receivingService.updateByPrimaryKey(receiving);
            /** 如果之前越库，现在不越库 */
            }else if("0".equals(oldCrossflag) && "1".equals(receiving.getrCrossflag())){
                //根据物品存放的仓库查询 该仓库的信息。
                Godown godown = godownService.selectByWhid(receiving.getrWhid());
                //可用容积，可用容积 ＝　可用容积-现在体积
                godown.setGoRdvolume(godown.getGoRdvolume()-receiving.getrNum());
                //已用容积，已用容积＝　已用容积+现在体积
                godown.setGoUsevolume(godown.getGoUsevolume()+receiving.getrNum());
                //修改仓库信息
                godownRow  = godownService.updateByPrimaryKey(godown);
                //修改收货入库物品信息。
                receivingRow  = receivingService.updateByPrimaryKey(receiving);
            /**如果之前越库，现在越库 */
            }else if("0".equals(oldCrossflag) && "0".equals(receiving.getrCrossflag())){
                //修改收货入库物品信息。
                receivingRow  = receivingService.updateByPrimaryKey(receiving);
                if(receivingRow > 0){
                    return renderSuccess("修改成功！");
                }else{
                    return renderError("修改失败！");
                }
            }else{
                System.err.println("------------------->>>>>>没有预想到的情况!!");
            }
        //仓库改变时
        }else {
            //如果之前不越库，现在也不越库
            if("1".equals(oldCrossflag) && "1".equals(receiving.getrCrossflag())){
                /** 还原以前仓库 */
                Godown godown = godownService.selectByWhid(oldWhid);
                //可用容积
                godown.setGoRdvolume(godown.getGoRdvolume() + volume);
                //已用容积
                godown.setGoUsevolume(godown.getGoUsevolume() - volume);
                godownService.updateByPrimaryKey(godown);
                /** 修改现在仓库 */
                Godown go = godownService.selectByWhid(receiving.getrWhid());
                //可用容积，可用容积 ＝　可用容积-现在体积
                go.setGoRdvolume(go.getGoRdvolume() - receiving.getrNum());
                //已用容积，已用容积＝　已用容积+现在体积
                go.setGoUsevolume(go.getGoUsevolume() + receiving.getrNum());
                godownRow = godownService.updateByPrimaryKey(go);
                receivingRow = receivingService.updateByPrimaryKey(receiving);
            //如果之前不越库，现在越库
            }else if("1".equals(oldCrossflag) && "0".equals(receiving.getrCrossflag())){
                /**还原以前仓库*/
                Godown go = godownService.selectByWhid(oldWhid);
                //可用容积
                go.setGoRdvolume(go.getGoRdvolume() + volume);
                //已用容积
                go.setGoUsevolume(go.getGoUsevolume() - volume);
                godownRow = godownService.updateByPrimaryKey(go);
                receivingRow = receivingService.updateByPrimaryKey(receiving);
            //如果之前越库，现在不越库
            }else if("0".equals(oldCrossflag) && "1".equals(receiving.getrCrossflag())){
                Godown g = godownService.selectByWhid(receiving.getrWhid());
                //可用容积，可用容积 ＝　可用容积-现在体积
                g.setGoRdvolume(g.getGoRdvolume()-receiving.getrNum());
                //已用容积，已用容积＝　已用容积+现在体积
                g.setGoUsevolume(g.getGoUsevolume()+receiving.getrNum());
                godownRow = godownService.updateByPrimaryKey(g);
                receivingRow = receivingService.updateByPrimaryKey(receiving);
            //如果之前越库，现在越库
            }else if("0".equals(oldCrossflag) && "0".equals(receiving.getrCrossflag())){
                receivingRow = receivingService.updateByPrimaryKey(receiving);
                if( receivingRow > 0){
                    return renderSuccess("修改成功！");
                }else{
                    return renderError("修改失败！");
                }
            }else{
                System.err.println("------------------->>>>>>没有预想到的情况!!");
            }
        }
        if (godownRow >0 && receivingRow > 0 ){
            return renderSuccess("修改成功!");
        }
        return renderError("修改失败！");
    }

    @PostMapping("/delete")
    @ResponseBody
    public Object delete(Integer id){
        int a = receivingService.deleteByPrimaryKey(id);
        if(a>0){
            return renderSuccess("删除成功");
        }
        return renderError("删除失败");
    }

}
