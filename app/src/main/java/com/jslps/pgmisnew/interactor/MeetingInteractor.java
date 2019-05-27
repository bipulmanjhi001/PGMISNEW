package com.jslps.pgmisnew.interactor;

import android.support.design.widget.TextInputEditText;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jslps.pgmisnew.database.PgMeetingtbl;
import com.jslps.pgmisnew.database.Pgtbl;
import com.jslps.pgmisnew.database.ShgModel;
import com.jslps.pgmisnew.database.Shgmemnonpg;
import com.jslps.pgmisnew.database.Shgtbl;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

public class MeetingInteractor {

    public interface meetingInteractor {
        void success();
        void dateNo();
        void getMeetingData(List<PgMeetingtbl> list);

    }


    public void Validation(String date,String no,boolean AKM,boolean APS, boolean AMM,boolean MBK, final  meetingInteractor listner){

        if(date.equals("")||no.equals("")){
            listner.dateNo();
        }else{
            listner.success();
        }

    }

    public void getMeetingData(String pgcode, final  meetingInteractor listner){
        List<PgMeetingtbl> list = Select.from(PgMeetingtbl.class)
                .where(Condition.prop("Pgcode").eq(pgcode))
                .list();
        if(list.size()>0){
            listner.getMeetingData(list);
        }
    }
}
