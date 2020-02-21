package com.nf.entity.tree;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单栏
 */
@Data
public class Tree implements Serializable {
    private Long id;
    /**
     *  数控件的文本值
     */
    private String text;
    /**
     * 状态值 open,closed
     */
    private String state = "open";

    /**
     *  默认不选中
     */
    private boolean checked = false;

    /**
     * 地址
     */
    private Object attributes;

    /**
     * 子菜单
     */
    private List<Children> childrens;

    private List<Tree> children;
    /**
     * 图标
     */
    private String iconCls;

    /**
     *  父菜单
     */
    private String pid;


}
