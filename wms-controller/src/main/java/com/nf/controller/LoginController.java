package com.nf.controller;

import com.nf.commons.uilts.DigestUtils;
import com.nf.commons.uilts.StringUtils;
import com.nf.controller.base.BaseController;
import com.nf.entity.User;
import com.nf.entity.vo.UserVo;
import com.nf.service.UserService;
import org.apache.maven.model.Model;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *  登录退出
 */
@Controller
public class LoginController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    /**
     * 首页
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        logger.info("GET 请求登录");
        //判断该用户是否登录了
        if(SecurityUtils.getSubject().isAuthenticated()){
            //是重定向到首页
            return "redirect:/index";
        }
        //该用户没有登录回到登录页面。
        return "login";
    }

    /**
     * POST 登录 shiro 写法
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public  Object loginPost(String username, String password, Model model, HttpServletRequest req){
        logger.info("Post请求登录");
        if (StringUtils.isBlank(username)) {
            return renderError("用户名不能为空");
        }
        if (StringUtils.isBlank(password)) {
            return renderError("密码不能为空");
        }
        /**
         * ##获取当前登录用户的信息(获取Subject单例对象)
         * 是每个请求创建一个Subject,
         * 并保存到ThreadContext的resources（ThreadLocal<Map<Object, Object>>）变量中，
         * 也就是一个http请求一个subject,并绑定到当前线程。每次请求都会重新设置Session和Principals,
         * 如果是web工程，直接从web容器获取httpSession，然后再从httpSession获取Principals，
         * 本质就是从cookie获取用户信息，然后每次都设置Principal，这样就知道是哪个用户的请求，
         * 并只得到这个用户有没有人认证成功，--本质：依赖于浏览器的cookie来维护session的
         */
        Subject subject = SecurityUtils.getSubject();
        /**
         * 把用户名和密码封装为 UsernamePasswordToken 对象
         * 使用用户的登录信息创建令牌 token可以理解为用户令牌
         * UserNamePasswordToken类的主要属性如下及用户名，密码，是否记住token以及验证来源的host主机地址.
         * 密码存储为char[]而不是字符串
         */
        UsernamePasswordToken token = new UsernamePasswordToken(username, DigestUtils.md5Hex(password).toCharArray());
        /**
         * 是否记住我
         * Shiro 提供了记住我（RememberMe）的功能
         * 如果登录成功 RememberMe 的Cookie 写到客户端并保存下来
         */
        token.setRememberMe(false);
        return login(username, req, subject, token);
    }

    /**
     *  执行登录等操作
     * @param username
     * @param req
     * @param subject
     * @param token
     * @return
     */
    private Object login(String username, HttpServletRequest req, Subject subject, UsernamePasswordToken token) {
        try {
            /**
             * 执行登录
             */
            subject.login(token);
            /**
             * 获取的是Servlet容器对象
             */
            ServletContext context = req.getSession().getServletContext();
            UserVo userVo = userService.selectByLoginName(username);
            System.err.println("userVo = " + userVo);
            /**
             * 登录时间写入servlet
             */
            dataStorageServlet(context, userVo);
            User users = new User();
            users.setOuttime(new Date());
            users.setId(userVo.getId());
            userService.updateOutTime(users);
            return renderSuccess();
        }catch (UnknownAccountException e) {
            throw new RuntimeException("账号不存在！", e);
        } catch (DisabledAccountException e) {
            throw new RuntimeException("账号未启用！", e);
        } catch (IncorrectCredentialsException e) {
            throw new RuntimeException("密码错误！", e);
        } catch (Throwable e) {
            throw new RuntimeException("未知错误,请联系管理员！", e);
        }
    }

    /**
     * 把用户登录时间存入上下文
     * @param context servlet对象
     * @param userVo 对象
     * @return
     */
    private void dataStorageServlet(ServletContext context, UserVo userVo) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (userVo.getOuttime() == null) {
            userVo.setOuttime(new Date());
            context.setAttribute("time", simpleDateFormat.format(userVo.getOuttime()));
        } else {
            context.setAttribute("time", simpleDateFormat.format(userVo.getOuttime()));
        }
    }


    /**
     * 退出登录
     * @return
     */
    @PostMapping("/logout")
    @ResponseBody
    public Object logout(){
        logger.info("POST登出");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return  renderSuccess();
    }

    /**
     * 未授权
     */
    //    @GetMapping("/unauth")
//    public String unauth(){
//        if (SecurityUtils.getSubject().isAuthenticated() == false){
//            return "redirect:/login";
//        }
//        return  "unauth";
//    }

}

