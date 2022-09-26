package com.qf.dao.impl;

import com.qf.dao.IGoodsDao;
import com.qf.pojo.Goods;
import com.qf.pojo.Page;
import com.qf.utils.DBUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: GoodsDaoImpl
 * @Date: 2022/9/20 22:57
 */
public class GoodsDaoImpl implements IGoodsDao {
    private final QueryRunner queryRunner = new QueryRunner(DBUtil.getDateSource());

    @Override
    public List<Goods> getGoodsByTid(int tid) {
        String sql = "select * from tb_goods where tid = ?";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(Goods.class),tid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Goods> getGoodsByPage(int offset, int size) {
        // 现在这里可以使用*号,之后大家务必查询字段, 否则见一回打一回.
        // 现在这里可以使用*号,之后大家务必查询字段, 否则见一回打一回.
        // 现在这里可以使用*号,之后大家务必查询字段, 否则见一回打一回.
        // 现在这里可以使用*号,之后大家务必查询字段, 否则见一回打一回.
        // 现在这里可以使用*号,之后大家务必查询字段, 否则见一回打一回.
        String sql = "select * from tb_goods limit ?,?";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(Goods.class),offset,size);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Goods> getGoodsByPage(int offset, int size, int tid) {
        String sql = "select * from tb_goods where tid = ? limit ?,? ";
        try {
            return queryRunner.query(sql, new BeanListHandler<>(Goods.class), tid , offset, size);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getGoodsCount(int tid) {
        String sql = "select count(*) from tb_goods where tid = ?";
        try {
            return queryRunner.query(sql,new ScalarHandler<>(),tid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Goods getGoodsById(int id) {
        String sql = "select * from tb_goods where id = ?";
        try {
            return queryRunner.query(sql,new BeanHandler<>(Goods.class),id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
