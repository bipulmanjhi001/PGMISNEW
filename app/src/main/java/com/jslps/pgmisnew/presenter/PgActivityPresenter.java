package com.jslps.pgmisnew.presenter;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jslps.pgmisnew.database.PgActivityModel;
import com.jslps.pgmisnew.database.Pgtbl;
import com.jslps.pgmisnew.interactor.PgActivityInteractor;
import com.jslps.pgmisnew.view.PgActivityView;

import java.util.List;

public class PgActivityPresenter implements PgActivityInteractor.pgActivityInteractor {
    private PgActivityView pgActivityView;
    private PgActivityInteractor pgActivityInteractor;

    public PgActivityPresenter(PgActivityView pgActivityView, PgActivityInteractor pgActivityInteractor) {
        this.pgActivityView = pgActivityView;
        this.pgActivityInteractor = pgActivityInteractor;
    }

    public void getList(){
        pgActivityInteractor.getPgActivityList(this);
    }

    public void getSpinnerList(){
        pgActivityInteractor.getPgList(this);
    }


    @Override
    public void getPgActivityList(List<PgActivityModel> list) {
        pgActivityView.setPgActivityList(list);
    }

    @Override
    public void getPgList(List<Pgtbl> list) {
        pgActivityView.setPgSpinner(list);
    }

    public void setRecyclerView(){
        pgActivityView.setRecyclerView();
    }

    public void setZoomIn(){
        pgActivityView.setZoomIn();
    }

    public void setViewAdapter(TextView text1, TextView text2, ImageView icon1, ImageView icon2, ConstraintLayout layout1, ConstraintLayout layout2, int adapterPostion, View viewLayout){
        pgActivityView.setViewAdapter(text1,text2,icon1,icon2,layout1,layout2,adapterPostion,viewLayout);
    }
}
