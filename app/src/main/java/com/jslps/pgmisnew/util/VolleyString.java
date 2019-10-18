package com.jslps.pgmisnew.util;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;


import java.util.Map;

public class VolleyString {
    private StringRequest mStringRequest;
    private String url;
    private String tableIndentifier;
    VolleyListner volleyListner;

    public interface VolleyListner{
        void onResponseSuccess(String tableIndentifier,String result);
        void onResponseFailure(String tableIdentifier);
    }

    public VolleyString( String url, String tableIndentifier,VolleyListner volleyListner) {
        this.tableIndentifier=tableIndentifier;
        this.url=url;
        this.volleyListner = volleyListner;
    }

    public StringRequest getString(){
        mStringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    String XmlString = response.substring(response.indexOf("\">")+2);
                    String result = XmlString.replaceAll("</string>","");
                    volleyListner.onResponseSuccess(tableIndentifier,result);

                },
                error -> {
                    volleyListner.onResponseFailure(tableIndentifier);
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {

                return null;
            }
        };
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return mStringRequest;


    }
}
