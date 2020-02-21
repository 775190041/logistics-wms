package com.nf.controller.base;

import com.nf.commons.result.ResourceVo;
import shiro.ShiroUser;
import com.nf.entity.User;
import com.nf.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * base controller
 * @author 骚哥神机
 */

public abstract class BaseController {

    @Autowired
    private UserService userService;

    /**
     * ajax失败
     * @param msg 失败的消息
     * @return {Object}
     */
    public Object renderError(String msg){
        ResourceVo result = ResourceVo.newBuilder().msg(msg).build();
        return result;
    }

    /**
     * ajax 成功
     * @return {Object}
     */
    public  Object renderSuccess(){
        ResourceVo result = ResourceVo.newBuilder().success(true).build();
        return result;
    }

    /**
     * ajax成功
     * @param msg 消息
     * @return {Object}
     */
    public Object renderSuccess(String msg) {
        ResourceVo result = ResourceVo.newBuilder().success(true).msg(msg).build();
        return result;
    }

    /**
     * ajax成功
     * @param obj 成功时的对象
     * @return {Object}
     */
    public Object renderSuccess(Object obj) {
        ResourceVo result = ResourceVo.newBuilder().success(true).obj(obj).build();
        return result;
    }

    /**
     * 获取当前登录用户对象
     * @return
     */
    public User getCurrentUser(){
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        User currentUser = userService.selectById(user.id);
        return currentUser;
    }

    /**
     * 获取当前登录用户ID
     * @return
     */
    public Long getUserId(){
        return this.getCurrentUser().getId();
    }



    @InitBinder
    public void initBinder(ServletRequestDataBinder binder){
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),true));
    }

}
