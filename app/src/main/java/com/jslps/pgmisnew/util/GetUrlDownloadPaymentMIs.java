package com.jslps.pgmisnew.util;

public class GetUrlDownloadPaymentMIs {
    private String domain;
    private String method;
    private String PgCodes;
    private String flag;



    public GetUrlDownloadPaymentMIs(String domain, String method, String PgCodes, String flag) {
        this.domain = domain;
        this.method = method;
        this.PgCodes=PgCodes;
        this.flag= flag;

    }

    public String getUrl(){
        String url;
        url = domain+"/"+method+"?PgCode="+PgCodes+"&flag="+flag;
        return url;
    }


}
