package shiro;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 */
public class ShiroUser implements Serializable {

    public Long id;
    public String loginName;
    public String name;
    public List<Long> roleList;

    public ShiroUser(Long id, String loginName, String name, List<Long> roleList) {
        this.id = id;
        this.loginName = loginName;
        this.name = name;
        this.roleList = roleList;
    }

    public String getName() {
        return name;
    }

    /**
     * 作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return loginName;
    }
}