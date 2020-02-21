package com.nf.service;

import com.baomidou.framework.service.ISuperService;
import com.nf.commons.uilts.PageInfo;
import com.nf.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService extends ISuperService<Role> {

    List<Long> selectRoleIdListByUserId(Long userId);

    List<Map<Long, String>> selectRoleResourceListByRoleId(Long roleId);

    void selectDataGrid(PageInfo pageInfo);

    Object selectTree();

    List<Long> selectResourceIdListByRoleId(Long id);

    void updateRoleResource(Long id, String resourceIds);

}
