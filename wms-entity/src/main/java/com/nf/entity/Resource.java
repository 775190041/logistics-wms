package com.nf.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nf.commons.uilts.JsonUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 资源界面
 * @author 骚哥神机
 */
@Data
public class Resource implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @TableField(value = "id")
    private Long id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源路径
     */
    private String url;

    /**
     * 资源图标
     * @JsonProperty JSON对象字段用于属性的名称
     */
    @JsonProperty(value = "icon")
    private String icon;

    /**
     * 父资源id
     */
    private Long pid;

    /**
     *  排序
     */
    private Integer seq;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 资源类别
     * @TableField 数据库名称
     */
    @TableField(value = "resource_type")
    private Integer resourceType;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 对象序列化成json
     * @return
     */
    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
