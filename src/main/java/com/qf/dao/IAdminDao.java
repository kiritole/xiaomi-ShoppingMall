package com.qf.dao;

import com.qf.pojo.Goods;
import com.qf.pojo.Orders;
import com.qf.pojo.Type;
import com.qf.pojo.User;

import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: IAdminDao
 * @Date: 2022/9/22 23:15
 */
public interface IAdminDao {

    /**
     * 通过姓名获取用户
     * @param username
     * @return
     */
    User getAdminByName(String username);

    /**
     * 添加商品分类
     * @param type
     * @return
     */
    int addGoodsType(Type type);

    /**
     * 根据查询条件搜索
     * @param sql 在service层拼接好的sql, 这里直接执行即可;
     * @return  查询结果, 集合.
     */
    List<Type> getTypeList(String sql);

    /**
     * 根据level删除
     * @param level
     * @return
     */
    int deleteByLevel(String level);


    /**
     * 修改商品类型
     * @param type
     * @return
     */
    int updateGoodsById(Type type);

    /**
     * 添加商品
     * @param goods
     * @return
     */
    int addGoods(Goods goods);

    /**
     * 根据username查询到用户
     * @param username
     * @return
     */
    User searchUser(String username);

    /**
     * 根据用户id很状态获取订单
     * @param uid
     * @param status
     * @return
     */
    List<Orders> showOrderByUidAndStatus(int uid,int status);

    /**
     * 根据status状态获取订单
     * @param status
     * @return
     */
    List<Orders> showOrderByUidAndStatus(int status);
}
