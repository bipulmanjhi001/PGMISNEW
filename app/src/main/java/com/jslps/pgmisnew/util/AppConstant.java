package com.jslps.pgmisnew.util;

import android.content.Context;
import android.graphics.Typeface;

public class AppConstant {
    public static final String domain="http://johar.swalekha.in/Webservices/WebService.asmx";

    //methods
    public static final String loginMethod="PGMIS_Login_Data";
    public static final String Upload_tblMstGroupMembers_Johar="Upload_tblMstGroupMembers_Johar";
    public static final String Upload_PgMeetingtbl="Upload_PgMeetingtbl";
    public static final String Download_Johar_TabletData_Service="Download_Johar_TabletData_Service";

    //table indentifier
    public static final String logintbl ="1";
    public static final String tblMstGroupMembers_Johar="2";
    public static final String PgMeetingtbl="3";
    public static final String PgMeetingtblDownload="4";


    //flags
    public static final String meetingtblflag ="PgMeetingtbl";



    //Fixed Amounts

    public static final String MEMBERSHIPFEE="100";
    public static final String SHARECAPITAL="1000";


    //Edits
    public static boolean editpgpaymentrecord=false;
    public static boolean editpgreceiptrecord=false;

}
