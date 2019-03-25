package com.jslps.pgmisnew.database;

import com.orm.SugarRecord;

public class Shgmemberslocallyaddedtbl extends SugarRecord {
    private String Pgcode;
    private String Grpmemcode;
    private String Grpcode;
    private String Membername;
    private String Fathername;
    private String Husbandname;
    private String Designation;
    private String Primaryactivity;
    private String Fishery;
    private String Hva;
    private String Ntfp;
    private String Livestock;
    private String Status;
    private String Uid;
    private String Isexported;
    private String Gender;
    private String Cast;

    public Shgmemberslocallyaddedtbl() {
    }

    public Shgmemberslocallyaddedtbl(String pgcode, String grpmemcode, String grpcode, String membername, String fathername, String husbandname, String designation, String primaryactivity, String fishery, String hva, String ntfp, String livestock, String status, String uid, String isexported, String gender, String cast) {
        Pgcode = pgcode;
        Grpmemcode = grpmemcode;
        Grpcode = grpcode;
        Membername = membername;
        Fathername = fathername;
        Husbandname = husbandname;
        Designation = designation;
        Primaryactivity = primaryactivity;
        Fishery = fishery;
        Hva = hva;
        Ntfp = ntfp;
        Livestock = livestock;
        Status = status;
        Uid = uid;
        Isexported = isexported;
        Gender = gender;
        Cast = cast;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getCast() {
        return Cast;
    }

    public void setCast(String cast) {
        Cast = cast;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getIsexported() {
        return Isexported;
    }

    public void setIsexported(String isexported) {
        Isexported = isexported;
    }
}
