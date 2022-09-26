//package com.qf.test;
//
//import com.alibaba.fastjson2.JSON;
//import com.wf.captcha.utils.CaptchaUtil;
//
//import javax.servlet.*;
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;
//import java.io.IOException;
//
//@WebServlet(name = "UserController", value = "/userController")
//public class UserController extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // 四件事
//        // 1. 设置编码
//        request.setCharacterEncoding("utf-8");
//        response.setContentType("text/html;charset=utf-8");
//
//        // 2. 获取请求参数
//        //      强制要求,前端传递参数的时候必须给带这个参数.
//        String methodName = request.getParameter("method");
//
//        // 这就属于异常情况了.
//        if(methodName == null || methodName.length() == 0){
//            methodName = "index";
//        }
//
//        String result = null;
//        // 根据方法名称进行判断了.不同的方法指向不同的业务处理
//        switch (methodName){
//            case "login":
//                result = login(request, response);
//                break;
//            case "logout":
//                result = logout(request, response);
//                break;
//            case "register":
//                result = register(request, response);
//                break;
//            case "test":
//                result =   test(request, response);
//                break;
//            case "userInfo":
//                result = userInfo(request, response);
//                System.out.println(result);
//                break;
//            case  "captcha":
//                captcha(request, response);
//                break;
//            case "index":
//                // 兜底方法,当methodName不匹配的时候,执行此逻辑.
//                index(request, response);
//                break;
//            default:
//                // 当没有匹配上参数的时候,直接调用这个方法处理
//                index(request, response);
//                break;
//        }
//
//        // 判断一下返回值有没有值;
//        //      肯定有值. --> 约定.
//        //      返回值包含了两部分内容:
//        //          姿势: 就两种: 转发 重定向
//        //          去哪: url
//        // 解析返回值这个串
//        // "forward:success.jsp";
//        if(result != null){
//            String[] split = result.split(":");
//            String type = split[0]; // 从返回值的字符串当中拿到了标记, 「姿势标记」, forward「转发」, redirect「重定向」
//            String url = split[1];
//            if(type.equals("forward")){
//                request.getRequestDispatcher("/" + url).forward(request, response);
//            }else if(type.equals("redirect")){
//                response.sendRedirect(request.getContextPath() + "/" + url);
//            }else{
//                // 直接写给浏览器, 完事.不用做额外处理
//                response.getWriter().write(result);
//            }
//        }
//
//        // 3. 调用service层
//        // 4. 根据service层处理结果跳转页面
//    }
//
//    /**
//     * 处理具体业务的方法
//     * @param request
//     * @param response
//     * @throws IOException
//     */
//    private void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.getWriter().write("大哥,你啥也没有传啊,直接回首页了... 这是异常情况. 呵呵...." );
//    }
//
//    /**
//     * 返回值包含两部分:
//     *          String字符串,
//     *                  目的:
//     *                          告诉doGet, 你的方式是咋样的  ----> 转发/重定向
//     *                          去哪 --> 跳转url
//     * @param request
//     * @param response
//     * @return
//     */
//    public String test(HttpServletRequest request, HttpServletResponse response){
//        return "forward:success.jsp";
//    }
//    /**
//     * 处理具体业务的方法
//     * @param request
//     * @param response
//     * @throws IOException
//     */
//    private String register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        // response.getWriter().write("注册成功了.....");
//        // request.getRequestDispatcher("/success.jsp").forward(request,response);
//        return "forward:success.jsp";
//    }
//
//    /**
//     * 处理具体业务的方法
//     * @param request
//     * @param response
//     * @throws IOException
//     */
//    private String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        // response.getWriter().write("登出成功了,,大兄弟....");
//        // response.sendRedirect(request.getContextPath() + "/success.jsp");
//        return "redirect:success.jsp";
//    }
//
//    /**
//     * 处理具体业务的方法
//     * @param request
//     * @param response
//     * @throws IOException
//     */
//    private String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        // response.getWriter().write("hello, 大哥,你好.你登录成功了......");
//        return "redirect:index.jsp";
//    }
//
//    /**
//     * 返回纯纯的字符串, 没有forward, 或者是redirect. 只是一个json串
//     * @param request
//     * @param response
//     * @return
//     */
//    private String userInfo(HttpServletRequest request, HttpServletResponse response){
//        User user = new User();
//        user.setId(1024);
//        user.setName("张小三三");
//        user.setSex("男");
//        return JSON.toJSONString(user);
//    }
//
//    /**
//     * 验证码处理
//     * @param request
//     * @param response
//     */
//    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        // 输出一张图片给前端.
//        // 并且把生成的验证保存到了Session里,方便我们后续的验证
//        CaptchaUtil.out(request,response);
//    }
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        this.doGet(request, response);
//    }
//}
