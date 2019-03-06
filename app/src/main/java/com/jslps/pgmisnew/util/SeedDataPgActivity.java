package com.jslps.pgmisnew.util;

import com.jslps.pgmisnew.R;
import com.jslps.pgmisnew.database.PgActivityModel;

import java.util.ArrayList;
import java.util.List;

public class SeedDataPgActivity {

    private static final int[] id1 = new int[] {
            1,
            3,
            5,
            7};
    private static final int[] id2 = new int[] {
            2,
            4,
            6,
            8};


    private static final String[] title1 = new String[] {
            "Member Detail",
            "Share Capital",
            "Livelihood Activities in PG",
            "WUG"
    };

    private static final String[] title2 = new String[] {
            "Membership Fee",
            "Meeting Details of PG",
            "Irrigation",
            "",
    };

    private static final int[] imageIcon1= new int[] {
            R.drawable.member_detail_icon,
            R.drawable.share_capital_icon,
            R.drawable.livelihood_activities_icon,
            R.drawable.member_detail_icon

    };

    private static final int[] imageIcon2= new int[] {
            R.drawable.member_fee_icon,
            R.drawable.meeting_details_icon,
            R.drawable.irrigation_icon,
            R.drawable.member_detail_icon

    };

    public static List<PgActivityModel> getListData(){
        List<PgActivityModel> list = new ArrayList<>();
        for (int i = 0; i < title1.length; i++)
        {

            PgActivityModel dashboardFragmentModel = new PgActivityModel(title1[i],title2[i],imageIcon1[i],imageIcon2[i],id1[i],id2[i]);
            // Binds all strings into an array
            list.add(dashboardFragmentModel);

        }
        return list;
    }
}
