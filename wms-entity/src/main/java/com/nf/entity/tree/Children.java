package com.nf.entity.tree;

import lombok.Data;

import java.util.List;

/**
 * 子菜单
 */
@Data
public class Children {

    private Long id;
    private String text;
    /**
     * open,closed
     */
    private String state = "open";
    private boolean checked = false;
    private Object attributes;
    private List<Children> childrenes;
    private String iconCls;
    private String pid;


}
