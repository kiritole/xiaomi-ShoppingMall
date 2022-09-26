package com.qf.dao;

import com.qf.pojo.Cart;

import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: ICartDao
 * @Date: 2022/9/21 20:14
 */
public interface ICartDao {

    /**
     * 添加到购物车
     * @param cart
     * @return
     */
    int addCart(Cart cart);

    /**
     * 根据用户id查询出购物车数据
     * @param uid
     * @return
     */
    List<Cart> lookCart(int uid);

    /**
     * 更新购物车数据
     * @param cid
     * @param num
     * @param price
     * @return
     */
    int updateCart(int cid, int num, int price);

    /**
     * 根据cid删除tb_cart表当中的数据
     * @param cid
     * @return
     */
    int deleteCartById(int cid);

    /**
     * 批量删除操作
     * @param params
     * @return
     */
    int batchClearCart(Object[][] params);

    /**
     * 根据用户id查询出购物车数据
     * @param uid
     * @return
     */
    List<Cart> getCart(int uid);

    /**
     * 根据uid删除tb_cart表中的数据
     * @param uid
     * @return
     */
    int deleteCartByUid(int uid);


}
