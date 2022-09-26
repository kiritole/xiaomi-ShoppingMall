package com.qf.service.impl;

import com.alibaba.fastjson2.JSON;
import com.qf.conditions.GoodsTypeCondition;
import com.qf.dao.IAdminDao;
import com.qf.dao.IUserDao;
import com.qf.dao.impl.AdminDaoImpl;
import com.qf.dao.impl.UserDaoImpl;
import com.qf.pojo.*;
import com.qf.service.IAdminService;
import com.qf.utils.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: AdminServiceImpl
 * @Date: 2022/9/22 23:22
 */
public class AdminServiceImpl implements IAdminService {
    private final IAdminDao adminDao = new AdminDaoImpl();
    private final IUserDao userDao = new UserDaoImpl();

    @Override
    public User getAdminByName(String username,String password) {
        //参数校验
        User admin = adminDao.getAdminByName(username);
        if (admin == null){
            return null;
        }

        if (admin.getRole() != 1){
            return null;
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        bCryptPasswordEncoder.matches(password,admin.getPassword());

        return admin;
    }

    @Override
    public boolean addGoodsType(Type type) {
        type.setLevel(new Random().nextInt(1000) + 100);
        //参数校验
        return adminDao.addGoodsType(type) == 1;
    }

    @Override
    public JsonResult searchByCondition(GoodsTypeCondition condition) {
        //封装要执行的sql
        String goodsLevel = condition.getGoodsLevel();
        String goodsName = condition.getGoodsName();

        String sql = "select * from tb_goods_type where 1=1";

        // 判断一下, 当前两个参数是否有值,如果有则拼接到sql当中,如果没有则不拼接到sql当中
        if (goodsName != null && goodsName.length() > 0){
            sql += " and name like '%" + goodsName + "%'";
        }

        if (goodsLevel != null && goodsLevel.length() > 0){
            sql += " and level =" + goodsLevel;
        }

        List<Type> typeList = adminDao.getTypeList(sql);

        return JsonResult.ok(0,"操作成功",typeList);
    }

    @Override
    public boolean deleteById(String level) {
        //参数校验
        return adminDao.deleteByLevel(level) == 1;
    }

    @Override
    public boolean updateGoodsById(Type type) {
        //参数校验

        return adminDao.updateGoodsById(type) == 1;
    }

    @Override
    public boolean addGoods(Goods goods) {

        // 参数校验
        return adminDao.addGoods(goods) == 1;
    }

    @Override
    public List<Orders> showOrderByUidAndStatus(String username,int status) {
        //参数校验
        //调用多个DML需要用到事务
        User user = null;
        List<Orders> ordersList = null;
        if (username != null && username.length() > 0){
            user = adminDao.searchUser(username);
            ordersList = adminDao.showOrderByUidAndStatus(user.getId(), status);
            for (Orders orders : ordersList) {
                orders.setUsername(username);
            }
        }else {
                ordersList = adminDao.showOrderByUidAndStatus(status);
                for (Orders orders : ordersList) {
                    int uid = orders.getUid();
                    User users = userDao.getUserById(uid);
                    orders.setUsername(users.getUsername());
                }
        }

        return ordersList;
    }
}
