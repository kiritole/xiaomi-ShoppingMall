package com.qf.service;

import com.qf.pojo.JsonResult;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: ITypeService
 * @Date: 2022/9/20 21:29
 */
public interface ITypeService {
    /**
     * 获取所有商品的大类别
     * tb_goods_type
     * @param parentId
     * @return
     */
    JsonResult getGoodsTypes(String parentId);
}
