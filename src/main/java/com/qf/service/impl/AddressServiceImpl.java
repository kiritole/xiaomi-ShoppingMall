package com.qf.service.impl;

import com.qf.dao.IAddressDao;
import com.qf.dao.impl.AddressDaoImpl;
import com.qf.pojo.Address;
import com.qf.service.IAddressService;

import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: AddressServiceImpl
 * @Date: 2022/9/22 0:44
 */
public class AddressServiceImpl implements IAddressService {
    private final IAddressDao addressDao = new AddressDaoImpl();
    @Override
    public List<Address> getAddressByUid(String uid) {
        //参数校验

        //调用数据库返回地址
        return addressDao.getAddressList(Integer.parseInt(uid));
    }

    @Override
    public boolean addAddress(Address address) {
        //参数校验

        return addressDao.addAddress(address) == 1;
    }

    @Override
    public boolean deleteAddress(String aid) {

        return addressDao.deleteAddress(Integer.parseInt(aid)) == 1;
    }

    @Override
    public boolean updateAddress(Address address) {
        //参数校验

        return addressDao.updateAddress(address) == 1;
    }

    @Override
    public boolean defaultAddress(String aid) {
        //校验参数

        return addressDao.defaultAddress(aid) == 1;
    }
}
