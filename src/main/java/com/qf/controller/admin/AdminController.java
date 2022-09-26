package com.qf.controller.admin;

import com.qf.conditions.GoodsTypeCondition;
import com.qf.controller.BaseController;
import com.qf.pojo.*;
import com.qf.service.IAdminService;
import com.qf.service.ITypeService;
import com.qf.service.impl.AdminServiceImpl;
import com.qf.service.impl.TypeServiceImpl;
import com.qf.utils.Constants;
import com.qf.utils.ServletContextUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: adminController
 * @Date: 2022/9/22 23:11
 */
@WebServlet("/admin")
public class AdminController extends BaseController {
    private final IAdminService adminService = new AdminServiceImpl();
    private final ITypeService typeService = new TypeServiceImpl();

    public String adminLogin(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //4件事
        //1.设置编码格式
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.拿去参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //3.调用service层的方法
        User admin = adminService.getAdminByName(username,password);
        if (admin == null){
            return Constants.FORWARD + "admin/login.jsp";
        }

        //4.根据service的结果进行跳转
        request.setAttribute("admin",admin);
        // 这里就是为了存储goodsTypeList域对象. 写到这里了.
        // 建议重新写一个方法好一些.
        JsonResult result = typeService.getGoodsTypes("0");
        if (result.getCode() == 0){
            request.getSession().setAttribute("goodsTypeList",result.getData());
        }
        return Constants.FORWARD + "admin/admin.jsp";
    }

    /**
     * 添加商品分类
     *
     * @param request
     * @param response
     * @return
     */
    public String addGoodsType(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //四件事
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //拿参数
        // 表tb_goods_type表当中的parent对应的值
        String goodsParent = request.getParameter("goodsParent");
        // 表tb_goods_type表当中的parent对应的值
        String typename = request.getParameter("typename");

        //调用service层
        Type type = new Type();
        type.setParent(Integer.parseInt(goodsParent));
        type.setName(typename);

        if (adminService.addGoodsType(type)){
            // 存储到域对象: 查询当前的所有parent = 0的数据.
            JsonResult result = typeService.getGoodsTypes("0");
            if (result.getCode() == 0){
                request.getSession().setAttribute("goodsTypeList",result.getData());
            }
            return Constants.FORWARD + "admin/showGoodsType.jsp";
        }

        //根据service进行跳转
        return "admin/admin.jsp";
    }

    /**
     * 搜索实现
     * @param request
     * @param response
     * @return
     */
    public String search(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        //四件事
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");

        //2.拿封装的参数
        String typeName = request.getParameter("typeName");
        String level = request.getParameter("level");

        GoodsTypeCondition condition = new GoodsTypeCondition();
        condition.setGoodsLevel(level);
        condition.setGoodsName(typeName);

        //3.调用service层，处理核心的业务逻辑
        JsonResult result = adminService.searchByCondition(condition);
        request.setAttribute("goodsTypeList",result.getData());
        return Constants.FORWARD + "admin/showGoodsType.jsp";
    }

    public String deleteById(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        //四件事
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.拿参数
        String level = request.getParameter("level");

        //3.调用service层
        adminService.deleteById(level);

        //4.根据service跳转
        // 这里就是为了存储goodsTypeList域对象. 写到这里了.
        // 建议重新写一个方法好一些.
        JsonResult result = typeService.getGoodsTypes("0");
        if (result.getCode() == 0){
            request.getSession().setAttribute("goodsTypeList",result.getData());
        }
        return Constants.FORWARD + "admin/showGoodsType.jsp";
    }

    public String updateGoodsType(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        //4件事
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.拿参数
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String level = request.getParameter("level");
        String parent = request.getParameter("parent");

        //3.调用service层
        Type type = new Type();
        type.setId(Integer.parseInt(id));
        type.setName(name);
        type.setLevel(Integer.parseInt(level));
        type.setParent(Integer.parseInt(parent));
        if (adminService.updateGoodsById(type)){
            // 这里就是为了存储goodsTypeList域对象. 写到这里了.
            // 建议重新写一个方法好一些.
            JsonResult result = typeService.getGoodsTypes("0");
            if (result.getCode() == 0){
                request.getSession().setAttribute("goodsTypeList",result.getData());
            }
            return Constants.FORWARD + "admin/showGoodsType.jsp";
        }

        //4.跳转界面
        return Constants.FORWARD + "admin/showGoodsType.jsp";
    }

