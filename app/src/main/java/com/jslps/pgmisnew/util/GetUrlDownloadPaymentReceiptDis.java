package com.jslps.pgmisnew.util;

public class GetUrlDownloadPaymentReceiptDis {
    private String domain;
    private String method;
    private String PgCodes;



    public GetUrlDownloadPaymentReceiptDis(String domain, String method, String PgCodes) {
        this.domain = domain;
        this.method = method;
        this.PgCodes=PgCodes;

    }

    public String getUrl(){
        String url;
        url = domain+"/"+method+"?PgCode="+PgCodes;
        return url;
    }


}
