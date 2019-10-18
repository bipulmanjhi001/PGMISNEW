package com.jslps.pgmisnew.view;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jslps.pgmisnew.database.PgActivityModel;
import com.jslps.pgmisnew.database.Pgtbl;

import java.util.List;

public interface PgActivityView {

    void setPgActivityList(List<PgActivityModel> list);

    void setRecyclerView();

    void setZoomIn();

    void setViewAdapter(TextView text1, TextView text2, ImageView icon1, ImageView icon2, ConstraintLayout layout1, ConstraintLayout layout2, int adapterPostion, View viewLayout);

    void setPgSpinner(List<Pgtbl> list);

    void uploadHide();

    void uploadUnhide();

    void pgMemsShgMems();

    void callUploadApi(String sData,String sData1);

    void callUploadApiMeeting(String sData);

    void callDownloadWebServicesMeetingtbl();
}
