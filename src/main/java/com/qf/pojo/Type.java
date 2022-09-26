package com.qf.pojo;

import java.io.Serializable;

/**
 * 对应的数据库的类别表
 */
public class Type  implements Serializable {


    private int  id;
    private String name;
    private Integer level;
    private Integer parent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", parent=" + parent +
                '}';
    }

}
