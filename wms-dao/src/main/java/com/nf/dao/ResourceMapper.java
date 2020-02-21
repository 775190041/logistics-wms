package com.nf.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.nf.entity.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Resource
 *
 * AutoMapper继承BaseMapper
 * 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能
 * @author 骚哥神机
 */
@Repository
public interface ResourceMapper  extends AutoMapper<Resource> {
    /** 查询所有父菜单 父类菜单得pid 是空值 */
    List<Resource> selectAllByTypeAndPidNull(@Param("resourceType") Integer resourceType);

    /** 查询所有子菜单 */
    List<Resource> selectAllByTypeAndPid(@Param("resourceType") Integer resourceType, @Param("pId") Long pId );

    /** 查询所有菜单 */
    List<Resource> selectAll();
}
