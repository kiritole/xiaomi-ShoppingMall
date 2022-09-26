package com.qf.service;

import com.qf.pojo.Cart;
import com.qf.pojo.Orders;

import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: IOrderService
 * @Date: 2022/9/22 11:41
 */
public interface IOrderService {

    /**
     * 通过uid展示用户购物车数据到订单展示页
     * @param uid
     * @return
     */
    List<Cart> showCartByUid(String uid);

    /**
     * 生成订单
     * @param orders
     * @return
     */
    boolean  createOrder(Orders orders);

    /**
     * 获取订单
     * @param uid
     * @return
     */
    List<Orders> showMyOrders(String uid);

    /**
     * 获取订单详细信息
     * @param oid
     * @return
     */
    Orders orderDetails(String oid);
}
