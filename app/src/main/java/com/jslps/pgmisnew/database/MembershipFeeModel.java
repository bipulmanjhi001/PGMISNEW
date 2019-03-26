package com.jslps.pgmisnew.database;

public class MembershipFeeModel {
    private String Pgcode;
    private String Pgmemcode;
    private String Grpcode;
    private String Adapterposition;
    private String Amount;
    private String Updateamount;

    public String getUpdateamount() {
        return Updateamount;
    }

    public void setUpdateamount(String updateamount) {
        Updateamount = updateamount;
    }

    public String getPgcode() {
        return Pgcode;
    }

    public void setPgcode(String pgcode) {
        Pgcode = pgcode;
    }

    public String getPgmemcode() {
        return Pgmemcode;
    }

    public void setPgmemcode(String pgmemcode) {
        Pgmemcode = pgmemcode;
    }

    public String getGrpcode() {
        return Grpcode;
    }

    public void setGrpcode(String grpcode) {
        Grpcode = grpcode;
    }

    public String getAdapterposition() {
        return Adapterposition;
    }

    public void setAdapterposition(String adapterposition) {
        Adapterposition = adapterposition;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
