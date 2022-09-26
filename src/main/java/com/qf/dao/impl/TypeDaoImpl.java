package com.qf.dao.impl;

import com.qf.dao.ITypeDao;
import com.qf.pojo.Type;
import com.qf.utils.DBUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: TypeDaoImpl
 * @Date: 2022/9/20 21:33
 */
public class TypeDaoImpl implements ITypeDao {
    private final QueryRunner queryRunner = new QueryRunner(DBUtil.getDateSource());
    @Override
    public List<Type> getAllTypes(int parentId) {
        String sql = "select * from tb_goods_type where parent = ?";

        try {
            return queryRunner.query(sql,new BeanListHandler<>(Type.class),parentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
