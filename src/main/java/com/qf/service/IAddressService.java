package com.qf.service;

import com.qf.pojo.Address;

import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: IAddressService
 * @Date: 2022/9/22 0:42
 */
public interface IAddressService {

    /**
     * 通过uid获取数据库中的地址
     * @param uid
     * @return
     */
    List<Address> getAddressByUid(String uid);

    /**
     * 新增地址
     * @param address
     * @return
     */
    boolean  addAddress(Address address);

    /**
     * 删除地址
     * @param aid
     * @return
     */
    boolean deleteAddress(String aid);

    /**
     * 更新地址
     * @param address
     * @return
     */
    boolean updateAddress(Address address);

    /**
     * 设置默认地址
     * @param aid
     * @return
     */
    boolean defaultAddress(String aid);
}
