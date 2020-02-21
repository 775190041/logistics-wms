package com.nf.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 仓库表
 */
@Data
public class Godown implements Serializable {
    /**
     * gostatus仓库状态
     * 0:正常
     * 1:停用
     * 2:已满
     */
    private int goStatus;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    @TableField(value = "go_id")
    private Integer goId;

    /**
     * 仓库名称
     */
    @TableField(value = "go_whid")
    private String goWhid;

    /**
     * 管理员
     */
    @TableField(value = "go_p")
    private String goP;

    /**
     * 仓库容积
     */
    @TableField(value = "go_volume")
    private Double goVolume;

    /**
     * 以用容积
     */
    @TableField(value = "go_usevolume")
    private Double goUsevolume;

    /**
     * 可用容积
     */
    @TableField(value = "go_rdvolume")
    private Double goRdvolume;

    private List<User> userList;
}
