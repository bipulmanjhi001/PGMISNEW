package com.jslps.pgmisnew.database;

import com.orm.SugarRecord;

public class Pgmisstockmsttbl extends SugarRecord {
    private String uuid;
    private String itemcode;
    private String itemname;
    private String entrydate;
    private String entryby;

    public Pgmisstockmsttbl() {
    }

    public Pgmisstockmsttbl(String uuid, String itemcode, String itemname, String entrydate, String entryby) {
        this.uuid = uuid;
        this.itemcode = itemcode;
        this.itemname = itemname;
        this.entrydate = entrydate;
        this.entryby = entryby;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(String entrydate) {
        this.entrydate = entrydate;
    }

    public String getEntryby() {
        return entryby;
    }

    public void setEntryby(String entryby) {
        this.entryby = entryby;
    }

    @Override
    public String toString() {
        return itemname;
    }
}
