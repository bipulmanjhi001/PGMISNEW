package com.jslps.pgmisnew.database;

import com.orm.SugarRecord;

public class Pgtbl extends SugarRecord {
    private String Villagecode;
    private String Pgcode;
    private String Pgname;
    private String Primaryactivity;
    private String Fishery;
    private String Hva;
    private String Livestock;
    private String Ntfp;

    public Pgtbl() {
    }

    public Pgtbl(String villagecode, String pgcode, String pgname, String primaryactivity, String fishery, String hva, String livestock, String ntfp) {
        Villagecode = villagecode;
        Pgcode = pgcode;
        Pgname = pgname;
        Primaryactivity = primaryactivity;
        Fishery = fishery;
        Hva = hva;
        Livestock = livestock;
        Ntfp = ntfp;
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

    public String getLivestock() {
        return Livestock;
    }

    public void setLivestock(String livestock) {
        Livestock = livestock;
    }

    public String getNtfp() {
        return Ntfp;
    }

    public void setNtfp(String ntfp) {
        Ntfp = ntfp;
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
