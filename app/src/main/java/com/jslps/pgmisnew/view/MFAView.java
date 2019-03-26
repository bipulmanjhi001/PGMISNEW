package com.jslps.pgmisnew.view;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jslps.pgmisnew.database.Pgmemtbl;

import java.util.List;

public interface MFAView {
    void setPgMemList(List<Pgmemtbl> list);

    void setZoomIn();

    void setPgName();

    void setViewAdapter(ConstraintLayout firstLayout, TextView farmer, TextView total, TextView paid, TextView remaining, TextInputEditText enterAmount, View viewLayout, int adapterPosition, CheckBox checkBox);

    void setRecyclerView();
}
