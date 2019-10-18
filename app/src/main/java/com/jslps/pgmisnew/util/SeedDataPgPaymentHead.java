package com.jslps.pgmisnew.util;

import com.jslps.pgmisnew.database.PgPaymentHeadModel;

import java.util.ArrayList;
import java.util.List;

public class SeedDataPgPaymentHead {
    private static final String[] budget_code = new String[] {
            "0",
            "JO.1.2.3",
            "JO.2.7.3",
            "JO.2.7.1",
            "JO.4.9.1",
            "JO.2.5.1",
            "JO.2.8",
            "JO.6.1.1"
    };

    private static final String[] head_name = new String[] {
            "Select Head Name",
            "Pg Start up Cost",
            "Pg Sorting and Grading",
            "Pg Poly House Nursery",
            "Advance Harvesting and Marketing",
            "HVA Demo Plot",
            "HVAFarmer Input Cost",
            "Irrigation"
    };

    public static List<PgPaymentHeadModel> getListData(){
        List<PgPaymentHeadModel> list = new ArrayList<>();
        for (int i = 0; i < budget_code.length; i++)
        {

            PgPaymentHeadModel pgPaymentHeadModel = new PgPaymentHeadModel(budget_code[i],head_name[i]);
            // Binds all strings into an array
            list.add(pgPaymentHeadModel);

        }
        return list;
    }
}
