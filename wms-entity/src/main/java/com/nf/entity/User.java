package com.nf.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.nf.commons.uilts.JsonUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @author 骚哥神机
 */
@Data
public class User implements Serializable {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 登陆名
     */
    @TableField(value = "login_name")
    private String loginName;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户类别
     */
    @TableField(value = "user_type")
    private Integer userType;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 最近登录时间
     */
    private Date outtime;

    /**
     * 对象序列化成json
     * @return
     */
    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
