package com.jslps.pgmisnew.view;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jslps.pgmisnew.database.Pgmemtbl;

import java.util.List;

public interface MDAView {
    void setPgMemList(List<Pgmemtbl> list);

    void setZoomIn();

    void setRecyclerView();

    void moveToNext();

    void setPgName();

    void setPgItems();

    void search();

    void setViewAdapter(ConstraintLayout firstLayout,ImageView edit,ImageView delete, ImageView dropDown,TextView farmername, TextView fatherhusbandshg, TextView shg, TextView fathername, TextView husbandname, TextView designation, TextView primaryactivity, TextView fishery, TextView hva, TextView livestock, TextView ntfp, TextView memfee, TextView sharecapital, View viewLayout, ConstraintLayout layout, int adapterPosition);
}
