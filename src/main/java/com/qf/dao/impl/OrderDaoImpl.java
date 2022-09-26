package com.qf.dao.impl;

import com.qf.dao.IOrderDao;
import com.qf.pojo.Cart;
import com.qf.pojo.OrderDetail;
import com.qf.pojo.Orders;
import com.qf.utils.DBUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: OrderDaoImpl
 * @Date: 2022/9/22 11:37
 */
public class OrderDaoImpl implements IOrderDao {
    private final QueryRunner queryRunner = new QueryRunner(DBUtil.getDateSource());

    @Override
    public List<Cart> getCartList(int uid) {
        String sql = "select * from tb_cart where uid = ?";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(Cart.class),uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int createOrder(Orders orders) {
        String sql = "insert into tb_order values(?,?,?,?,?,?)";
        try {
            return queryRunner.update(sql, orders.getId(),
                    orders.getUid(),
                    orders.getMoney(),
                    orders.getStatus(),
                    orders.getTime(),
                    orders.getAid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Orders> showMyOrders(String uid) {
        String sql = "select * from tb_order where uid = ?";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(Orders.class),uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Orders orderDetails(String oid) {
        String sql = "select * from tb_order where id = ?";
        try {
            return queryRunner.query(sql,new BeanHandler<>(Orders.class),oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addOrderDetails(OrderDetail orderDetail) {
        String sql = "insert into tb_orderdetail(oid,pid,num,money)values(?,?,?,?)";
        try {
            return queryRunner.update(sql, orderDetail.getOid(),
                    orderDetail.getPid(),
                    orderDetail.getNum(),
                    orderDetail.getMoney());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(String oid) {
        String sql = "select * from tb_orderdetail where oid = ?";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(OrderDetail.class),oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
