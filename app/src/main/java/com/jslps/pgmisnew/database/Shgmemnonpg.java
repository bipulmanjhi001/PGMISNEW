package com.jslps.pgmisnew.database;

import com.orm.SugarRecord;

public class Shgmemnonpg extends SugarRecord {
    private String Grpmemcode;
    private String Memname;
    private String Fatherhusbandname;

    public Shgmemnonpg() {
    }

    public Shgmemnonpg(String grpmemcode, String memname, String fatherhusbandname) {
        Grpmemcode = grpmemcode;
        Memname = memname;
        Fatherhusbandname = fatherhusbandname;
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
