package com.nf.entity.component;

import lombok.Data;

/**
 * EasyUI下拉框实体类
 * @author 骚哥神机
 */
@Data
public class ComboBox4EasyUI {

    private String id;

    private String text;

    private String whid;

    private boolean selected;

    public static void main(String[] args) {
        ComboBox4EasyUI  comboBox4EasyUI = new ComboBox4EasyUI();
        System.err.println("comboBox4EasyUI.getText() = " + comboBox4EasyUI.getText());
    }
}
