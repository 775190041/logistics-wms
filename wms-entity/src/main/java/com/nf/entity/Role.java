package com.nf.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.nf.commons.uilts.JsonUtils;
import lombok.Data;

/**
 *  角色
 * @author 骚哥神机
 */
@Data
public class Role {
    /**
     *主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 角色名
     */
    private String name;

    /**
     * 排序号
     */
    private Integer seq ;

    /**
     * 简介
     */
    private String description;

    /**
     * 状态
     */
    private Integer status;

    /**
     *  对象序列化成json
     * @return
     */
    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
