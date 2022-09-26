package com.qf.service;

import com.qf.pojo.Cart;
import com.qf.pojo.JsonResult;

import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: ICartService
 * @Date: 2022/9/21 20:19
 */
public interface ICartService {

    /**
     * 添加到购物
     * @param
     * @return
     */
    JsonResult addCart(Cart cart);

    /**
     * 根据当前的用户id查看购物车
     * @param uid
     * @return
     */
    List<Cart> lookCart(String uid);

    /**
     * 更新购物车数据
     * @param cid
     * @param num
     * @param price
     * @return
     */
    boolean updateCart(String cid,String num,String price);

    /**
     * 根据cid来删除tb_cart表中的商品数据
     * @param cid
     * @return
     */
    boolean deleteCartById(String cid);

    /**
     * 清空当前「我的购物车」
     * @param cid
     * @return
     */
    boolean clearCartByUId(String uid);

}
