package com.qf.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 对应数据库订单项
 */
public class OrderDetail implements Serializable {


    private int id;
    private String oid; //订单id
    private int pid;    //商品id
    private int money; //小计
    private int num;    //购买数量
    private Goods goods;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", oid='" + oid + '\'' +
                ", pid=" + pid +
                ", money=" + money +
                ", num=" + num +
                ", goods=" + goods +
                '}';
    }

}