    /**
     * https://commons.apache.org/proper/commons-fileupload/using.html
     * @param request
     * @param response
     * @return
     */
    public String addGoods(HttpServletRequest request,HttpServletResponse response) throws FileUploadException, IOException {
        // Check that we have a file upload request
        // 判断一下当前请求当中是否包含文件上传
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        // 1. 区分普通字段和上传字段
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Configure a repository (to ensure a secure temp location is used)
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Parse the request, 解析请求.
        // FileItem, 表单当中的每一项都是一个FileItem
        List<FileItem> items = upload.parseRequest(request);

        // Process the uploaded items
        Iterator<FileItem> iter = items.iterator();
        Goods goods = new Goods();
        List<String> picUrlList = new ArrayList<>();
        HashMap<String, Object> params = new HashMap<>();
        while (iter.hasNext()) {
            FileItem item = iter.next();

            // 判断一下当前表单的字段是普通的字段还是文件上传的字段
            // true, 表单的普通字段
            // false, 文件上传字段
            if (item.isFormField()) {
                // 处理普通字段
                processFormField(params,item);
            } else {
                // 处理文件上传字段
//                processUploadedFile(picUrlList,item);
                manyProcessUploadedFile(picUrlList, item);
            }
        }

        // java新特性
        String url = picUrlList.stream()
                .collect(Collectors.joining(","));
        params.put("picture",url);
        try {
            BeanUtils.populate(goods,params);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        System.out.println("goods = " + goods);

        // 调用service, 核心业务逻辑.
        boolean success = adminService.addGoods(goods);
        return Constants.FORWARD + "admin/addGoods.jsp";
    }
    /**
     * 处理普通的表单字段
     * 拿到name属性对应的表单字段的值, 存储到对象: Goods对象.
     *
     * @param
     * @param params
     * @param item
     */
    private void processFormField(HashMap<String, Object> params, FileItem item) throws UnsupportedEncodingException {
        //String name = item.getName();
        // 获取表单的name属性对应的值.
        // name必属性对应的值就是: username
        String fieldName = item.getFieldName();
        String value = item.getString("utf-8");

        // Goods goods = new Goods();
        // 解决: 哪个字段对应哪个值,这样才可以设置上
        // 这一种解决方案
        // switch (fieldName){
        //     case "name":
        //         goods.setName(value);
        //         break;
        // }

        // 方案二:
        // 写到方法外边去.
        // key, value存储到Map<String, Object> map;
        // map.put()
        // BeanUtils.populate();

        // name属性对应的值和表单的值封装到Map集合当中.通过BeanUtils来填充属性.
        if (fieldName.equals("pubdate")){
            params.put("pubdate",new Date());
        }else {
            params.put(fieldName,value);
        }

    }

    /**
     * 如果一次性选中多个文件进行上传,则会多次调用这个方法.有几个文件上传就调用几次.
     *          1. 上传逻辑一点都不更改.
     *          2. 保存到数据库当中访问路径要修改一波.
     *                  setPicture值,是一个集合.也可以是字符串.
     * 处理文件上传的字段
     *
     * @param
     * @param
     * @param item
     */
    private void manyProcessUploadedFile(List<String> urls, FileItem item) throws IOException {
        // <input type="file" name="picture"/>
        // 获取的是: picture,这个值
        String fieldName = item.getFieldName();
        // 获取图片的名称, 包含了后缀名称. 1.jpg
        String originName = item.getName();
        //2. 获取文件类型「拿到上传文件的后缀名称. 1.jpg, 拿到jpg即可.」originName,包含了完整的文件名称.1.jpg
        //1. 拆分成数组, 拿最后一个元素.
        //String[] fileArray = originName.split("\\.");
        //String suffix = fileArray[1]; // 没有点.
        //2. 通过substring
        String suffix = originName.substring(originName.lastIndexOf(("."))); // 已经包含了., 1.jpg拿到结果是.jpg,注意这个点.
        //1. 解决上传文件文件名称冲突的问题. 「当用户上传的时候,随机生成一个文件名称」
        String fileName = UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.ROOT) + suffix;
        //      3. 文件存储到硬盘上的目录.「文件真实目录」
        //      获取文件的真实目录 ---> 重要的很.
        String root = "/upload/";// 相对于idea当中目录.不能够使用,得获取它的真实目录.
        // ServletContext.getRealPath(root)
        ServletContext servletContext = ServletContextUtils.getServletContext();
        //获取文件保存的真实目录
        String rootRealPath = servletContext.getRealPath(root);
        File pathRoot = new File(rootRealPath);
        // 判断文件存储是否存在,不存在则创建一个.
        if (!pathRoot.exists()){
            pathRoot.mkdirs();
        }
        InputStream is = item.getInputStream();
        //输出流，文件保存的目录
        FileOutputStream fos = new FileOutputStream(new File(rootRealPath, fileName));
        System.out.println("文件的保存目录: " + (new File(rootRealPath, fileName)));
        // 三行代码
        int len;
        byte[] buffer = new byte[1024];
        while ((len = is.read(buffer)) != -1){
            fos.write(buffer,0,len);
        }

        if (fos != null) {
            fos.close();
        }

        if (is != null) {
            is.close();
        }

        //把路径存储到List集合当中.
        String lookPath = root + fileName;
        // 将要访问的图片地址存储到集合当中.
        urls.add(lookPath);
        System.out.println("访问的资源目录: " + lookPath);
    }

