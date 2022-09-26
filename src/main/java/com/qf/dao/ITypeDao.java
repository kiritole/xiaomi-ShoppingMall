package com.qf.dao;

import com.qf.pojo.Type;

import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: ITypeDao
 * @Date: 2022/9/20 21:31
 */
public interface ITypeDao {

    /**
     * 根据当前的parentId查询对应的商品类型
     * @param parentId
     * @return
     */
    List<Type> getAllTypes(int parentId);
}
