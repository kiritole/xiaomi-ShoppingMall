package com.qf.service.impl;

import com.qf.dao.IUserDao;
import com.qf.dao.impl.UserDaoImpl;
import com.qf.pojo.JsonResult;
import com.qf.pojo.User;
import com.qf.service.IUserService;
import com.qf.utils.AccountValidatorUtil;
import com.qf.utils.EmailUtils;
import com.qf.utils.RandomUtils;
import com.qf.utils.bcrypt.BCryptPasswordEncoder;

import java.io.ByteArrayOutputStream;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: UserServiceImpl
 * @Date: 2022/9/19 21:30
 */
public class UserServiceImpl implements IUserService {
    private final IUserDao userDao = new UserDaoImpl();

    @Override
    public JsonResult getUserByName(String username) {
        // 参数校验
        // 参数校验
        // 参数校验
        // 参数校验
        // 参数校验
        // 判空处理
        if (username == null || username.length() == 0){
            return JsonResult.error(-1,"用户名称为空,赶紧填写");
        }
        if (username.length() >= 20){
            return JsonResult.error(-2,"用户名称太长了, 短一些哦");
        }

        User user = userDao.getUserByName(username);
        // 查询到了数据,也就是名称被占用了,不能用了.
        if (user != null){
            return JsonResult.error(-3,"用户名称已经被占用了,换一个吧!");
        }
        return JsonResult.ok(0, "用户名称可以用, 大哥!");
    }

    /**
     * 返回值处理
     *      1. 前端需要一个注册的异常信息,显示在页面上.
     *      2. 这个方法中没有办法直接存储域对象
     *              可以把HttpServletRequest, 传递到这个方法当中   --> 正确作法.
     *              上边作法差点意思,仅仅用传递过来的Request对象来保存一个域对象,没有必要,破坏了
     *              该方法的单一性.
     *      3. 返回值是一个对象,对象当中有三个字段
     *              code, 在controller进行业务判断
     *              msg, 拿到当前的提示信息, 直接在controller层, 把该存储到域对象当中
     *              data, 没有使用.
     * @param user
     * @return
     */
    @Override
    public JsonResult register(User user) {
        // 参数校验
        // 参数校验
        // 参数校验
        //      密码校验, 非空, 长度, 复杂度
        //      邮箱校验, 非空, 合法, 长度....
        String email = user.getEmail();
        if (email == null || email.length() == 0 || email.length() > 50){
            return JsonResult.error(-1,"邮箱地址不正确");
        }

        //合法邮箱
        if (!AccountValidatorUtil.isEmail(email)){
            return  JsonResult.error(-2, "不是一个合法的邮箱");
        }

        //处理一个密码，对铭文的密码进行加密处理
        String rawPassword = user.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(password);

        // 假设各个字段都验证通过了.调用Dao插入到数据库当中
        // 0, 未激活, 1, 表示已经激活 2,黑名单
        user.setStatus("0");
        // 0, 普通用户, 1, 表示管理员
        user.setRole(0);
        //激活码
        user.setCode(RandomUtils.createActive());
        int rows = userDao.register(user);

        //存储成功，注册成功
        if (rows != 1){
            return JsonResult.error(-3,"注册失败");
        }
        //注册成功之后，发送激活账号的邮件给用户的邮箱
        EmailUtils.sendEmail(user);
        return JsonResult.ok(0,"注册成功",user.getEmail());
    }

    @Override
    public JsonResult activate(String email, String code) {
        if (userDao.activate(email,code) != 1){
            return JsonResult.error(-1,"未知原因,激活失败, 稍后重试!");
        }
        return JsonResult.ok(0,"激活成功");
    }

    @Override
    public JsonResult login(String username, String password) {
        // 参数校验
        // 参数校验
        // 参数校验
        // 参数校验
        //          状态码, 提示文字,域对象的key, 写成常量,方便维护和管理.
        //          状态码, 提示文字,域对象的key, 写成常量,方便维护和管理.
        //          状态码, 提示文字,域对象的key, 写成常量,方便维护和管理.
        //          状态码, 提示文字,域对象的key, 写成常量,方便维护和管理.
        //          状态码, 提示文字,域对象的key, 写成常量,方便维护和管理.
        if (username == null || username.length() == 0 || username.length() >= 20){
            return JsonResult.error(-1,"用户名称填写错误");
        }
        if(password == null || password.length() == 0 || password.length() >= 20){
            return JsonResult.error(-1, "密码填写错误");
        }
        //根据用户名称去数据库查询出对应的人
        User user = userDao.getUserByName(username);

        // 没有从数据库查询出具体的数据,表示当前用户不存在.直接返回
        if (user == null){
            return JsonResult.error(-6, "用户名称或者密码错误!!!!");
        }

        // 1. 角色信息, 0: 普通用户 1: 管理员
        if (user.getRole() != 0) {
            return JsonResult.error(-2, "用户角色不对");
        }

        //2.账号是否激活了，status，0：未激活，1：表示激活了
        if (!user.getStatus().equals("1")){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    EmailUtils.sendEmail(user);
                }
            },"repeat send email: ").start();
            String email = user.getEmail();
            String suffix = "https://mail." + email.split("@")[1];
            String html = "<a href='" + suffix + "'>" + email + "</a>";
            String msg = "账号未激活, 已经发送激活邮件,去" + html + "激活!";
            return JsonResult.error(-3,msg);
        }

        //3.验证密码
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(password,user.getPassword())){
            return JsonResult.error(-4,"密码不对，请重试");
        }

        //登录成功
        return JsonResult.ok(0,"登陆成功",user);
    }
}
