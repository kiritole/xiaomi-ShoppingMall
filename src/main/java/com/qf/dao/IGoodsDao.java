package com.qf.dao;

import com.qf.pojo.Goods;
import com.qf.pojo.Page;

import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: IGoodsDao
 * @Date: 2022/9/20 22:56
 */
public interface IGoodsDao {

    /**
     * 根据tid查询出所有的数据
     * @param tid
     * @return
     */
    List<Goods> getGoodsByTid(int tid);

    /**
     * 分页查询数据库
     * @param offset, 偏移量,计算得来. 计算好的,不要在dao层进行计算. offset = (page - 1) * size
     * @param size, 每一页显示的条数
     * @return
     */
    List<Goods> getGoodsByPage(int offset, int size);

    /**
     * 带类型的分页查询
     * @param offset
     * @param size
     * @param tid
     * @return
     */
    List<Goods> getGoodsByPage(int offset,int size,int tid);

    /**
     * 根据类别查询记录数
     * @param tid
     * @return
     */
    long getGoodsCount(int tid);


    /**
     * 根据id查询记录
     * @param id
     * @return
     */
    Goods getGoodsById(int id);

}
