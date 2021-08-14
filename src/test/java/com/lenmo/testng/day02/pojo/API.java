package com.lenmo.testng.day02.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class API {
    //CaseId
    @Excel(name = "CaseId")
    private int id;
    @Excel(name = "Name")
    private String name;
    @Excel(name = "Url")
    private String url;
    @Excel(name = "Type")
    private String method;

    @Override
    public String toString() {
        return "API{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                '}';
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


}

