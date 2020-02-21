package com.nf.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nf.entity.Role;
import com.nf.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserVo implements Serializable {

    private Long id;

    private String loginName;

    private String name;

    @JsonIgnore
    private String password;

    private Integer sex;

    private Integer age;

    private Integer userType;

    private Integer status;

    private Date createTime;

    private String phone;

    private List<Role> rolesList;

    private String roleIds;

    private Date outtime;

    private Date createdateStart;
    private Date createdateEnd;


}
