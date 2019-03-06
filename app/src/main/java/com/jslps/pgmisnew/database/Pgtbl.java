package com.jslps.pgmisnew.database;

import com.orm.SugarRecord;

public class Pgtbl extends SugarRecord {
    private String Villagecode;
    private String Pgcode;
    private String Pgname;

    public Pgtbl() {
    }

    public Pgtbl(String villagecode, String pgcode, String pgname) {
        Villagecode = villagecode;
        Pgcode = pgcode;
        Pgname = pgname;
    }

    public String getVillagecode() {
        return Villagecode;
    }

    public void setVillagecode(String villagecode) {
        Villagecode = villagecode;
    }

    public String getPgcode() {
        return Pgcode;
    }

    public void setPgcode(String pgcode) {
        Pgcode = pgcode;
    }

    public String getPgname() {
        return Pgname;
    }

    public void setPgname(String pgname) {
        Pgname = pgname;
    }
}
