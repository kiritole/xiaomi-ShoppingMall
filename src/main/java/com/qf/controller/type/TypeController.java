package com.qf.controller.type;

import com.alibaba.fastjson2.JSON;
import com.qf.controller.BaseController;
import com.qf.pojo.JsonResult;
import com.qf.service.ITypeService;
import com.qf.service.impl.TypeServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: TypeController
 * @Date: 2022/9/20 21:39
 */
@WebServlet("/type")
public class TypeController extends BaseController {
    private final ITypeService typeService = new TypeServiceImpl();

    /**
     * 在tb_goods_type表中查找大类别/父目录, 说白了parent = 0, 保存成集合 --> json字符串给前端.
     * @param request
     * @param response
     * @return
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //四件事
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.获取参数

        //3.调用Service层
        JsonResult result = typeService.getGoodsTypes("0");
        // 4. 根据service层处理结果,返回数据即可;
        /**
         * {
         *  code:
         *  msg:
         *  data:[
         *      {},
         *      {},
         *  ]
         * }
         *
         *
         */
        return JSON.toJSONString(result);
    }
}
