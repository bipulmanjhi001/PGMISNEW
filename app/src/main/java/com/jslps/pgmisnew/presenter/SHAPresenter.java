package com.jslps.pgmisnew.presenter;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.interactor.MFAInteractor;
import com.jslps.pgmisnew.interactor.SHAInteractor;
import com.jslps.pgmisnew.view.MFAView;
import com.jslps.pgmisnew.view.SHAView;

import java.util.List;

public class SHAPresenter implements SHAInteractor.shaInteractor {
    private SHAView shaView;
    private SHAInteractor shaInteractor;

    public SHAPresenter(SHAView shaView, SHAInteractor shaInteractor) {
        this.shaView = shaView;
        this.shaInteractor = shaInteractor;
    }

    public void getPgMem(String pgCode){
        shaInteractor.getPgMemList(this,pgCode);
    }

    public void setZoomIn(){
        shaView.setZoomIn();
    }

    @Override
    public void getPgMemList(List<Pgmemtbl> list) {
        shaView.setPgMemList(list);
    }

    public void setPgname(){
        shaView.setPgName();
    }

    public void setViewAdapter(ConstraintLayout firstLayout, TextView farmer, TextView total, TextView paid, TextView remaining, TextInputEditText enterAmount, View viewLayout, int adapterPosition, CheckBox checkBox,TextView pos) {
        shaView.setViewAdapter(firstLayout,farmer,total,paid,remaining,enterAmount,viewLayout,adapterPosition,checkBox,pos);
    }

    public void setRecyclerView(){
        shaView.setRecyclerView();
    }

    public void addTextChangeListner(ConstraintLayout firstLayout, TextView farmer, TextView total, TextView paid, TextView remaining, TextInputEditText enterAmount, View viewLayout, int adapterPosition, CheckBox checkBox,TextView pos){
        shaView.addTextChangeListner(firstLayout,farmer,total,paid,remaining,enterAmount,viewLayout,adapterPosition,checkBox,pos);
    }

}
