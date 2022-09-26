package com.qf.service.impl;

import com.qf.dao.ITypeDao;
import com.qf.dao.impl.TypeDaoImpl;
import com.qf.pojo.JsonResult;
import com.qf.pojo.Type;
import com.qf.service.ITypeService;

import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: TypeServiceImpl
 * @Date: 2022/9/20 21:30
 */
public class TypeServiceImpl implements ITypeService {
    private final ITypeDao typeDao = new TypeDaoImpl();
    @Override
    public JsonResult getGoodsTypes(String parentId) {
        // 参数校验
        // 参数校验
        // 参数校验
        // 参数校验
        // List肯定不为空, 元素个数为0;
        List<Type> types = typeDao.getAllTypes(Integer.parseInt(parentId));
        return JsonResult.ok(0,"操作成功",types);
    }
}
