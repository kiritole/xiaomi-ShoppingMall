package com.qf.service;

import com.qf.pojo.JsonResult;
import com.qf.pojo.User;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: IUserService
 * @Date: 2022/9/19 21:26
 */
public interface IUserService {

    /**
     * 根据用户名称上tb_user表当中查询用户
     * @param username
     * @return
     */
    JsonResult getUserByName(String username);

    /**
     * 用户注册
     * @param user
     * @return
     */
    JsonResult register(User user);

    /**
     * 激活账号
     * @param email
     * @param code
     * @return
     */
    JsonResult activate(String email, String code);

    /**
     * 登陆业务处理
     * @param username
     * @param password
     * @return
     */
    JsonResult login(String username,String password);
}
