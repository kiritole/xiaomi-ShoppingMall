package com.qf.dao;

import com.qf.pojo.Cart;
import com.qf.pojo.OrderDetail;
import com.qf.pojo.Orders;

import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: IOrderDao
 * @Date: 2022/9/22 11:35
 */
public interface IOrderDao {

    /**
     * 获取购物车的数据,以便于封装订单预览数据
     * @param uid
     * @return
     */
    List<Cart> getCartList(int uid);

    /**
     * 生成订单
     * @param orders
     * @return
     */
    int createOrder(Orders orders);

    /**
     * 获取订单
     * @param uid
     * @return
     */
    List<Orders> showMyOrders(String uid);

    /**
     * 根据oid来获取tb_order表信息
     * @param oid
     * @return
     */
    Orders orderDetails(String oid);

    /**
     *  插入订单详情
     * @param orderDetail
     * @return
     */
    int addOrderDetails(OrderDetail orderDetail);

    /**
     * 根据订单表的主键查询出该订单对应的详情信息
     * @param oid
     * @return
     */
    List<OrderDetail> getOrderDetailsByOrderId(String oid);
}
