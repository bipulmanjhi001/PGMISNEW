package com.jslps.pgmisnew.database;

import com.orm.SugarRecord;

public class Shgtbl extends SugarRecord {
    private String Villagecode;
    private String Shgcode;
    private String Shgname;

    public Shgtbl() {
    }

    public Shgtbl(String villagecode, String shgcode, String shgname) {
        Villagecode = villagecode;
        Shgcode = shgcode;
        Shgname = shgname;
    }

    public String getVillagecode() {
        return Villagecode;
    }

    public void setVillagecode(String villagecode) {
        Villagecode = villagecode;
    }

    public String getShgcode() {
        return Shgcode;
    }

    public void setShgcode(String shgcode) {
        Shgcode = shgcode;
    }

    public String getShgname() {
        return Shgname;
    }

    public void setShgname(String shgname) {
        Shgname = shgname;
    }
}
