package com.jslps.pgmisnew.util;

import android.content.Context;

import com.jslps.pgmisnew.R;

public class Activitycode {
    Context context;

    public Activitycode(Context context) {
        this.context = context;
    }

    public String primaryActivity(int code){
        String result="";
        if(code==1){
            result = context.getString(R.string.Fishery);
        }else if(code==2){
            result = context.getString(R.string.hva);
        }else if(code==3){
            result = context.getString(R.string.livestock_broiler);
        }else if(code==4){
            result = context.getString(R.string.livestock_goat);
        }else if(code==5){
            result = context.getString(R.string.livestock_layer);
        }else if(code==6){
            result = context.getString(R.string.livestock_pig);
        }
        return result;
    }

    public String secondaryActivity(int code){
        String result ="";
        if(code==1){
            result = context.getString(R.string.Fishery);
        }else if(code==2){
            result = context.getString(R.string.hva);
        }else if(code==3){
            result = context.getString(R.string.livestock_byp);
        }else if(code==4) {
            result = context.getString(R.string.ntfp);
        }
        return result;
    }

    public String designation(int code){
        String result="";
        if(code==1){
            result = context.getString(R.string.president);
        }else if(code==2){
            result = context.getString(R.string.Secretary);
        }else if(code==3){
            result = context.getString(R.string.Treasurer);
        }else if(code==4) {
            result = context.getString(R.string.Member);
        }
        return result;
    }
}
