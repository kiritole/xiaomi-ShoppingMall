package com.qf.dao.impl;

import com.qf.dao.IAdminDao;
import com.qf.pojo.Goods;
import com.qf.pojo.Orders;
import com.qf.pojo.Type;
import com.qf.pojo.User;
import com.qf.utils.DBUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: AdminDaoImpl
 * @Date: 2022/9/22 23:17
 */
public class AdminDaoImpl implements IAdminDao {
    private final QueryRunner queryRunner = new QueryRunner(DBUtil.getDateSource());

    @Override
    public User getAdminByName(String username) {
        String sql = "select * from tb_user where username = ?";
        try {
            return queryRunner.query(sql,new BeanHandler<>(User.class),username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addGoodsType(Type type) {
        String sql = "INSERT INTO tb_goods_type(name,level, parent)values(?,?,?)";
        try {
            return queryRunner.update(sql,type.getName(),type.getLevel(),type.getParent());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Type> getTypeList(String sql) {
        try {
            return queryRunner.query(sql,new BeanListHandler<>(Type.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByLevel(String level) {
        String sql = "delete from tb_goods_type where level = ?";
        try {
            return queryRunner.update(sql,level);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateGoodsById(Type type) {
        String sql = "update tb_goods_type set name=?,level=?,parent=? where id=?";
        try {
            return queryRunner.update(sql,type.getName(),
                    type.getLevel(),
                    type.getParent(),
                    type.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addGoods(Goods goods) {
        String sql = "insert into tb_goods(name,pubdate,picture,price,star,intro,tid)values(?,?,?,?,?,?,?)";
        try {
            System.out.println(goods);
            return queryRunner.update(sql,
                    goods.getName(),
                    goods.getPubdate(),
                    goods.getPicture(),
                    goods.getPrice(),
                    goods.getStar(),
                    goods.getIntro(),
                    goods.getTid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User searchUser(String username) {
        String sql = "select * from tb_user where username=?";
        try {
            return queryRunner.query(sql,new BeanHandler<>(User.class),username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Orders> showOrderByUidAndStatus(int uid, int status) {
        String sql = "select * from tb_order where uid=? and status=?";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(Orders.class),uid,status);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Orders> showOrderByUidAndStatus(int status) {
        String sql = "select * from tb_order where status=?";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(Orders.class),status);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
