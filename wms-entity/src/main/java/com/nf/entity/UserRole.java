package com.nf.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.nf.commons.uilts.JsonUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色表
 * @author 骚哥神机
 */
@Data

@TableName("user_role")
public class UserRole implements Serializable {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    @TableField(value = "id")
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    private Long roleId;

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
