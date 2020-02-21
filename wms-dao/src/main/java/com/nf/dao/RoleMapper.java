package com.nf.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.nf.entity.Resource;
import com.nf.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Role
 * @author 骚哥神机
 */
@Repository
public interface RoleMapper extends AutoMapper<Role> {

    /** 查询role所有数据 */
    List<Role> selectAll();
    /** 查询当前角色拥有的资源 */
    List<Long> selectResourceIdListByRoleId(@Param("id") Long id );
    /** 查询当前角色可管理的所有资源和当前角色管理的菜单 0 */
    List<Resource> selectResourceIdListByRoleIdAndType(@Param("id") Long id);
    /** 查询当前角色可管理的url地址*/
    List<Map<Long,String>> selectResourceListByRoleId(@Param("id") Long id);

    List<Role> selectRoleList(Pagination page, @Param("sort") String sort, @Param("order") String order);

}
