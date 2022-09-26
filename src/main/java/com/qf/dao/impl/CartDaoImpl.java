package com.qf.dao.impl;

import com.qf.dao.ICartDao;
import com.qf.pojo.Cart;
import com.qf.utils.DBUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: CartDaoImpl
 * @Date: 2022/9/21 20:15
 */
public class CartDaoImpl implements ICartDao {
    private final QueryRunner queryRunner = new QueryRunner(DBUtil.getDateSource());

    @Override
    public int addCart(Cart cart) {
        String sql = "insert into tb_cart(uid,pid,num,money) values(?,?,?,?)";

        try {
            return queryRunner.update(sql, cart.getUid(),
                    cart.getPid(),
                    cart.getNum(),
                    cart.getMoney());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cart> lookCart(int uid) {
        String sql = "select * from tb_cart where uid = ?";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(Cart.class),uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateCart(int cid, int num, int price) {
        String sql = "update tb_cart set num = ?,money = ? where cid = ?";
        try {
            return queryRunner.update(sql,num,price,cid);
        } catch (SQLException e) {
            System.out.println("就是这里报错，他妈的");
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteCartById(int cid) {
        String sql = "delete from tb_cart where cid = ?";

        try {
            return queryRunner.update(sql,cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int batchClearCart(Object[][] params) {
        String sql = "delete from tb_cart where cid = ?";

        try {
            return queryRunner.batch(sql,params).length;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cart> getCart(int uid) {
        String sql = "select * from tb_cart where uid = ?";

        try {
            return queryRunner.query(sql,new BeanListHandler<>(Cart.class),uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteCartByUid(int uid) {
        String sql = "delete from tb_cart where uid = ?";
        try {
            return queryRunner.update(sql, uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
