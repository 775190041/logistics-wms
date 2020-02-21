package com.nf.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 公告栏
 * @author 骚哥神机
 */
@Data
public class Notice {
    @TableId(type = IdType.AUTO)
    @TableField(value = "nid")
    private int nid;
    /**公告内容 */
    @TableField(value = "text")
    private String text;
    @TableField(value = "userid")
    /** 发布人 */
    private int userid;
    @TableField(value = "ntime")
    /** 发布时间 */
    private Date ntime;

    /** 发布状态 */
    @TableField(value = "nstatus")
    private int nstatus;

    private String username;

    private List<User> users;

    private String stringtime;
}
