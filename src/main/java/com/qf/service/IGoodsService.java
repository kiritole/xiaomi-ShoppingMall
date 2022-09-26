package com.qf.service;

import com.qf.pojo.Goods;
import com.qf.pojo.Page;

import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: IGoodsService
 * @Date: 2022/9/20 22:54
 */
public interface IGoodsService {

    /**
     * 根据tid去tb_goods表当中查询出对应的所有数据
     * @param tid
     * @return
     */
    List<Goods> getGoodsByTId(String tid);

    /**
     * 分页处理
     * @param page  当前页「页码」
     * @param size, 每一页显示的条数
     * @return
     */
    Page<Goods> getGoodsByPage(String page, String size, String tid);

    /**
     * 根据主键查询数据
     * @param id
     * @return
     */
    Goods getGoodsById(String id);
}
