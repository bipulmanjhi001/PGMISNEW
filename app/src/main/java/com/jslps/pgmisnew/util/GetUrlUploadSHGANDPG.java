package com.jslps.pgmisnew.util;

public class GetUrlUploadSHGANDPG {
    private String domain;
    private String method;
    private String sData;
    private String sData1;
    private String whr1;
    private String whr2;

    public GetUrlUploadSHGANDPG(String domain, String method, String sData, String sData1) {
        this.domain = domain;
        this.method = method;
        this.sData = sData;
        this.sData1 = sData1;

    }

    public String getUrl(){
        String url;
        url = domain+"/"+method+"?sData="+sData+"&sData1="+sData1;
        return url;
    }


}
