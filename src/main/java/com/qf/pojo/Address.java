package com.qf.pojo;

import java.io.Serializable;

/**
 * 对应数据库的地址表
 */
public class Address implements Serializable {

    private int id;
    private int uid;
    private String name; //收件人名称
    private String phone; //收件人电话
    private String detail; //收件人地址
    private int state = 0; //收件地址状态 0 非默认 1默认地址

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", uid=" + uid +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", detail='" + detail + '\'' +
                ", state=" + state +
                '}';
    }

}
