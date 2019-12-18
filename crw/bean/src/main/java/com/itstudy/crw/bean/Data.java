package com.itstudy.crw.bean;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private List<User> userList = new ArrayList<User>();
    private List<User> datas = new ArrayList<User>();

    private List<Integer> ids;
    private List<MemberCert> certs = new ArrayList<MemberCert>();

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getDatas() {
        return datas;
    }

    public void setDatas(List<User> datas) {
        this.datas = datas;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<MemberCert> getCerts() {
        return certs;
    }

    public void setCerts(List<MemberCert> certs) {
        this.certs = certs;
    }
}
