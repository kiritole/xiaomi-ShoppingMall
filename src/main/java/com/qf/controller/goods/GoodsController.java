package com.qf.controller.goods;

import com.qf.controller.BaseController;
import com.qf.pojo.Goods;
import com.qf.pojo.Page;
import com.qf.service.IGoodsService;
import com.qf.service.impl.GoodsServiceImpl;
import com.qf.utils.Constants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: GoodsController
 * @Date: 2022/9/20 23:01
 */
@WebServlet("/goods")
public class GoodsController extends BaseController {
    private final IGoodsService goodsService = new GoodsServiceImpl();

    public String showGoodsByTid(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //四件事
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.拿取参数
        String tid = request.getParameter("tid");
        String page = request.getParameter("page");
        String size = request.getParameter("size");

//        //3.调用service层处理业务逻辑
//        List<Goods> goodsList = goodsService.getGoodsByTId(tid);
//        request.setAttribute("list",goodsList);
        //使用分页
        Page<Goods> paging = goodsService.getGoodsByPage(page, size, tid);
        request.setAttribute("pageBean",paging);

        //根据service处理处理结果跳转页面
        return Constants.FORWARD + "goodsList.jsp";
    }

    /**
     * 展示商品详情
     * 根据id, tb_goods表当中的id
     * @param request
     * @param response
     * @return
     */
    public String showGoodsDetail(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //四件事
        // 1. 设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.那参数
        //tb_goods表当中的键
        String id = request.getParameter("id");
        //3. 调用service层, 处理具体的业务逻辑
        Goods goods = goodsService.getGoodsById(id);
        request.setAttribute("goods",goods);
        // 4. 根据service层的处理结果跳转页面.
        return Constants.FORWARD + "goodsDetail.jsp";
    }
}
