package com.nf.commons.uilts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 分页实体类 (结合JQuery EasyUI)
 * @SuppressWarnings  注解主要用在取消一些编译器产生的警告对代码左侧行列的遮挡，
 *  有时候这会挡住我们断点调试时打的断点。
 * rawtypes 抑制单类型的警告
 */
@Data
@SuppressWarnings("rawtypes")
public class PageInfo {
    /** 默认显示的记录数 */
    private final static int PAGESIZE = 10;
    /** 总记录 */
    private int total;
    /** 显示的记录 */
    private List rows;

    @JsonIgnore
    private int from;
    @JsonIgnore
    private int size;
    /** 当前页 */
    @JsonIgnore
    private int nowPage;
    /** 每页显示的记录数 */
    @JsonIgnore
    private int pageSize;
    /** 查询条件 */
    @JsonIgnore
    private Map<String, Object> condition;

    /** 排序字段 */
    @JsonIgnore
    private String sort = "seq";
    /** asc，desc mybatis Order 关键字 */
    @JsonIgnore
    private String order = "asc";

    public PageInfo() {}

    //构造方法
    public PageInfo(int nowPage, int pageSize) {
        //计算当前页  
        if (nowPage < 0) {
            this.nowPage = 1;
        } else {
            //当前页
            this.nowPage = nowPage;
        }
        //记录每页显示的记录数  
        if (pageSize < 0) {
            this.pageSize = PAGESIZE;
        } else {
            this.pageSize = pageSize;
        }
        //计算开始的记录和结束的记录  
        this.from = (this.nowPage - 1) * this.nowPage;
        this.size = this.pageSize;
    }

    // 构造方法
    public PageInfo(int nowPage, int pageSize, String sort, String order) {
        // 计算当前页  
        if (nowPage < 0) {
            this.nowPage = 1;
        } else {
            // 当前页
            this.nowPage = nowPage;
        }
        // 记录每页显示的记录数  
        if (pageSize < 0) {
            this.pageSize = PAGESIZE;
        } else {
            this.pageSize = pageSize;
        }
        // 计算开始的记录和结束的记录  
        this.from = (this.nowPage - 1) * this.pageSize;
        this.size = this.pageSize;
        // 排序字段，正序还是反序
        this.sort = sort;
        this.order = order;
    }

}
