package com.qf.service;

import com.qf.conditions.GoodsTypeCondition;
import com.qf.pojo.*;

import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: IAdminService
 * @Date: 2022/9/22 23:20
 */
public interface IAdminService {

    /**
     * 返回管理员用户数据
     * @param username
     * @return
     */
    User getAdminByName(String username,String password);


    /**
     * 添加商品分类
     * @param type
     * @return
     */
    boolean addGoodsType(Type type);

    /**
     * 搜索实现
     *      参数校验
     *      拼接sql
     *           dao层不做任务业务逻辑,拼接操作. 把所有操作都放到了service层,交给dao层就是一个
     *           拼接好的sql语句,直接去直接
     *
     *           为了方便方便功能显示,不考虑sql注入问题.为了之后mybatis做一个铺垫, mybatis有动态Sql
     *           之后肯定不会像现在这样写.
     *
     *           只是演示功能.
     * @param condition
     * @return
     */
    JsonResult searchByCondition(GoodsTypeCondition condition);

    /**
     *删除商品分类
     * @param level
     * @return
     */
    boolean deleteById(String level);


    /**
     * 修改商品分类
     * @param type
     * @return
     */
    boolean updateGoodsById(Type type);

    /**
     * 添加商品
     * @param goods
     * @return
     */
    boolean addGoods(Goods goods);

    /**
     * 获取订单集合
     * @param username
     * @param uid
     * @param status
     * @return
     */
    List<Orders> showOrderByUidAndStatus(String username,int status);
}
