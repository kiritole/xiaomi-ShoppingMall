package com.qf.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 数据库对应的购物车表
 */
public class Cart implements Serializable {

    private int cid; //购物车主键id
    private int uid; //用户id
    private int pid; //商品id
    private Goods goods;
    private int num = 0;  //购车商品数量
    private int money; //购物车小计

    public Cart() {
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getMoney() {
        return goods.getPrice() * num;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cid=" + cid +
                ", uid=" + uid +
                ", pid=" + pid +
                ", goods=" + goods +
                ", num=" + num +
                ", money=" + money +
                '}';
    }

}
