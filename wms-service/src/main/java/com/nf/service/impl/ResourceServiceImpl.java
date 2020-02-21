package com.nf.service.impl;

import com.baomidou.framework.service.impl.SuperServiceImpl;

import com.nf.dao.RoleMapper;
import com.nf.dao.UserRoleMapper;
import com.nf.entity.tree.Children;
import com.nf.entity.tree.Tree;
import com.nf.dao.ResourceMapper;
import com.nf.entity.Resource;
import com.nf.entity.User;
import com.nf.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * SuperServiceImpl :
 *  第一个参数是  是mapper对象也就是dao
 *  第二个参数是  是实体
 * 注意 ：
 *  mapper对象 要和 .xml命名一致
 *  否则 AutoMapper 无法找到 xml文件
 */
@Service
public class ResourceServiceImpl extends SuperServiceImpl<ResourceMapper, Resource> implements ResourceService {
    /** 菜单 0 */
    private static final int RESOURCE_MENU = 0;
    /** 按钮 1 */
    private static final int RESOURCE_BUTTON = 1;

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private ResourceMapper resourceMapper;

    /**
     * 查询所有菜单
     * @return
     */
    @Override
    public List<Resource> selectAll() {
        return resourceMapper.selectAll();
    }

    /**
     *  全部树结构的集合
     * @return
     */
    @Override
    public List<Tree> selectAllTree() {

        /** 树菜单集合 */
        List<Tree> trees = new ArrayList<>();

        /** 查询所有父级菜单集合 */
        List<Resource> resources = resourceMapper.selectAllByTypeAndPidNull(RESOURCE_MENU);

        /**判断父级菜单集合是否是空值——————>>是 返回空 不是 返回父菜单集合 */
        resourcesList(resources);

        /** 树结构 */
        forResourceParent(trees, resources);

        /**返回树集合*/
        return trees;
    }

    /**
     *  循环所有菜单
     * @param trees 树集合
     * @param resources 父级菜单
     */
    private void forResourceParent(List<Tree> trees, List<Resource> resources) {
        for(Resource resourceParent : resources){
            Tree treeOne = new Tree();
            treeOne.setId(resourceParent.getId());
            treeOne.setText(resourceParent.getName());
            treeOne.setIconCls(resourceParent.getIcon());
            treeOne.setAttributes(resourceParent.getUrl());
            //查询所有子菜单集合
            List<Resource> resourcesSon = resourceMapper.selectAllByTypeAndPid(RESOURCE_MENU,resourceParent.getId());
            if (resourcesSon != null){
                //存入子菜单集合
                List<Children> tree = new ArrayList<>();
                /** 遍历子菜单集合 */
                forTree(resourcesSon,tree);
                //把子菜单存入到实体类
                treeOne.setChildrens(tree);
            }else {
                //父菜单为空则把状态关闭
                treeOne.setState("closed");
            }
            //把查询到的父菜单存入到树集合种
            trees.add(treeOne);
        }
    }

    /**
     *  查询所有子菜单
     * @param resourcesSon
     * @param tree 子菜单
     */
    private void forTree(List<Resource> resourcesSon,List<Children> tree) {
        for (Resource resourceTwo : resourcesSon) {
            Children treeTwo = new Children();
            treeTwo.setId(resourceTwo.getId());
            treeTwo.setText(resourceTwo.getName());
            treeTwo.setIconCls(resourceTwo.getIcon());
            treeTwo.setAttributes(resourceTwo.getUrl());
            //查询所有子菜单以下的菜单集合
            List<Resource> resourceSons =  resourceMapper.selectAllByTypeAndPid(RESOURCE_MENU,resourceTwo.getId());
            if(resourceSons != null){
                //存入所有子菜单以下的菜单
                List<Children> children = new ArrayList<>();
                /** 遍历子菜单以下的菜单*/
                forChildren(resourceSons,children);
                //把子菜单以下的菜单存入实体类
                treeTwo.setChildrenes(children);
                //把查询到的子菜单存入到树集合种
                tree.add(treeTwo);
            }else{
                //子菜单为空则把状态关闭
                treeTwo.setState("closed");
            }
        }
    }

