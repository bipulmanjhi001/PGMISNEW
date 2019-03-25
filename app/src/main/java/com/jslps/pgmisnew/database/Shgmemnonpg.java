package com.jslps.pgmisnew.database;

import com.jslps.pgmisnew.adapter.AddPgMembersAdapter;
import com.orm.SugarRecord;

public class Shgmemnonpg extends SugarRecord {
    private String Grpmemcode;
    private String Memname;
    private String Fatherhusbandname;
    private String Shgcode;
    private String Addedtopg;

    public Shgmemnonpg() {
    }

    public Shgmemnonpg(String grpmemcode, String memname, String fatherhusbandname, String shgcode, String addedtopg) {
        Grpmemcode = grpmemcode;
        Memname = memname;
        Fatherhusbandname = fatherhusbandname;
        Shgcode = shgcode;
        Addedtopg =addedtopg;
    }

    public String getAddedtopg() {
        return Addedtopg;
    }

    public void setAddedtopg(String addedtopg) {
        Addedtopg = addedtopg;
    }

    public String getShgcode() {
        return Shgcode;
    }

    public void setShgcode(String shgcode) {
        Shgcode = shgcode;
    }

    public String getGrpmemcode() {
        return Grpmemcode;
    }

    public void setGrpmemcode(String grpmemcode) {
        Grpmemcode = grpmemcode;
    }

    public String getMemname() {
        return Memname;
    }

    public void setMemname(String memname) {
        Memname = memname;
    }

    public String getFatherhusbandname() {
        return Fatherhusbandname;
    }

    public void setFatherhusbandname(String fatherhusbandname) {
        Fatherhusbandname = fatherhusbandname;
    }
}
