package com.jslps.pgmisnew.database;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class PgMeetingtbl extends SugarRecord {
    @Unique
    private String Meetingid;
    private String Meetingdate;
    private String Noofpeople;
    private String Cadres;
    private String Pgcode;
    private String Isxported;

    public PgMeetingtbl() {
    }

    public PgMeetingtbl(String meetingid, String meetingdate, String noofpeople, String cadres,String pgcode,String isexported) {
        Meetingid = meetingid;
        Meetingdate = meetingdate;
        Noofpeople = noofpeople;
        Cadres = cadres;
        Pgcode = pgcode;
       Isxported = isexported;
    }

    public String getPgcode() {
        return Pgcode;
    }

    public void setPgcode(String pgcode) {
        Pgcode = pgcode;
    }

    public String getIsxported() {
        return Isxported;
    }

    public void setIsxported(String isxported) {
        Isxported = isxported;
    }

    public String getMeetingid() {
        return Meetingid;
    }

    public void setMeetingid(String meetingid) {
        Meetingid = meetingid;
    }

    public String getMeetingdate() {
        return Meetingdate;
    }

    public void setMeetingdate(String meetingdate) {
        Meetingdate = meetingdate;
    }

    public String getNoofpeople() {
        return Noofpeople;
    }

    public void setNoofpeople(String noofpeople) {
        Noofpeople = noofpeople;
    }

    public String getCadres() {
        return Cadres;
    }

    public void setCadres(String cadres) {
        Cadres = cadres;
    }
}
