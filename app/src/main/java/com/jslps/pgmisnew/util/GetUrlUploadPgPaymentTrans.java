package com.jslps.pgmisnew.util;

public class GetUrlUploadPgPaymentTrans {
    private String domain;
    private String method;
    private String sData;
    private String flag;
    private String where;


    public GetUrlUploadPgPaymentTrans(String domain, String method, String sData,String flag,String where) {
        this.domain = domain;
        this.method = method;
        this.sData = sData;
        this.flag = flag;
        this.where = where;

    }

    public String getUrl(){
        String url;
        url = domain+"/"+method+"?jsonstr="+sData+"&flag="+flag+"&where="+where;
        return url;
    }


}
