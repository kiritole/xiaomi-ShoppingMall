package com.qf.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 对应数据库的商品表
 */
public class Goods implements Serializable {

    private int id;
    private int tid;        //商品分类id
    private String name;    //商品名称
    private Date  pubdate;  //商品的上架时间！ 数据库date --> java.util.Date
    private String picture; //商品的图片名称
    private int star;       //商品的热门指数
    private String intro;   //商品的描述
    private int price; //价格(单价)


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPubdate() {
        return pubdate;
    }

    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", tid=" + tid +
                ", name='" + name + '\'' +
                ", pubdate=" + pubdate +
                ", picture='" + picture + '\'' +
                ", star=" + star +
                ", intro='" + intro + '\'' +
                ", price=" + price +
                '}';
    }

}
