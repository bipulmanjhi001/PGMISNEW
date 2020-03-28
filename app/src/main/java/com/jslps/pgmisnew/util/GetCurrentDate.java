package com.jslps.pgmisnew.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetCurrentDate {

    public GetCurrentDate() {
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
