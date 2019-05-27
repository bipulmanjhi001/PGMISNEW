package com.jslps.pgmisnew.view;

import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jslps.pgmisnew.database.PgMeetingtbl;

import java.util.List;

public interface MeetingView {
    void setPgName();

    void setOpenCalender();

    void setAdapter(ImageView edit, ImageView delete, TextView date, TextView no, TextView cadre, ConstraintLayout layout1, ConstraintLayout layout2,int position);

    void dateNoEmpty();

    void saveData();

    void setRecyclerView();

    void meetingData(List<PgMeetingtbl> list);
}
