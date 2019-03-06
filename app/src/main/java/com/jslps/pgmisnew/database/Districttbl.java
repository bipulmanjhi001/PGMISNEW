package com.jslps.pgmisnew.database;

import com.orm.SugarRecord;

public class Districttbl extends SugarRecord {
    private String Userid;
    private String Districtcode;
    private String Districtname;

    public Districttbl() {
    }

    public Districttbl(String userid, String districtcode, String districtname) {
        Userid = userid;
        Districtcode = districtcode;
        Districtname = districtname;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getDistrictcode() {
        return Districtcode;
    }

    public void setDistrictcode(String districtcode) {
        Districtcode = districtcode;
    }

    public String getDistrictname() {
        return Districtname;
    }

    public void setDistrictname(String districtname) {
        Districtname = districtname;
    }
}
