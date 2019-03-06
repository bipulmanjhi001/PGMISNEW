package com.jslps.pgmisnew.util;

public class GetUrlLogin {
    private String domain;
    private String method;
    private String username;
    private String password;

    public GetUrlLogin(String domain, String method, String username, String password) {
        this.domain = domain;
        this.method = method;
        this.username = username;
        this.password = password;
    }

    public String getUrl(){
        String url;
        url = domain+"/"+method+"?stUserName="+username+"&stPassWord="+password;
        return url;
    }


}
