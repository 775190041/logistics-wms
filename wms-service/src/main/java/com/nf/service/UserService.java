package com.nf.service;

import com.baomidou.framework.service.ISuperService;
import com.nf.commons.uilts.PageInfo;
import com.nf.entity.User;
import com.nf.entity.vo.UserVo;

import java.util.List;
import java.util.Map;

public interface UserService extends ISuperService<User> {
    /** 用户登录名 */
    List<User> selectByLoginName(UserVo userVo);
    /** 添加用户 */
    void insertByVo(UserVo userVo);

    /** 查询用户id*/
    UserVo selectByVoId(Long id);

    List<UserVo> selectUserByType(Map<String,Object> map);

    /** 用户修改*/
    void updateByVo(UserVo userVo);

    void updatePwdByUserId(Long id,String md5Hex);

    void selectDateGrid(PageInfo pageInfo);

    void deleteUserById(Long id);
    //登录名查询用户所有信息
    UserVo selectByLoginName(String user);

    /**查询用户类型是用户*/
    List<UserVo> selectByRole();

    int updateOutTime(User user);

}
