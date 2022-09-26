package com.qf.controller;

import com.qf.utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: BaseController
 * @Date: 2022/9/19 20:48
 * 三件事
 * 1. 拿参数
 * 2. 调用子类方法 --> 反射
 * 3. 处理方法的返回值,跳转页面
 */
public class BaseController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码格式
        request.setCharacterEncoding("utf-8");

        //2.获取参数
        String methodName = request.getParameter(Constants.TAG);
        if (methodName == null || methodName.length() == 0){
            methodName = Constants.INDEX;
        }

        //3. 调用子类的方法, 反射一波.
        Class<? extends BaseController> clazz = this.getClass();
        try {
            // 根据方法名称获取方法执行对象.
            Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // 调用方法
            //  参数说明
            //      1. 方法所在类的对象
            //      2. Object ... args, 表示方法的参数
            //      Object result, 表示返回值
            String result = (String) method.invoke(this, request, response);

            //表示该方法有返回值
            //      forward/redirect
            //      纯字符串
            if (result != null){
                //forward:index.jsp
                if (result.startsWith(Constants.FORWARD)){
                    request.getRequestDispatcher(Constants.ROOT_PATH + result.split(Constants.FLAG)[1]).forward(request,response);
                }else if (result.startsWith(Constants.REDIRECT)){
                    response.sendRedirect(request.getContextPath() + Constants.ROOT_PATH + result.split(Constants.FLAG)[1]);
                }else {
                    response.getWriter().write(result);
                }
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // 如果方法没有找着,则强制去首页.index.jsp.「根据自己需求来决定.」
            request.getSession().setAttribute("msg","程序异常!请稍后再试！");
            response.sendRedirect("/message.jsp");
            System.out.println("e: " + e);
            e.printStackTrace();
            //记录日志
        }

    }

    /**
     * 兜底数据, 当methodName没有传或者是方法
     * @param request
     * @param response
     * @return
     */
    public String index(HttpServletRequest request,HttpServletResponse response){
        return "forward:index.jsp";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
