package com.nf.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.nf.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserRole
 * @author 骚哥神机
 */
@Repository
public interface UserRoleMapper  extends AutoMapper<UserRole> {
    /** 查询当前用户角色的 用户和角色*/
    List<UserRole> selectUserRoleId(@Param("userId") Long userId);
    /** 查询当前用户角色表的 角色 */
    List<Long> selectRoleIdListByUserId(@Param("userId") Long userId);

}
