package com.qf.controller.address;

import com.qf.controller.BaseController;
import com.qf.pojo.Address;
import com.qf.pojo.User;
import com.qf.service.IAddressService;
import com.qf.service.impl.AddressServiceImpl;
import com.qf.utils.Constants;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: AddressController
 * @Date: 2022/9/22 0:37
 */
@WebServlet("/address")
public class AddressController extends BaseController {
    private final IAddressService addressService = new AddressServiceImpl();
    /**
     * 获取已经存在数据库当中的地址列表
     * @param request
     * @param response
     * @return
     */
    public String getAddressList(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //4件事
        //1.编码格式
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession();
        User userNow = (User) session.getAttribute("userNow");

        //2.获取参数
        int id = userNow.getId();
        //3.调用service层方法
        List<Address> addressList = addressService.getAddressByUid(String.valueOf(id));
        request.setAttribute("addressList",addressList);

        //4.根据service层方法返回值来进行跳转
        // 如果查询出来的地址列表为空, 没有地址呢.则跳转到去地址管理的jsp页面.
        // 去self_info.jsp
        if(addressList.size() == 0){
            return Constants.FORWARD + "self_info.jsp";
        }

        //如果地址列表不为空,则返回到生成订单的页面order.jsp
        // order.jsp页面当中可能不觉有未被处理的数据.
        return Constants.FORWARD + "order?method=orderPreView&uid=" + userNow.getId();
    }

    /**
     * 展示地址列表, 返回的页面不同.
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    public String showAllAddress(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession();
        User userNow = (User) session.getAttribute("userNow");

        // 拿参数
        // 调用service处理核心业务
        List<Address> addressList = addressService.getAddressByUid(String.valueOf(userNow.getId()));
        request.setAttribute("addressList", addressList);
        return Constants.FORWARD + "self_info.jsp";
    }

    public String addAddress(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        Address address = new Address();
        // 从表单当中获取三个值
        // 姓名,手机号,详细地址.
        // 剩下三个, 手动填充
        //      主键, 自增
        //      用户uid, 可以自己获取.
        //      status, 是否是默认地址, 0, 不是, 1, 是, 新增数据默认都是0.
        Map<String, String[]> map = request.getParameterMap();
        try {
            BeanUtils.populate(address,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        address.setState(0);
        // 调用service层处理核心业务逻辑
        boolean flag = addressService.addAddress(address);

        return Constants.FORWARD + "address?method=showAllAddress";
    }

    /**
     * 删除地址
     * @param request
     * @param response
     * @return
     */
    public String deleteAddress(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        //4件事
        //1.设置编码格式
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.拿取参数
        String aid = request.getParameter("aid");

        //3.调用service层方法
        addressService.deleteAddress(aid);

        //4.根据service层返回值跳转页面
        return Constants.FORWARD + "address?method=showAllAddress";
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public String updateAddress(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        //4件事
        //1.设置编码格式
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession();
        User userNow = (User) session.getAttribute("userNow");
        Address address = new Address();

        //2.拿取参数
        Map<String, String[]> map = request.getParameterMap();
        try {
            BeanUtils.populate(address,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        address.setUid(userNow.getId());
        System.out.println(address);

        //3.调用service层方法
        addressService.updateAddress(address);

        //4.跳转页面
        return Constants.FORWARD + "address?method=showAllAddress";
    }


    /**
     * 修改为默认地址
     * @param request
     * @param response
     * @return
     */
    public String defaultAddress(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        //4件事
        //1.设置编码格式
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.拿取参数
        String aid = request.getParameter("aid");

        //3.调用service层方法
        addressService.defaultAddress(aid);

        //4.跳转页面
        return Constants.FORWARD + "address?method=showAllAddress";
    }
}
