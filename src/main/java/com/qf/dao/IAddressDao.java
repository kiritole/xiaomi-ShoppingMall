package com.qf.dao;

import com.qf.pojo.Address;

import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: IAddressDao
 * @Date: 2022/9/22 0:38
 */
public interface IAddressDao {

    /**
     * 获取当前用户的地址列
     * @param uid
     * @return
     */
    List<Address> getAddressList(int uid);

    /**
     * 通过地址id来获取指定地址
     * @param aid
     * @return
     */
    Address getAddressById(int aid);

    /**
     * 新增地址
     * @param address
     * @return
     */
    int addAddress(Address address);


    /**
     * 删除地址
     * @param aid
     * @return
     */
    int deleteAddress(int aid);

    /**
     * 更新地址
     * @param address
     * @return
     */
    int updateAddress(Address address);

    /**
     * 设置默认地址
     * @param aid
     * @return
     */
    int defaultAddress(String aid);
}
