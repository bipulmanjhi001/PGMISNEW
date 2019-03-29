package com.jslps.pgmisnew.presenter;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.interactor.MFAInteractor;
import com.jslps.pgmisnew.view.MFAView;

import java.util.List;

public class MFAPresenter implements MFAInteractor.mfaInteractor {
    private MFAView mfaView;
    private MFAInteractor mfaInteractor;

    public MFAPresenter(MFAView mfaView, MFAInteractor mfaInteractor) {
        this.mfaView = mfaView;
        this.mfaInteractor = mfaInteractor;
    }

    public void getPgMem(String pgCode){
        mfaInteractor.getPgMemList(this,pgCode);
    }

    public void setZoomIn(){
        mfaView.setZoomIn();
    }

    @Override
    public void getPgMemList(List<Pgmemtbl> list) {
        mfaView.setPgMemList(list);
    }

    public void setPgname(){
        mfaView.setPgName();
    }

    public void setViewAdapter(ConstraintLayout firstLayout, TextView farmer, TextView total, TextView paid, TextView remaining, TextInputEditText enterAmount, View viewLayout, int adapterPosition, CheckBox checkBox,TextView pos) {
        mfaView.setViewAdapter(firstLayout,farmer,total,paid,remaining,enterAmount,viewLayout,adapterPosition,checkBox,pos);
    }

    public void setRecyclerView(){
        mfaView.setRecyclerView();
    }

    public void addTextChangeListner(ConstraintLayout firstLayout, TextView farmer, TextView total, TextView paid, TextView remaining, TextInputEditText enterAmount, View viewLayout, int adapterPosition, CheckBox checkBox,TextView pos){
        mfaView.addTextChangeListner(firstLayout,farmer,total,paid,remaining,enterAmount,viewLayout,adapterPosition,checkBox,pos);
    }

}
