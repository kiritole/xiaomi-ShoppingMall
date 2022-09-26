package com.qf.controller.order;

import com.qf.controller.BaseController;
import com.qf.pojo.Cart;
import com.qf.pojo.Goods;
import com.qf.pojo.Orders;
import com.qf.pojo.User;
import com.qf.service.IGoodsService;
import com.qf.service.IOrderService;
import com.qf.service.impl.GoodsServiceImpl;
import com.qf.service.impl.OrderServiceImpl;
import com.qf.utils.Constants;
import com.qf.utils.RandomUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: OrderControlelr
 * @Date: 2022/9/22 0:20
 */
@WebServlet("/order")
public class OrderController extends BaseController {
    private final IOrderService orderService = new OrderServiceImpl();

    /**
     * 订单预览
     * @param request
     * @param response
     * @return
     */
    public String orderPreView(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //4件事
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.那参数
        String uid = request.getParameter("uid");

        //3.调用service层处理业务并返回Cart集合
        List<Cart> carts = orderService.showCartByUid(uid);
        request.setAttribute("cartList",carts);

        // 商品信息
        // 人
        // 地址
        return Constants.FORWARD + "order.jsp";
    }

    /**
     * 生成订单
     * @param request
     * @param response
     * @return
     */
    public String createOrder(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // 拿参数三个
        // 用户id
        String uid = request.getParameter("uid");
        // 订单总价格
        String money = request.getParameter("money");
        // 地址id
        String aid = request.getParameter("aid");
        // 为了存储域对象, 两个思路
        //      1. service层返回JsonResult  -> 推荐此种方式
        //      2. 直接在controller层封装好Orders对象.然后再交给service, 根据service层处理的结果存储域对象.
        Orders orders = new Orders();
        orders.setUid(Integer.parseInt(uid));
        orders.setMoney(Integer.parseInt(money));
        orders.setAid(Integer.parseInt(aid));
        // 生成一个主键
        String id = RandomUtils.createOrderId();
        orders.setId(id);
        orders.setTime(new Date());
        orders.setStatus(0);

        // 调用service层
        boolean success = orderService.createOrder(orders);
        if(success){
            request.setAttribute("order", orders);
            return Constants.FORWARD + "orderSuccess.jsp";
        }

        // 失败了,再回去原来的页面
        return Constants.FORWARD + "order?method=orderPreView&uid=" + uid;
    }

    public String showMyOrders(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession();
        User userNow = (User) session.getAttribute("userNow");

        //2.拿参数
        int id = userNow.getId();

        //3.调用service层的方法
        List<Orders> ordersList = orderService.showMyOrders(String.valueOf(id));
        request.setAttribute("ordersList",ordersList);
        //跳转页面
        return Constants.FORWARD + "orderList.jsp";
    }

    /**
     * 展示订单详情
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    public String orderDetails(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.拿去参数
        String oid = request.getParameter("oid");

        //3.调用service层方法
        Orders orders = orderService.orderDetails(oid);
        request.setAttribute("order",orders);

        //4.根据service层的处理结果,跳转页面
        return Constants.FORWARD + "orderDetail.jsp";
    }
}
