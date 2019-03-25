package com.jslps.pgmisnew.view;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jslps.pgmisnew.database.Districttbl;
import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.database.ShgModel;
import com.jslps.pgmisnew.database.Shgmemnonpg;
import com.jslps.pgmisnew.database.Shgtbl;

import java.util.List;

public interface APMView {
    void setShgList(List<ShgModel> list);

    void setShgSpinner();

    void setPgName();

    void setViewAdapter(TextView farmername, TextView fatherhusbandshg, int adapterPosition, ImageView imageView);

    void setRecyclerView();

    void setShgMemNonPg(List<Shgmemnonpg> list);

    void setZoomIn();
}