    /**
     *  遍历子菜单以下的菜单
     * @param resourceSons
     * @param children
     */
    private void forChildren(List<Resource> resourceSons,List<Children> children){
        for (Resource resourceThree : resourceSons){
            Children treeThree = new Children();
            treeThree.setId(resourceThree.getId());
            treeThree.setText(resourceThree.getName());
            treeThree.setIconCls(resourceThree.getIcon());
            treeThree.setAttributes(resourceThree.getUrl());
            //把子菜单以下的菜单添加到树集合中
            children.add(treeThree);
        }
    }

    /**
     * 二级菜单
     * @return
     */
    @Override
    public List<Tree> selectAllTrees() {
        List<Tree> treeList = new ArrayList<>();
        List<Resource>resources = resourceMapper.selectAllByTypeAndPidNull(RESOURCE_MENU);
        resourcesList(resources);
        forResourceOne(treeList, resources);
        return treeList;
    }

    /**
     * @param treeList
     * @param resources
     */
    private void forResourceOne(List<Tree> treeList, List<Resource> resources) {
        for (Resource resourceOne : resources) {
            Tree treeOne = new Tree();
            treeOne.setId(resourceOne.getId());
            treeOne.setText(resourceOne.getName());
            treeOne.setIconCls(resourceOne.getIcon());
            treeOne.setAttributes(resourceOne.getUrl());
            List<Resource> resourceSon = resourceMapper.selectAllByTypeAndPid(RESOURCE_MENU, resourceOne.getId());
            if (resourceSon == null) {
                treeOne.setState("closed");
            } else {
                List<Tree> treeTwoList = new ArrayList<Tree>();
                forResourceTwo(resourceSon, treeTwoList);
                treeOne.setChildren(treeTwoList);
            }
            treeList.add(treeOne);
        }
    }

    /**
     * @param resourceSon
     * @param treeTwoList
     */
    private void forResourceTwo(List<Resource> resourceSon, List<Tree> treeTwoList) {
        for (Resource resourceTwo : resourceSon) {
            Tree treeTwo = new Tree();
            treeTwo.setId(resourceTwo.getId());
            treeTwo.setText(resourceTwo.getName());
            treeTwo.setIconCls(resourceTwo.getIcon());
            treeTwo.setAttributes(resourceTwo.getUrl());

            List<Resource> resourceSons = resourceMapper.selectAllByTypeAndPid(RESOURCE_BUTTON, resourceTwo.getId());
            if (resourceSons == null) {
                treeTwo.setState("closed");
            } else {
                List<Tree> treeThreeList = new ArrayList<Tree>();

                forResourceThree(resourceSons, treeThreeList);

                treeTwo.setChildren(treeThreeList);
            }
            treeTwoList.add(treeTwo);
        }
    }

    /**
     * @param resourceSons
     * @param treeThreeList
     */
    private void forResourceThree(List<Resource> resourceSons, List<Tree> treeThreeList) {
        for (Resource resourceThree : resourceSons) {
            Tree treeThree = new Tree();
            treeThree.setId(resourceThree.getId());
            treeThree.setText(resourceThree.getName());
            treeThree.setIconCls(resourceThree.getIcon());
            treeThree.setAttributes(resourceThree.getUrl());
            treeThreeList.add(treeThree);
        }
    }


