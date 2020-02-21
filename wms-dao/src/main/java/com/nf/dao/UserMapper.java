package com.nf.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.nf.entity.User;
import com.nf.entity.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * User
 * @author 骚哥神机
 */
@Repository
public interface UserMapper  extends AutoMapper<User> {
    /** 得到id查询用户的信息以及角色类型 */
    UserVo selectUserVoById(@Param("id") Long id) ;
    /** 查询用户所有信息和得到用户角色所有类型并分页 */
    List<UserVo> selectUserVoPage(Pagination page, Map<String,Object> params);
    /** 登录名查询用户所有信息 */
    UserVo selectByLoginName(String loginName);
    /** 查询用户类型是否是超级管理员 */
    List<UserVo> selectUserByType(Map<String,Object> map);
    /** 查询用户类型是用户  */
    List<UserVo> selectByRole();
    /**  修改最近登录时间 */
    int updateOutTime(User user);
}
