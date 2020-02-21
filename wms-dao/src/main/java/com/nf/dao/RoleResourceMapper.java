package com.nf.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.nf.entity.RoleResource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * RoleResource
 */
@Repository
public interface RoleResourceMapper  extends AutoMapper<RoleResource> {
    /**查询当前角色拥有的资源*/
    Long selectIdListByRoleId(@Param("id") Long id);

}
