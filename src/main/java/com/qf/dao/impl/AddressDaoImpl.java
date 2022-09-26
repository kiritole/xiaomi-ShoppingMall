package com.qf.dao.impl;

import com.qf.dao.IAddressDao;
import com.qf.pojo.Address;
import com.qf.utils.DBUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: AddressDaoImpl
 * @Date: 2022/9/22 0:40
 */
public class AddressDaoImpl implements IAddressDao {
    private final QueryRunner queryRunner = new QueryRunner(DBUtil.getDateSource());

    @Override
    public List<Address> getAddressList(int uid) {
        String sql = "select * from tb_address where uid = ?";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(Address.class),uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Address getAddressById(int aid) {
        String sql = "select * from tb_address where id=?";
        try {
            return queryRunner.query(sql,new BeanHandler<>(Address.class),aid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addAddress(Address address) {
        String sql = "insert into tb_address(detail,name,phone,uid,state)values(?,?,?,?,?)";
        try {
            return queryRunner.update(sql,address.getDetail(),
                    address.getName(),
                    address.getPhone(),
                    address.getUid(),
                    address.getState());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteAddress(int aid) {
        String sql = "delete from tb_address where id = ?";
        try {
            return queryRunner.update(sql,aid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateAddress(Address address) {
        String sql = "UPDATE tb_address set detail=?,name=?,phone=?,uid=?,state=? where id=?";
        try {
            return queryRunner.update(sql,address.getDetail(),
                    address.getName(),
                    address.getPhone(),
                    address.getUid(),
                    address.getState(),
                    address.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int defaultAddress(String aid) {
        String sql = "update tb_address set state=? where id=?";
        try {
            return queryRunner.update(sql,1,aid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
