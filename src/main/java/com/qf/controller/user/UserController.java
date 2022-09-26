package com.qf.controller.user;

import com.alibaba.fastjson2.JSON;
import com.qf.controller.BaseController;
import com.qf.pojo.JsonResult;
import com.qf.pojo.User;
import com.qf.service.IUserService;
import com.qf.service.impl.UserServiceImpl;
import com.qf.utils.Base64Utils;
import com.qf.utils.Constants;
import com.wf.captcha.utils.CaptchaUtil;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: UserController
 * @Date: 2022/9/19 21:05
 */
@WebServlet("/user")
public class UserController extends BaseController {
    private final IUserService userService = new UserServiceImpl();

    /**
     * 校验用户名称是否合法
     * @param request
     * @param response
     * @return
     */
    public String checkUserName(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //四件事
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");

        //2.获取参数
        String username = request.getParameter("username");

        //3.调用service层处理业务
        JsonResult result = userService.getUserByName(username);

        //4.根据service处理结果，跳转页面
        return JSON.toJSONString(result);
    }

    /**
     * 用户注册
     * @param request
     * @param response
     * @return
     */
    public String register(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.拿参数
        // 使用BeanUtil映射成一个Use对象
        User user = new User();
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3. 调用service, 处理业务逻辑
//        boolean success = userService.register(user);
        JsonResult result = userService.register(user);

        //4. 根据service处理结果进行页面跳转

        if (result.getCode() != 0){
            request.setAttribute("registerMsg",result.getMsg());
            return Constants.FORWARD + "register.jsp";
        }
        //解析出邮箱的地址和Https://mail拼接成一个完整的url
        // 在registerSuccess.jsp页面点击「现在激活」, 直接可以打开邮箱
        String email = (String) result.getData();
        String suffix = email.split("@")[1];
        String activeEmailAddress = "https://mail." + suffix;
        System.out.println("激活的邮箱地址是：" + activeEmailAddress);
        request.setAttribute("activeEmailAddress",activeEmailAddress);

        //注册成功
        return Constants.FORWARD +  "registerSuccess.jsp";


        //4. 根据service处理结果进行页面跳转
//        if (success){
//            return Constants.FORWARD + "index.jsp";
//        }
//        return Constants.FORWARD + "register.jsp";
    }

    /**
     * 激活账号
     *      从邮件当中点击激活链接, 调用这个方法,完成整个激活逻辑
     *              1. 确定激活是哪个账号
     *                      从url当中解析出参数, 确定一个唯一用户.
     *                      // 邮件内容当中提供的两个参数
     *                      Base64Utils.encode(user.getEmail())+"&c="+Base64Utils.encode(user.getCode());
     *
     *                      // 将转码之后字符串
     *                      e=MTc1Njg2NjU2NUBxcS5jb20=&c=MjAyMjA5MjAxMDExMDY5NjJhNA==
     *                      decode(串), 拿到邮箱和激活码.
     *              2. 修改数据库当中对应用户的status字段, 值更改为1即可.
     *              3. 激活完之后跳转页面
     * @param request
     * @param response
     * @return
     */
    public String activate(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        //四件事：
        //1.调协编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2. 获取参数, 从邮件当中点击的url当中获取.两个, 一个e,表示邮件地址, c, 表示激活码
        String base64Email = request.getParameter("e");
        String base64ActiveCode = request.getParameter("c");

        //将加密之后的邮箱地址和激活码解密
        String email = Base64Utils.decode(base64Email);
        String activeCode = Base64Utils.decode(base64ActiveCode);

        //3.调用service层完成整个激活逻辑
        JsonResult result = userService.activate(email, activeCode);

        //4.根据service处理业务结果，完成页面跳转
        if (result.getCode() != 0){
            request.setAttribute("msg",result.getMsg());
        }
        return Constants.FORWARD + "login.jsp";
    }

    /**
     * 生成验证码
     *      easy-captcha工具生成
     * @param request
     * @param response
     */
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 生成验证码,并且将验证存储到了session当中, 方便后续对验证码进行验证.
        CaptchaUtil.out(request, response);
    }

    /**
     * 登录业务处理
     * @param request
     * @param response
     * @return
     */
    public  String login(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        //4.四件事
        //设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession();
        //拿参数
        //      三个, 用户名称, 密码, 验证码
        // 1. 校验验证码的正确性. 「验证码只能用一回.只能消费一次」
        //      获取前端传递过来的验证码
        String captcha = request.getParameter("captcha");

        // 两个参数
        //  1. ver(captcha, request)
        //      captcha, 表示,前端传递过来将要进行验证的验证码
        //      request, 目的从当前Session取出生成的验证码
        //      表示验证没有通过
        if (!CaptchaUtil.ver(captcha,request)){
            request.setAttribute("loginMsg", "大哥,验证不对昂.... ");
            // 前提条件: 验证码只能使用一回,所以不管验证成功还是失败了,必须从当前Session清除掉已经生成的验证码.
            CaptchaUtil.clear(request);
            return Constants.FORWARD + "login.jsp";
        }

        // 验证通过
        // 前提条件: 验证码只能使用一回,所以不管验证成功还是失败了,必须从当前Session清除掉已经生成的验证码.
        CaptchaUtil.clear(request);

        //2.获取用户名称和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 3. 调用service层,处理整个的登录业务逻辑
        //      service处理登录逻辑的时候,由于数据库存储密码是经过加密的,而前端传递的是一个明文,
        //      不能直接从sql层面上,判断出是否登录成功.
        //          1. 根据用户名称,查询出数据库有没有对就的人,拿到当前用户名称对应的那条记录
        //          2. 前提条件: 用户名称是唯一的.「约定的」, 拿到一整行记录,包含了密码「加密的」
        //          3. 使用工具类当中提供的方法matches()验证密码的正确性.
        JsonResult result = userService.login(username, password);

        // 4. 根据登录结果跳转不同的页面即可
        // 登录失败的情况处理
        if (result.getCode() != 0){
            request.setAttribute("loginMsg",result.getMsg());
            return Constants.FORWARD + "login.jsp";
        }

        //登录成功的情况处理
        // 从JsonResult当中拿到将要存储的数据
        User user = (User) result.getData();
        session.setAttribute("userNow",user);
        return Constants.FORWARD + "index.jsp";
    }
    /**
     * 用户登出
     *      处理:
     *              存储到域对象所有信息,都清除掉.
     *              包括session
     *              request
     *              servletContext
     *              强烈建议域对象的key定义成常量.
     * @param request
     * @param response
     * @return
     */
    public String logout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        // 清除域对象当中的数据
        session.removeAttribute("userNow");
        // 验证码
        CaptchaUtil.clear(request);
        return Constants.FORWARD + "login.jsp";
    }
}
