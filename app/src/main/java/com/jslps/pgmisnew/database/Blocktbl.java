package com.jslps.pgmisnew.database;

import com.orm.SugarRecord;

public class Blocktbl extends SugarRecord {
    private String Districtcode;
    private String Blockcode;
    private String blockname;

    public Blocktbl() {
    }

    public Blocktbl(String districtcode, String blockcode, String blockname) {
        Districtcode = districtcode;
        Blockcode = blockcode;
        this.blockname = blockname;
    }

    public String getDistrictcode() {
        return Districtcode;
    }

    public void setDistrictcode(String districtcode) {
        Districtcode = districtcode;
    }

    public String getBlockcode() {
        return Blockcode;
    }

    public void setBlockcode(String blockcode) {
        Blockcode = blockcode;
    }

    public String getBlockname() {
        return blockname;
    }

    public void setBlockname(String blockname) {
        this.blockname = blockname;
    }
}
