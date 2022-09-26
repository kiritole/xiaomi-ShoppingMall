package com.qf.service.impl;

import com.qf.dao.IGoodsDao;
import com.qf.dao.impl.GoodsDaoImpl;
import com.qf.pojo.Goods;
import com.qf.pojo.Page;
import com.qf.service.IGoodsService;

import java.util.List;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: GoodsServiceImpl
 * @Date: 2022/9/20 22:55
 */
public class GoodsServiceImpl implements IGoodsService {
    private final IGoodsDao goodsDao = new GoodsDaoImpl();
    @Override
    public List<Goods> getGoodsByTId(String tid) {
        // 参数校验
        return goodsDao.getGoodsByTid(Integer.parseInt(tid));
    }

    @Override
    public Page<Goods> getGoodsByPage(String page, String size, String tid) {
        //参数校验
        if (page == null || page.length() == 0){
            page = "1";
        }
        if (size == null || size.length() == 0){
            size = "4";
        }

        //创建一个分页对象
        Page<Goods> paging = new Page<>();
        //当前页
        int pageInt = Integer.parseInt(page);
        // 每一页显示的条数
        int sizeInt = Integer.parseInt(size);
        // 商品分类的主键
        int tidInt = Integer.parseInt(tid);
        //查询出总记录数
        long totalSizes = goodsDao.getGoodsCount(tidInt);
        //计算出总页数
        long totalPages = totalSizes % sizeInt == 0 ? totalSizes / sizeInt : totalSizes / sizeInt + 1;

        // 判断一下前端传递过来的边界值.
        // 点击上一页, page = 1, 应该是没有上一页
        // 点击下一页, page = 分页的最大值, 没有下一页
        if (pageInt < 1){
            pageInt = 1;
        }
        if (totalPages != 0 && pageInt > totalPages){
            pageInt = (int) totalPages;
        }
        // 查询出分页数据
        int offset = (pageInt - 1) * sizeInt;
        // System.out.printf("page = %s, size = %s, totalSize = %s, totalPages = %s \n", pageInt, sizeInt, totalSizes, totalPages);
        // 调用dao层的查询方法,获取一个分页数据
        List<Goods> goods = goodsDao.getGoodsByPage(offset, sizeInt, tidInt);
        // System.out.printf("page = %s, size = %s, totalSize = %s, totalPages = %s, 数据长度:%s \n", pageInt, sizeInt, totalSizes, totalPages, goods.size());
        paging.setPage(pageInt);
        paging.setSize(sizeInt);
        paging.setTotalPages(totalPages);
        paging.setTotalSizes(totalSizes);
        paging.setData(goods);

        return paging;
    }

    @Override
    public Goods getGoodsById(String id) {
        // 参数校验
        // 参数校验
        // 参数校验
        // 参数校验
        // 参数校验
        // 参数校验
        return goodsDao.getGoodsById(Integer.parseInt(id));
    }
}
