package com.nf.service.impl;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.nf.commons.uilts.BeanUtils;
import com.nf.commons.uilts.PageInfo;
import com.nf.commons.uilts.StringUtils;
import com.nf.dao.UserMapper;
import com.nf.dao.UserRoleMapper;
import com.nf.entity.User;
import com.nf.entity.UserRole;
import com.nf.entity.vo.UserVo;
import com.nf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<User> selectByLoginName(UserVo userVo) {
        User user = new User();
        user.setLoginName(userVo.getLoginName());
        EntityWrapper<User> wrapper = new EntityWrapper<User>(user);
        if (null != userVo.getId()) {
            wrapper.where("id != {0}", userVo.getId());
        }
        return this.selectList(wrapper);
    }

    @Override
    public void insertByVo(UserVo userVo) {
        User user = BeanUtils.copy(userVo, User.class);
        user.setCreateTime(new Date());
        //判断数据库操作是否成功
        this.insert(user);

        Long id = user.getId();
        String[] roles = userVo.getRoleIds().split(",");
        UserRole userRole = new UserRole();
        for (String string : roles) {
            userRole.setUserId(id);
            userRole.setRoleId(Long.valueOf(string));
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public UserVo selectByVoId(Long id) {
        return userMapper.selectUserVoById(id);
    }

    @Override
    public List<UserVo> selectUserByType(Map<String, Object> map) {

        return userMapper.selectUserByType(map);
    }

    @Override
    public void updateByVo(UserVo userVo) {
        User user = BeanUtils.copy(userVo, User.class);
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        }
        this.updateSelectiveById(user);

        Long id = userVo.getId();
        List<UserRole> userRoles = userRoleMapper.selectUserRoleId(id);
        if (userRoles != null && !userRoles.isEmpty()) {
            for (UserRole userRole : userRoles) {
                userRoleMapper.deleteById(userRole.getId());
            }
        }

        String[] roles = userVo.getRoleIds().split(",");
        UserRole userRole = new UserRole();
        for (String string : roles) {
            userRole.setUserId(id);
            userRole.setRoleId(Long.valueOf(string));
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public void updatePwdByUserId(Long userId, String md5Hex) {
        User user = new User();
        user.setId(userId);
        user.setPassword(md5Hex);
        this.updateSelectiveById(user);
    }

    @Override
    public void selectDateGrid(PageInfo pageInfo) {
        Page<UserVo> page = new Page<UserVo>(pageInfo.getNowPage(), pageInfo.getSize());
        List<UserVo> list = userMapper.selectUserVoPage(page, pageInfo.getCondition());
        pageInfo.setRows(list);
        pageInfo.setTotal(page.getTotal());
    }

    @Override
    public void deleteUserById(Long id) {
        this.deleteById(id);
        List<UserRole> userRoles = userRoleMapper.selectUserRoleId(id);
        if (userRoles != null && !userRoles.isEmpty()) {
            for (UserRole userRole : userRoles) {
                userRoleMapper.deleteById(userRole.getId());
            }
        }
    }

    @Override
    public UserVo selectByLoginName(String user) {
        return userMapper.selectByLoginName(user);
    }

    /** 查询用户类型是用户 */
    @Override
    public List<UserVo> selectByRole() {
        List<UserVo> userList = userMapper.selectByRole();
        return userList;
    }

    @Override
    public int updateOutTime(User user) {
        return userMapper.updateOutTime(user);
    }



/*

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public List<User> selectByLoginName(UserVo userVo) {
        User user = new User();
        user.setLoginName(userVo.getLoginName());
        */
/** 对象封装操作类，定义T-SQL语法 *//*

        EntityWrapper<User> wrapper = new EntityWrapper<>(user);
        if (null != userVo.getId()){
            wrapper.where("id != {0} ",userVo.getId());
        }
        return this.selectList(wrapper);
    }

    */
/**
     * 添加用户角色
     * @param userVo
     *//*

    @Override
    public void insertByVo(UserVo userVo) {
//        //copy 对象属性到另一个对象
//        User user =  BeanUtils.copy(userVo,User.class);
//        user.setCreateTime(new Date());
//        //判断数据库操作是否成功 数据库操作返回影响条数
//        this.insert(user);
//        //用户id
//        Long id = user.getId();
//        //添加用户角色
//        insertUserRole(userVo, id);

        User user = BeanUtils.copy(userVo, User.class);
        user.setCreateTime(new Date());
        //判断数据库操作是否成功
        this.insert(user);

        Long id = user.getId();
        String[] roles = userVo.getRoleIds().split(",");
        UserRole userRole = new UserRole();
        for (String string : roles) {
            userRole.setUserId(id);
            userRole.setRoleId(Long.valueOf(string));
            userRoleMapper.insert(userRole);
        }
    }

    */
/**
     * 得到id查询用户的信息以及角色类型
     * @param id
     * @return
     *//*

    @Override
    public UserVo selectByVoId(Long id) {
        return userMapper.selectUserVoById(id) ;
    }

    @Override
    public List<UserVo> selectUserByType(Map<String, Object> map) {
        return userMapper.selectUserByType(map);
    }

    @Override
    public void updateByVo(UserVo userVo) {
        User user = BeanUtils.copy(userVo, User.class);
        if (StringUtils.isBlank(user.getPassword())){
            user.setPassword(null);
        }
        this.updateSelectiveById(user);
        Long id = userVo.getId();
        deleteUserRoleId(id);
        insertUserRole(userVo, id);
    }

    */
/**
     *  添加用户角色信息
     * @param userVo
     * @param id
     *//*

    private void insertUserRole(UserVo userVo, Long id) {
        String[] roles = userVo.getRoleIds().split(",");
        UserRole userRole = new UserRole();
        for (String string : roles) {
            userRole.setUserId(id);
            userRole.setRoleId(Long.valueOf(string));
            userRoleMapper.insert(userRole);
        }
    }

    //根据id删除用户角色信息
    private void deleteUserRoleId(Long id) {
        // 查询当前用户角色的 用户和角色
        List<UserRole> userRoles = userRoleMapper.selectUserRoleId(id);
        if (userRoles != null && !userRoles.isEmpty()){
            for (UserRole userRole : userRoles){
                userRoleMapper.deleteById(userRole.getId());
            }
        }
    }

    @Override
    public void updatePwdByUserId(Long id, String md5Hex) {
        User user = new User();
        user.setId(id);
        user.setPassword(md5Hex);
        this.updateSelectiveById(user);
    }

    @Override
    public void selectDateGrid(PageInfo pageInfo) {
        Page<UserVo> page = new Page<>(pageInfo.getNowPage(),pageInfo.getSize());
        List<UserVo> userVoList  = userMapper.selectUserVoPage(page,pageInfo.getCondition());
        pageInfo.setRows(userVoList);
        pageInfo.setTotal(page.getTotal());
        for (UserVo vo :userVoList) {
            System.err.println("vo = " + vo.getRolesList());
        }
    }


    @Override
    public void deleteUserById(Long id) {
        this.deleteById(id);
        */
/** 查询当前用户角色的 用户和角色*//*

        deleteUserRoleId(id);
    }

    */
/**
     * 登录名查询用户所有信息
     * @param user
     * @return
     *//*

    @Override
    public UserVo selectByLoginName(String user) {
        return userMapper.selectByLoginName(user) ;
    }

    */
/**
     *  查询用户角色（超级管理员员）
     * @return
     *//*

    @Override
    public List<UserVo> selectByRole() {
        return  userMapper.selectByRole();
    }

    */
/**
     * 最近登录时间
     * @param user
     * @return
     *//*

    @Override
    public int updateOutTime(User user) {
        return userMapper.updateOutTime(user);
    }
*/

}