    /**
     * 如果一次性选中多个文件进行上传,则会多次调用这个方法.有几个文件上传就调用几次.
     *          1. 上传逻辑一点都不更改.
     *          2. 保存到数据库当中访问路径要修改一波.
     *                  setPicture值,是一个集合.也可以是字符串.
     * 处理文件上传的字段
     *
     * @param
     * @param params
     * @param item
     */
    private void processUploadedFile(Map<String, Object> params, FileItem item) throws IOException {
        // <input type="file" name="picture"/>
        // 获取的是: picture,这个值
        String fieldName = item.getFieldName();
        // 获取图片的名称, 包含了后缀名称. 1.jpg
        String originName = item.getName();
        //      2. 获取文件类型「拿到上传文件的后缀名称. 1.jpg, 拿到jpg即可.」originName,包含了完整的文件名称.1.jpg
        //          1. 拆分成数组, 拿最后一个元素.
        // String[] fileArray = originName.split("\\.");
        // String suffix = fileArray[1]; // 没有点.
        //          2. 通过substring
        String suffix = originName.substring(originName.lastIndexOf(".")); // 已经包含了., 1.jpg拿到结果是.jpg,注意这个点.
        //         1. 解决上传文件文件名称冲突的问题. 「当用户上传的时候,随机生成一个文件名称」
        String fileName = UUID.randomUUID().toString().replace("-", "").toUpperCase() + suffix;
        //      3. 文件存储到硬盘上的目录.「文件真实目录」
        //      获取文件的真实目录 ---> 重要的很.
        String root = "/upload/"; // 相对于idea当中目录.不能够使用,得获取它的真实目录.
        // ServletContext.getRealPath(root)
        ServletContext servletContext = ServletContextUtils.getServletContext();
        // 获取文件保存的真实目录
        String rootRealPath = servletContext.getRealPath(root);
        File pathRoot = new File(rootRealPath);
        // 判断文件存储是否存在,不存在则创建一个.
        if(!pathRoot.exists()){
            pathRoot.mkdirs();
        }

        InputStream is = item.getInputStream();
        // 输出流,文件保存的目录.
        FileOutputStream fos = new FileOutputStream(new File(rootRealPath, fileName));
        System.out.println("文件的保存目录: " + (new File(rootRealPath, fileName)));
        // 三行代码
        int len;
        byte[] buffer = new byte[1024];
        while ((len = is.read(buffer)) != -1){
            fos.write(buffer,0, len);
        }

        if (fos != null) {
            fos.close();
        }

        if (is != null) {
            is.close();
        }

        String lookPath = root + fileName;
        System.out.println("访问的资源目录: " + lookPath);
        params.put(fieldName, lookPath);
    }

    public String searchOrder(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        //四件事
        //设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.拿参数
        String username = request.getParameter("username");
        String status = request.getParameter("status");
        int statusInt = Integer.parseInt(status);
        if (username == null && username.length() == 0 && statusInt == 5){
            return Constants.FORWARD + "admin/showAllOrder.jsp";
        }
        //3.调用service层
        List<Orders> ordersList = adminService.showOrderByUidAndStatus(username, Integer.parseInt(status));
        if (ordersList.size() > 0){
            request.getSession().setAttribute("orderList",ordersList);
            //4.根据service层转发
            return Constants.FORWARD + "admin/showAllOrder.jsp";
        }
        return Constants.FORWARD + "admin/showAllOrder.jsp";
    }
}
