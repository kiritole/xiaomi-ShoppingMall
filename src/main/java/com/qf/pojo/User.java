package com.qf.pojo;

import java.io.Serializable;

/**
 * 对应数据库的用户表
 */
public class User  implements Serializable {


    private int id;
    private String username;  //对应的是数据库的uname字段
    private String password;  //密码
    private String gender;    //性别
    private String status;    //用户的激活状态 0 未激活 1 激活

    private String code;
    private String email;     //对应的是数据库的uemail字段
    private int role;         //用户 0 管理员 1

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

}
