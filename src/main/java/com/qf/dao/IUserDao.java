package com.qf.dao;

import com.qf.pojo.User;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: IUserDao
 * @Date: 2022/9/19 21:09
 */
public interface IUserDao {

    /**
     * 根据userName查询User tb_user表当中
     * @param name
     * @return
     */
    User getUserByName(String name);

    /**
     * 用户注册
     * @param user
     * @return
     */
    int register(User user);

    /**
     * 激活账号
     * @param email
     * @param code
     * @return
     */
    int activate(String email,String code);

    /**
     * 通过id获取账号
     * @param id
     * @return
     */
    User getUserById(int id);
}
