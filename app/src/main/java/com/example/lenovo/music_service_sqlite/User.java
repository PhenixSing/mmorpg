package com.example.lenovo.music_service_sqlite;

import cn.bmob.v3.BmobObject;

public class User extends BmobObject {
    private String account;
    private String password;
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
