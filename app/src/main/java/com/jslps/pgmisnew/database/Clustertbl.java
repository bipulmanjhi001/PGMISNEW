package com.jslps.pgmisnew.database;

import com.orm.SugarRecord;

public class Clustertbl extends SugarRecord {
    private String Blockcode;
    private String Clustercode;
    private String Clustername;

    public Clustertbl() {
    }

    public Clustertbl(String blockcode, String clustercode, String clustername) {
        Blockcode = blockcode;
        Clustercode = clustercode;
        Clustername = clustername;
    }

    public String getBlockcode() {
        return Blockcode;
    }

    public void setBlockcode(String blockcode) {
        Blockcode = blockcode;
    }

    public String getClustercode() {
        return Clustercode;
    }

    public void setClustercode(String clustercode) {
        Clustercode = clustercode;
    }

    public String getClustername() {
        return Clustername;
    }

    public void setClustername(String clustername) {
        Clustername = clustername;
    }
}
