package com.qf.controller.cart;

import com.qf.controller.BaseController;
import com.qf.pojo.Cart;
import com.qf.pojo.JsonResult;
import com.qf.pojo.User;
import com.qf.service.ICartService;
import com.qf.service.impl.CartServiceImpl;
import com.qf.utils.Constants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: CartController
 * @Date: 2022/9/21 20:06
 */
@WebServlet("/cart")
public class CartController extends BaseController {
    private final ICartService cartService = new CartServiceImpl();
    /**
     * 添加购物车
     * @param request
     * @param response
     * @return
     */
    public String addCart(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        // 1. 设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession();
        User userNow = (User) session.getAttribute("userNow");

        // 域对象当中的值为空,则表示没有登录, 强制用户去登录就可以了.
        if (userNow == null){
            return Constants.FORWARD + "login.jsp";
        }

        //2.拿参数
        //      获取商品id, 从goodsDetail.jsp点击: 「添加购物车」按钮传递过来.
        String pid = request.getParameter("pid");
        // 业务当中pid, 一定是有值的.
        // 3. 调用service
        Cart cart = new Cart();
        cart.setPid(Integer.parseInt(pid));
        int uId = userNow.getId();
        cart.setUid(uId);
        JsonResult result = cartService.addCart(cart);

        //4.根据service处理的结果进行页面跳转
        // 4. 根据service处理的结果进行页面跳转
        if (result.getCode() != 0) {
            request.setAttribute("msg", result.getMsg());
            return Constants.FORWARD + "message.jsp";
        }

        // 一切ok, 添加到购物车成功, 添加成功的页面.
        return Constants.FORWARD + "cartSuccess.jsp";
    }

    /**
     * 查看购物车
     *
     * @param request
     * @param response
     * @return
     */
    public String lookCart(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // 获取参数
        // uid
        String uid = request.getParameter("uid");

        // 调用service层
        List<Cart> carts = cartService.lookCart(uid);
        request.setAttribute("list",carts);
        return Constants.FORWARD + "cart.jsp";
    }

    /**
     * 更新购物车数据
     * 价格
     * 数量
     * 根据cid
     *
     * @param request
     * @param response
     * @return
     */
    public String updateCart(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //4件事
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession();
        User userNow = (User) session.getAttribute("userNow");

        //2.获取参数
        String cid = request.getParameter("cid");
        String num = request.getParameter("cnum");
        String price = request.getParameter("price");

        //3.调用service方法
        cartService.updateCart(cid, num, price);

        //4.通过调用的service返回的数据进行跳转
        return Constants.FORWARD + "cart?method=lookCart&uid=" + userNow.getId();
    }

    /**
     * 根据cid删除tb_cart表当中的数据
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    public String deleteById(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        //4.4件事
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession();
        User userNow = (User) session.getAttribute("userNow");

        //2.获取参数
        String cid = request.getParameter("cid");

        //调用service方法
        cartService.deleteCartById(cid);

        //4.通过调用的service返回的数据进行跳转
        return Constants.FORWARD + "cart?method=lookCart&uid=" + userNow.getId();
    }

    /**
     * 根据uid，清空购物车
     * ocation.href = "cart?method=clearCart&uid="+uid;
     * @param request
     * @param response
     * @return
     */
    public String clearCart(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.拿参数
        String uid = request.getParameter("uid");

        //调用service层，处理业务逻辑
        boolean flag = cartService.clearCartByUId(uid);

        //清空之后,我的购物车没有数据, 所返回的页面,可以自己定义一下,你想去哪就去哪里. 我这里返回当前页面即可;
        return Constants.FORWARD + "cart?method=lookCart&uid=" + uid;
    }

}
