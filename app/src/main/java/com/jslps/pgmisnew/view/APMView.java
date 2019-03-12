package com.jslps.pgmisnew.view;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jslps.pgmisnew.database.Districttbl;
import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.database.ShgModel;
import com.jslps.pgmisnew.database.Shgtbl;

import java.util.List;

public interface APMView {
    void setShgList(List<ShgModel> list);

    void setShgSpinner();

    void setPgName();
}
