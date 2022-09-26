package com.qf.service.impl;

import com.qf.dao.ICartDao;
import com.qf.dao.IGoodsDao;
import com.qf.dao.impl.CartDaoImpl;
import com.qf.dao.impl.GoodsDaoImpl;
import com.qf.pojo.Cart;
import com.qf.pojo.Goods;
import com.qf.pojo.JsonResult;
import com.qf.service.ICartService;

import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: CartService
 * @Date: 2022/9/21 20:19
 */
public class CartServiceImpl implements ICartService {
    private final ICartDao cartDao = new CartDaoImpl();
    private final IGoodsDao goodsDao = new GoodsDaoImpl();

    /**
     * 判断一下当前用户是否已经登录了
     *      依赖于域对象来判断, 如果用户已经登录过了,在登录逻辑当中,把当前登录的用户
     *      存储到了session里.现在从session拿出来,判断一下,
     *      这里就需要:
     *              1. 方法当前传入一个Request对象.
     *              2. 直接在controller层把cart对象封装好,直接交给咱们service层处理. 「用它」
     * @param
     * @return
     */
    @Override
    public JsonResult addCart(Cart cart) {
        int pid = cart.getPid();
        // 去tb_goods表当中查询出对应的数据, pid所对应的数据.
        Goods goods = goodsDao.getGoodsById(pid);
        cart.setNum(1);
        cart.setMoney(goods.getPrice());
        // 因为咱们在Cart实例类当中获取getMoney方法,取商品价格是从Goods对象当中
        // 获取的, 所以必须这里给赋值,否则报空指针异常.
        cart.setGoods(goods);

        //持久化
        int rows = cartDao.addCart(cart);

        if (rows != 1){
            return JsonResult.error(-1,"添加到购物车失败");
        }
        return JsonResult.ok(0,"添加到购物车成功");
    }

    @Override
    public List<Cart> lookCart(String uid) {
        // 参数校验
        // 查询出tb_cart当中所对应当前用户id的所有数据. 注意: 一共表当中只有五个字段. cid,uid,pid,num,money
        //      此时实体类Cart当中有一个字段没有值, Goods goods,为空,通过pid去tb_goods当中查询出数据,再给这个字段赋值.
        List<Cart> carts = cartDao.lookCart(Integer.parseInt(uid));

        // 目的: 就是给Cart类当中的Goods属性赋值.
        for (Cart cart : carts) {
            int pid = cart.getPid();
            // 调用IGoodsDao当中提供的方法
            Goods goods = goodsDao.getGoodsById(pid);
            // 设置到Cart的成员字段当中
            cart.setGoods(goods);
        }
        return carts;
    }

    @Override
    public boolean updateCart(String cid, String num, String price) {
        // 参数校验
        int id = Integer.parseInt(cid);
        int n = Integer.parseInt(num);
        int money = Integer.parseInt(price);

        //调用数据库
        return cartDao.updateCart(id,n,money) == 1;
    }

    @Override
    public boolean deleteCartById(String cid) {
        //参数校验

        return cartDao.deleteCartById(Integer.parseInt(cid)) == 1;
    }

    @Override
    public boolean clearCartByUId(String uid) {
        // 参数校验
        boolean deleteAll = true;
        // 1. 根据uid查询出我的购物车所有数据
        try {
            List<Cart> carts = cartDao.getCart(Integer.parseInt(uid));

            // 批量删除操作
            // 构造删除条件, 正经的斯文人使用此种姿势进行批量: 删除/更新/插入---> dml操作
            Object[][] param = new Object[carts.size()][1];
            for (int i = 0; i < carts.size(); i++) {
                int cid = carts.get(i).getCid();
                param[i][0] = cid;
            }
            int rows = cartDao.batchClearCart(param);
            System.out.println("批量删除了: " + rows + " 记录");
        }catch (Exception e){
            deleteAll = false;
            e.printStackTrace();
        }

        return deleteAll;
    }
}