    @Override
    public List<Tree>  selectTree(User currentUser) {
        List<Tree> trees = new ArrayList<>();
        /** 判断当前用户是否是董事会 */
        if (currentUser.getUserType().equals(99)){
            List<Resource> resourceFather = resourceMapper.selectAllByTypeAndPidNull(RESOURCE_MENU);
            /** 判断父级资源是否为空 */
            resourcesList(resourceFather);
            /** 遍历菜单 */
            for (Resource resourceOne : resourceFather){
                Tree treeOne = new Tree();
                treeOne.setId(resourceOne.getId());
                treeOne.setText(resourceOne.getName());
                treeOne.setIconCls(resourceOne.getIcon());
                treeOne.setAttributes(resourceOne.getUrl());
                /** 子菜单集合 */
                List<Resource> resourceSon = resourceMapper.selectAllByTypeAndPid(RESOURCE_MENU, resourceOne.getId());

                /** 判断子菜单是否为空 */
                if (resourceSon != null) {
                    List<Tree> tree = new ArrayList<Tree>();
                    for (Resource resourceTwo : resourceSon) {
                        Tree treeTwo = new Tree();
                        treeTwo.setId(resourceTwo.getId());
                        treeTwo.setText(resourceTwo.getName());
                        treeTwo.setIconCls(resourceTwo.getIcon());
                        treeTwo.setAttributes(resourceTwo.getUrl());
                        /** 添加子菜单 */
                        tree.add(treeTwo);
                    }
                    /** 子菜单添加到当前父菜单 */
                    treeOne.setChildren(tree);
                } else {
                    /** 关闭子菜单状态 */
                    treeOne.setState("closed");
                }
                /** 添到树集合*/
                trees.add(treeOne);
            }
            /**返回树集合*/
            return trees;
        }
        //普通用户 与 管理员
        /** 当前用户所管理的资源集合 */
        List<Resource> resourceIdList = new ArrayList<>();
        /** 获取用户管理资源 */
        forUserManagedResources(currentUser, resourceIdList);

        /** 遍历当前用户资源 */
        forResourceIdList(trees, resourceIdList);
        /** 树集合 */

        return trees ;
    }

    /**
     * 获取用户管理资源
     * @param currentUser 当前用户
     * @param resourceIdList 用户管理资源
     */
    private void forUserManagedResources(User currentUser, List<Resource> resourceIdList) {
        /** 查询当前用户角色表的 角色 */
        List<Long> roleIdList = userRoleMapper.selectRoleIdListByUserId(currentUser.getId());
        for (Long id : roleIdList){
            /** 查询当前角色可管理的所有资源和当前角色管理的菜单 0 */
            List<Resource> resourceIdLists = roleMapper.selectResourceIdListByRoleIdAndType(id);
            for (Resource resource : resourceIdLists){
                resourceIdList.add(resource);
            }
        }
    }

    /** 遍历所有资源*/
    private void forResourceIdList(List<Tree> trees, List<Resource> resourceIdList) {
        for (Resource resource : resourceIdList) {
            if (resource != null && resource.getPid() == null) {
                Tree treeOne = new Tree();
                treeOne.setId(resource.getId());
                treeOne.setText(resource.getName());
                treeOne.setIconCls(resource.getIcon());
                treeOne.setAttributes(resource.getUrl());
                List<Tree> tree = new ArrayList<Tree>();
                /** 遍历当前用户子级资源*/
                forTreeTwo(resourceIdList, resource, tree);

                treeOne.setChildren(tree);
                trees.add(treeOne);
            }
        }
    }

    /** 遍历当前用户子级*/
    private void forTreeTwo(List<Resource> resourceIdList, Resource resource, List<Tree> tree) {
        for (Resource resourceTwo : resourceIdList) {
            if (resourceTwo.getPid() != null && resource.getId().longValue() == resourceTwo.getPid().longValue()) {
                Tree treeTwo = new Tree();
                treeTwo.setId(resourceTwo.getId());
                treeTwo.setText(resourceTwo.getName());
                treeTwo.setIconCls(resourceTwo.getIcon());
                treeTwo.setAttributes(resourceTwo.getUrl());
                tree.add(treeTwo);
            }
        }
    }

    /**
     *  资源是否为空
     * @param resources value
     * @return  value || null
     */
    private List<Resource> resourcesList(List<Resource> resources){
        if(resources == null){
            return null;
        }
        return  resources;
    }
}
