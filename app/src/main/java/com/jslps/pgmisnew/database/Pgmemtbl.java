package com.jslps.pgmisnew.database;

import com.orm.SugarRecord;

public class Pgmemtbl extends SugarRecord {
    private String Pgcode;
    private String Grpmemcode;
    private String Grpcode;
    private String Membername;
    private String Membershipfee;
    private String Sharecapital;
    private String Fathername;
    private String Husbandname;
    private String Designation;
    private String Fatherhusbandnameshg;
    private String Primaryactivity;
    private String Fishery;
    private String Hva;
    private String Ntfp;
    private String Livestock;
    private String Grpname;

    public Pgmemtbl() {
    }

    public Pgmemtbl(String pgcode, String grpmemcode, String grpcode, String membername, String membershipfee, String sharecapital, String fathername, String husbandname, String designation, String fatherhusbandnameshg, String primaryactivity, String fishery, String hva, String ntfp, String livestock,String grpname) {
        Pgcode = pgcode;
        Grpmemcode = grpmemcode;
        Grpcode = grpcode;
        Membername = membername;
        Membershipfee = membershipfee;
        Sharecapital = sharecapital;
        Fathername = fathername;
        Husbandname = husbandname;
        Designation = designation;
        Fatherhusbandnameshg = fatherhusbandnameshg;
        Primaryactivity = primaryactivity;
        Fishery = fishery;
        Hva = hva;
        Ntfp = ntfp;
        Livestock = livestock;
        Grpname = grpname;
    }

    public String getGrpname() {
        return Grpname;
    }

    public void setGrpname(String grpname) {
        Grpname = grpname;
    }

    public String getMembershipfee() {
        return Membershipfee;
    }

    public void setMembershipfee(String membershipfee) {
        Membershipfee = membershipfee;
    }

    public String getSharecapital() {
        return Sharecapital;
    }

    public void setSharecapital(String sharecapital) {
        Sharecapital = sharecapital;
    }

    public String getFathername() {
        return Fathername;
    }

    public void setFathername(String fathername) {
        Fathername = fathername;
    }

    public String getHusbandname() {
        return Husbandname;
    }

    public void setHusbandname(String husbandname) {
        Husbandname = husbandname;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getFatherhusbandnameshg() {
        return Fatherhusbandnameshg;
    }

    public void setFatherhusbandnameshg(String fatherhusbandnameshg) {
        Fatherhusbandnameshg = fatherhusbandnameshg;
    }

    public String getPrimaryactivity() {
        return Primaryactivity;
    }

    public void setPrimaryactivity(String primaryactivity) {
        Primaryactivity = primaryactivity;
    }

    public String getFishery() {
        return Fishery;
    }

    public void setFishery(String fishery) {
        Fishery = fishery;
    }

    public String getHva() {
        return Hva;
    }

    public void setHva(String hva) {
        Hva = hva;
    }

    public String getNtfp() {
        return Ntfp;
    }

    public void setNtfp(String ntfp) {
        Ntfp = ntfp;
    }

    public String getLivestock() {
        return Livestock;
    }

    public void setLivestock(String livestock) {
        Livestock = livestock;
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
