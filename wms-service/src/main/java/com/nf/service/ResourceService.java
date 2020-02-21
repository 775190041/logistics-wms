package com.nf.service;

import com.baomidou.framework.service.ISuperService;
import com.nf.entity.tree.Tree;
import com.nf.entity.Resource;
import com.nf.entity.User;

import java.util.List;

public interface ResourceService extends ISuperService<Resource>  {

    List<Resource> selectAll();

    List<Tree> selectAllTree();

    List<Tree> selectAllTrees();
    /**
     *当前用户可访问得 树菜单
     * @param currentUser
     * @return
     */
    List<Tree> selectTree(User currentUser);


}
