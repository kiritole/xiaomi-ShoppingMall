package com.qf.pojo;

import java.util.List;

/**
 * 分页对象.
 * @param <T>
 */
public class Page <T> {

    /**
     * 当前页「页码」, 由前端传递过来
     */
    private long size;
    /**
     * 每一页显示的条数, 由前端传递
     */
    private long page;
    /**
     * 一共有多少条记录
     *      select count(*) from tb_goods;
     */
    private long totalSizes;
    /**
     * 一共分多少页
     *      totalPage = totalSizes % size == 0 ? totalSizes / size : totalSizes / size + 1;
     */
    private long totalPages;
    /**
     * 分页数据
     *      offset = (page - 1) * size
     *      select * from tb_goods limit offset, size;
     */
    List<T> data;

    public Page() {
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getTotalSizes() {
        return totalSizes;
    }

    public void setTotalSizes(long totalSizes) {
        this.totalSizes = totalSizes;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Page{" +
                "size=" + size +
                ", page=" + page +
                ", totalSizes=" + totalSizes +
                ", totalPage=" + totalPages +
                ", data=" + data +
                '}';
    }
}
