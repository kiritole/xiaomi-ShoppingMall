package com.qf.service.impl;

import com.qf.dao.IAddressDao;
import com.qf.dao.ICartDao;
import com.qf.dao.IGoodsDao;
import com.qf.dao.IOrderDao;
import com.qf.dao.impl.AddressDaoImpl;
import com.qf.dao.impl.CartDaoImpl;
import com.qf.dao.impl.GoodsDaoImpl;
import com.qf.dao.impl.OrderDaoImpl;
import com.qf.pojo.*;
import com.qf.service.IGoodsService;
import com.qf.service.IOrderService;


import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: OrderServiceImpl
 * @Date: 2022/9/22 11:42
 */
public class OrderServiceImpl implements IOrderService {
    private IOrderDao orderDao = new OrderDaoImpl();
    private IGoodsService goodsService = new GoodsServiceImpl();
    private IAddressDao addressDao = new AddressDaoImpl();
    private ICartDao cartDao = new CartDaoImpl();
    private IGoodsDao goodsDao = new GoodsDaoImpl();

    @Override
    public List<Cart> showCartByUid(String uid) {
        //参数校验
        List<Cart> cartList = orderDao.getCartList(Integer.parseInt(uid));

        for (Cart cart : cartList) {
            int id = cart.getPid();
            Goods goods = goodsService.getGoodsById(String.valueOf(id));
            cart.setGoods(goods);
        }
        //调用数据库获得Cart购物车信息集合
        return cartList;
    }

    /**
     *  多个dml操作,一定要想到事务.

     *          这里想要操作事务必须保证是同一个Connection对象来操作数据库,DbUtils使用Connection麻烦,
     *          更改好多东西.暂不写, 之后学到spring, 声明式事务,简单的一p.
     *
     *          重要的你得知道这里事务.
     *          重要的你得知道这里事务.
     *          重要的你得知道这里事务.
     *          重要的你得知道这里事务.
     * =============================================
     *      1. 多个dml操作,一定要想到事务.
     *              四大特征
     *              隔离级别
     *              隔离级别产生的问题
     *
     *              Connection ---> setAutoCommit, commit, rollback
     * 1. 插入到tb_orders表当中, 生成订单
     * 2. 删除掉tb_cart表当中当前用户对应的商品信息
     *      清空当前的购物车「根据uid来清空」
     * 3. 将当前的订单数据封装一下,插入到tb_ordersdetail表当中, 订单详情表.
     * @param orders
     * @return
     */
    @Override
    public boolean createOrder(Orders orders) {
        // 执行tb_orders插入数据, 生成订单信息
        int rows = orderDao.createOrder(orders);

        // 向tb_orderdetail表当中插入订单详情
        //id, 自增, oid, 从orders对象当中获取,  pid,num, money根据userId去tb_cart当中查询出pid, List<Cart>, num
        // id自增
        String oid = orders.getId();
        int uid = orders.getUid();
        /// 通过uid去tb_cart表当中查询出所有记录
        List<Cart> cartList = cartDao.getCart(uid);
        for (Cart cart : cartList) {
            int pid = cart.getPid();
            Goods goods = goodsDao.getGoodsById(pid);
            cart.setGoods(goods);

            // 批量插入合理一些.
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setPid(pid);
            orderDetail.setNum(cart.getNum());
            orderDetail.setMoney(cart.getMoney());
            orderDetail.setOid(oid);
            orderDao.addOrderDetails(orderDetail);
        }

        // 3. 删除掉tb_cart表当中,对应的uid的数据;
        cartDao.deleteCartByUid(orders.getUid());
        // 参数校验
        return rows == 1;
    }

    @Override
    public List<Orders> showMyOrders(String uid) {
        List<Orders> ordersList = orderDao.showMyOrders(uid);
        for (Orders orders : ordersList) {
            int aid = orders.getAid();
            Address address = addressDao.getAddressById(aid);
            orders.setAddress(address);
        }
        //参数校验
        return ordersList;
    }

    @Override
    public Orders orderDetails(String oid) {
        // 1. 通过接收到的orderId去tb_order查询出对应的记录;
        Orders orders = orderDao.orderDetails(oid);

        //2.获取aid，「地址的主键」, 去tb_address表中查询出对应的address
        int aid = orders.getAid();
        Address address = addressDao.getAddressById(aid);
        orders.setAddress(address);

        //3. 根据orders当中的id去tb_orderDetail表当中,查询出订单详情.
        List<OrderDetail> orderDetails = orderDao.getOrderDetailsByOrderId(oid);

        // 4. 获取OrderDetail当中的pid去tb_goods表当中查询出对应的数据即可;
        for (OrderDetail orderDetail : orderDetails) {
            int pid = orderDetail.getPid();
            Goods goods = goodsDao.getGoodsById(pid);
            orderDetail.setGoods(goods);
        }
        orders.setOrderDetails(orderDetails);
        return orders;
    }
}
