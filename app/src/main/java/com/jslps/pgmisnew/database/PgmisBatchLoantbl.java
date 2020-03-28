package com.jslps.pgmisnew.database;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class PgmisBatchLoantbl extends SugarRecord {
    @Unique
    private String loanid;
    private String itemuuids;
    private String pgcode;
    private String grpcode;
    private String grpmemcode;
    private String entrydate;
    private String isexported;
    private String amount;

    public PgmisBatchLoantbl() {
    }

    public PgmisBatchLoantbl(String loanid, String itemuuids, String pgcode, String grpcode, String grpmemcode, String entrydate, String isexported,String amount) {
        this.loanid = loanid;
        this.itemuuids = itemuuids;
        this.pgcode = pgcode;
        this.grpcode = grpcode;
        this.grpmemcode = grpmemcode;
        this.entrydate = entrydate;
        this.isexported = isexported;
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLoanid() {
        return loanid;
    }

    public void setLoanid(String loanid) {
        this.loanid = loanid;
    }

    public String getItemuuids() {
        return itemuuids;
    }

    public void setItemuuids(String itemuuids) {
        this.itemuuids = itemuuids;
    }

    public String getPgcode() {
        return pgcode;
    }

    public void setPgcode(String pgcode) {
        this.pgcode = pgcode;
    }

    public String getGrpcode() {
        return grpcode;
    }

    public void setGrpcode(String grpcode) {
        this.grpcode = grpcode;
    }

    public String getGrpmemcode() {
        return grpmemcode;
    }

    public void setGrpmemcode(String grpmemcode) {
        this.grpmemcode = grpmemcode;
    }

    public String getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(String entrydate) {
        this.entrydate = entrydate;
    }

    public String getIsexported() {
        return isexported;
    }

    public void setIsexported(String isexported) {
        this.isexported = isexported;
    }
}
