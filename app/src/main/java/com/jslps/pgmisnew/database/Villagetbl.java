package com.jslps.pgmisnew.database;

import com.orm.SugarRecord;

public class Villagetbl extends SugarRecord {
    private String Clustercode;
    private String Villagecode;
    private String Villagename;

    public Villagetbl() {
    }

    public Villagetbl(String clustercode, String villagecode, String villagename) {
        Clustercode = clustercode;
        Villagecode = villagecode;
        Villagename = villagename;
    }

    public String getClustercode() {
        return Clustercode;
    }

    public void setClustercode(String clustercode) {
        Clustercode = clustercode;
    }

    public String getVillagecode() {
        return Villagecode;
    }

    public void setVillagecode(String villagecode) {
        Villagecode = villagecode;
    }

    public String getVillagename() {
        return Villagename;
    }

    public void setVillagename(String villagename) {
        Villagename = villagename;
    }
}
