package com.jslps.pgmisnew.database;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class Logintbl extends SugarRecord {
    @Unique
    private String Userid;
    private String Password;
    private String Username;
    private String Districtcode;
    private String Districtname;

    public Logintbl() {
    }

    public Logintbl(String userid, String password, String username, String districtcode, String districtname) {
        Userid = userid;
        Password = password;
        Username = username;
        Districtcode = districtcode;
        Districtname = districtname;
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

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
