package com.qf.dao.impl;

import com.qf.dao.IUserDao;
import com.qf.pojo.User;
import com.qf.utils.DBUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import java.sql.SQLException;


/**
 * @author kilote
 * @version 1.0
 * @ClassName: UserDaoImpl
 * @Date: 2022/9/19 21:11
 */
public class UserDaoImpl implements IUserDao {
    private final QueryRunner queryRunner = new QueryRunner(DBUtil.getDateSource());

    @Override
    public User getUserByName(String name) {
        String sql = "select * from tb_user where username = ?";
        try {
            return queryRunner.query(sql, new BeanHandler<>(User.class), name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int register(User user) {
        String sql = "insert into tb_user(username,password,email,gender,status,role,code)values(?,?,?,?,?,?,?)";
        try {
            return queryRunner.update(sql,
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getGender(),
                    user.getStatus(),
                    user.getRole(),
                    user.getCode());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int activate(String email, String code) {
        String sql = "update tb_user set status=? where email = ? and code = ?";
        try {
            return queryRunner.update(sql,1,email,code);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserById(int id) {
        String sql = "select * from tb_user where id=?";
        try {
            return queryRunner.query(sql,new BeanHandler<>(User.class),id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
