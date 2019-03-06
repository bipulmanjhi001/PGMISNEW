package com.jslps.pgmisnew.database;

import com.orm.SugarRecord;

public class Pgmemtbl extends SugarRecord {
    private String Pgcode;
    private String Grpmemcode;
    private String Grpcode;
    private String Membername;

    public Pgmemtbl() {
    }

    public Pgmemtbl(String pgcode, String grpmemcode, String grpcode, String membername) {
        Pgcode = pgcode;
        Grpmemcode = grpmemcode;
        Grpcode = grpcode;
        Membername = membername;
    }

    public String getPgcode() {
        return Pgcode;
    }

    public void setPgcode(String pgcode) {
        Pgcode = pgcode;
    }

    public String getGrpmemcode() {
        return Grpmemcode;
    }

    public void setGrpmemcode(String grpmemcode) {
        Grpmemcode = grpmemcode;
    }

    public String getGrpcode() {
        return Grpcode;
    }

    public void setGrpcode(String grpcode) {
        Grpcode = grpcode;
    }

    public String getMembername() {
        return Membername;
    }

    public void setMembername(String membername) {
        Membername = membername;
    }
}
