package com.nf.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.nf.commons.uilts.JsonUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色资源
 * @author 骚哥神机
 */
@Data
@TableName("role_resource")
public class RoleResource implements Serializable {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 资源id
     */
    @TableField(value = "resource_id")
    private Long resourceId;

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
