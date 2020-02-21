package com.nf.service.impl;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.plugins.Page;
import com.nf.commons.uilts.PageInfo;
import com.nf.dao.RoleMapper;
import com.nf.dao.RoleResourceMapper;
import com.nf.dao.UserRoleMapper;
import com.nf.entity.Role;
import com.nf.entity.RoleResource;
import com.nf.entity.tree.Tree;
import com.nf.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * role
 */

@Service
public class RoleServiceImpl extends SuperServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Override
    public List<Long> selectRoleIdListByUserId(Long userId) {
        return userRoleMapper.selectRoleIdListByUserId(userId);
    }

    @Override
    public List<Map<Long, String>> selectRoleResourceListByRoleId(Long roleId) {
        return roleMapper.selectResourceListByRoleId(roleId);
    }

    @Override
    public void selectDataGrid(PageInfo     pageInfo) {
        Page<Role> page = new Page<Role>(pageInfo.getNowPage(), pageInfo.getSize());
        List<Role> list = roleMapper.selectRoleList(page, pageInfo.getSort(), pageInfo.getOrder());
        //显示的记录
        pageInfo.setRows(list);
        //总页数
        pageInfo.setTotal(page.getTotal());
    }

    @Override
    public Object selectTree() {
        List<Tree> trees = new ArrayList<Tree>();
        List<Role> roles = roleMapper.selectAll();
        for (Role role : roles) {
            Tree tree = new Tree();
            tree.setId(role.getId());
            tree.setText(role.getName());

            trees.add(tree);
        }
        return trees;
    }

    @Override
    public List<Long> selectResourceIdListByRoleId(Long id) {
        return roleMapper.selectResourceIdListByRoleId(id);
    }


    @Override
    public void updateRoleResource(Long roleId, String resourceIds) {
        //先删除后添加,有点爆力
        RoleResource roleResource = new RoleResource();
        roleResource.setRoleId(roleId);
        //删除该角色资源
        roleResourceMapper.deleteSelective(roleResource);
        //判断该授权资源是否为空
        if ( "".equals(resourceIds) ){
            //该角色资源授权清零
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(Long.valueOf(0));
            roleResourceMapper.insert(roleResource);
        }else {
            //遍历角色授权资源
            String[] resourceIdArray = resourceIds.split(",");
            for (String resourceId : resourceIdArray) {
                roleResource = new RoleResource();
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(Long.parseLong(resourceId));
                //添加授权
                roleResourceMapper.insert(roleResource);
            }
        }

    }

}
